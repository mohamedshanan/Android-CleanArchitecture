/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import com.masary.anamasary.data.net.RestApi;

import io.reactivex.Observable;

/**
 * {@link EncryptionKeyDataStore} implementation based on connections to the api (Cloud).
 */
class CloudEncryptionKeyDataStore implements EncryptionKeyDataStore {

  private final RestApi restApi;
    private final EncryptionKeyCache encryptionKeyCache;

  /**
   * Construct a {@link EncryptionKeyDataStore} based on connections to the api (Cloud).
   *
   * @param restApi The {@link RestApi} implementation to use.
   * @param encryptionKeyCache A {@link EncryptionKeyCache} to cache data retrieved from the api.
   */
  CloudEncryptionKeyDataStore(RestApi restApi, EncryptionKeyCache encryptionKeyCache) {
    this.restApi = restApi;
      this.encryptionKeyCache = encryptionKeyCache;
  }

    @Override
    public Observable<EncryptionKeyEntity> verifyCode(final String verificationCode) {
        return this.restApi.verifyCode(verificationCode).doOnNext(CloudEncryptionKeyDataStore.this.encryptionKeyCache::put);
  }
}
