/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.masary.anamasary.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.masary.anamasary.data.cache.EncryptionKeyCache;
import com.masary.anamasary.data.entity.mapper.EncryptionKeyEntityJsonMapper;
import com.masary.anamasary.data.net.RestApi;
import com.masary.anamasary.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link EncryptionKeyDataStore}.
 */
@Singleton
public class EncryptionKeyDataStoreFactory {

  private final Context context;
    private final EncryptionKeyCache encryptionKeyCache;

  @Inject
  EncryptionKeyDataStoreFactory(@NonNull Context context, @NonNull EncryptionKeyCache encryptionKeyCache) {
    this.context = context.getApplicationContext();
      this.encryptionKeyCache = encryptionKeyCache;
  }

  /**
   * Create {@link EncryptionKeyDataStore} from a user id.
   */
  public EncryptionKeyDataStore create(final String verificationKey) {
      EncryptionKeyDataStore encryptionKeyDataStore;

      if (!this.encryptionKeyCache.isExpired() && this.encryptionKeyCache.isCached()) {
          encryptionKeyDataStore = new DiskEncryptionKeyDataStore(this.encryptionKeyCache);
    } else {
          encryptionKeyDataStore = createCloudDataStore(verificationKey);
    }

      return encryptionKeyDataStore;
  }

  /**
   * Create {@link EncryptionKeyDataStore} to retrieve data from the Cloud.
   */
  public EncryptionKeyDataStore createCloudDataStore(final String verificationKey) {
      final EncryptionKeyEntityJsonMapper encryptionKeyEntityJsonMapper = new EncryptionKeyEntityJsonMapper();
      final RestApi restApi = new RestApiImpl(this.context, encryptionKeyEntityJsonMapper);

      return new CloudEncryptionKeyDataStore(restApi, this.encryptionKeyCache);
  }
}
