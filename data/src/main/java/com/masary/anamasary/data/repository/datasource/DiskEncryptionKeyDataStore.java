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

import com.masary.anamasary.data.cache.EncryptionKeyCache;
import com.masary.anamasary.data.entity.EncryptionKeyEntity;

import io.reactivex.Observable;

/**
 * {@link EncryptionKeyDataStore} implementation based on file system data store.
 */
class DiskEncryptionKeyDataStore implements EncryptionKeyDataStore {

    private final EncryptionKeyCache encryptionKeyCache;

  /**
   * Construct a {@link EncryptionKeyDataStore} based file system data store.
   *
   * @param encryptionKeyCache A {@link EncryptionKeyCache} to cache data retrieved from the api.
   */
  DiskEncryptionKeyDataStore(EncryptionKeyCache encryptionKeyCache) {
      this.encryptionKeyCache = encryptionKeyCache;
  }


    @Override
    public Observable<EncryptionKeyEntity> verifyCode(final String verificationCode) {
        return this.encryptionKeyCache.get();
  }
}
