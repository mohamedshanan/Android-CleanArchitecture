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
package com.masary.anamasary.data.entity.mapper;

import com.masary.anamasary.data.entity.EncryptionKeyEntity;
import com.masary.anamasary.domain.EncryptionKey;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link EncryptionKeyEntity} (in the data layer) to {@link EncryptionKey} in the
 * domain layer.
 */
@Singleton
public class EncryptionKeyEntityDataMapper {

  @Inject
  EncryptionKeyEntityDataMapper() {
  }

  /**
   * Transform a {@link EncryptionKeyEntity} into an {@link EncryptionKey}.
   *
   * @param EncryptionKeyEntity Object to be transformed.
   * @return {@link EncryptionKey} if valid {@link EncryptionKeyEntity} otherwise null.
   */
  public EncryptionKey transform(EncryptionKeyEntity EncryptionKeyEntity) {
      EncryptionKey encryptionKey = null;
      if (EncryptionKeyEntity != null) {
          encryptionKey = new EncryptionKey(EncryptionKeyEntity.getKey());
      }
      return encryptionKey;
  }
}
