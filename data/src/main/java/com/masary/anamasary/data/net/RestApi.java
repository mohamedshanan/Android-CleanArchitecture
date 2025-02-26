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
package com.masary.anamasary.data.net;

import com.masary.anamasary.data.entity.EncryptionKeyEntity;
import com.masary.anamasary.domain.EncryptionKey;

import io.reactivex.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
  String API_BASE_URL =
          "https://raw.githubusercontent.com/anamasary/Sample-Data/master/Android-CleanArchitecture/";

  /** Api url for getting all users */
  String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
  /** Api url for getting a user profile: Remember to concatenate id + 'json' */
  String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

  /**
   * Retrieves an {@link Observable} which will emit a {@link EncryptionKey}.
   *
   * @param verificationCode The verification code to check against.
   */
  Observable<EncryptionKeyEntity> verifyCode(final String verificationCode);
}
