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
package com.masary.anamasary.presentation.mapper;

import com.masary.anamasary.domain.EncryptionKey;
import com.masary.anamasary.presentation.internal.di.PerActivity;
import com.masary.anamasary.presentation.model.EncryptionKeyModel;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link EncryptionKey} (in the domain layer) to {@link EncryptionKeyModel} in the
 * presentation layer.
 */
@PerActivity
public class EncryptionKeyModelDataMapper {

  @Inject
  public EncryptionKeyModelDataMapper() {
  }

  /**
   * Transform a {@link EncryptionKey} into an {@link EncryptionKeyModel}.
   *
   * @param encryptionKey Object to be transformed.
   * @return {@link EncryptionKeyModel}.
   */
  public EncryptionKeyModel transform(EncryptionKey encryptionKey) {
      if (encryptionKey == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
      final EncryptionKeyModel encryptionKeyModel = new EncryptionKeyModel(encryptionKey.getKey());

      return encryptionKeyModel;
  }
}