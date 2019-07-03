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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.masary.anamasary.data.entity.EncryptionKeyEntity;

import java.lang.reflect.Type;

import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class EncryptionKeyEntityJsonMapper {

  private final Gson gson;

  @Inject
  public EncryptionKeyEntityJsonMapper() {
    this.gson = new Gson();
  }

  /**
   * Transform from valid json string to {@link EncryptionKeyEntity}.
   *
   * @param encryptionKeyJsonResponse A json representing a EncryptionKey.
   * @return {@link EncryptionKeyEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public EncryptionKeyEntity transformEncryptionKeyEntity(String encryptionKeyJsonResponse) throws JsonSyntaxException {
      final Type userEntityType = new TypeToken<EncryptionKeyEntity>() {
      }.getType();
      return this.gson.fromJson(encryptionKeyJsonResponse, userEntityType);
  }
}
