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
package com.masary.anamasary.data.cache;

import com.masary.anamasary.data.entity.EncryptionKeyEntity;

import io.reactivex.Observable;

/**
 * An interface representing encryption key Cache.
 */
public interface EncryptionKeyCache {
  /**
   * Gets an {@link Observable} which will emit a {@link EncryptionKeyEntity}.
   *
   */
  Observable<EncryptionKeyEntity> get();

  /**
   * Puts and element into the cache.
   *
   * @param encryptionKeyEntity Element to insert in the cache.
   */
  void put(EncryptionKeyEntity encryptionKeyEntity);

  /**
   * Checks if there is an element (EncryptionKey) in the cache.
   *
   * @return true if the element is cached, otherwise false.
   */
  boolean isCached();

  /**
   * Checks if the cache is expired.
   *
   * @return true, the cache is expired, otherwise false.
   */
  boolean isExpired();

  /**
   * Evict all elements of the cache.
   */
  void evictAll();
}
