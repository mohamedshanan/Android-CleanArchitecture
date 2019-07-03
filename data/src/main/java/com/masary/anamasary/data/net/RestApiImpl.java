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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.masary.anamasary.data.entity.EncryptionKeyEntity;
import com.masary.anamasary.data.entity.mapper.EncryptionKeyEntityJsonMapper;
import com.masary.anamasary.data.exception.NetworkConnectionException;

import java.net.MalformedURLException;

import io.reactivex.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

  private final Context context;
    private final EncryptionKeyEntityJsonMapper encryptionKeyEntityJsonMapper;

  /**
   * Constructor of the class
   *
   * @param context {@link android.content.Context}.
   * @param encryptionKeyEntityJsonMapper {@link EncryptionKeyEntityJsonMapper}.
   */
  public RestApiImpl(Context context, EncryptionKeyEntityJsonMapper encryptionKeyEntityJsonMapper) {
      if (context == null || encryptionKeyEntityJsonMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
      this.encryptionKeyEntityJsonMapper = encryptionKeyEntityJsonMapper;
  }

    @Override
    public Observable<EncryptionKeyEntity> verifyCode(final String verificationCode) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
            String verificationResponse = verifyCodeWithServer(verificationCode);
            if (verificationResponse != null) {
                emitter.onNext(encryptionKeyEntityJsonMapper.transformEncryptionKeyEntity(verificationResponse));
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

    private String verifyCodeWithServer(String verificationCode) throws MalformedURLException {
        String apiUrl = API_URL_GET_USER_DETAILS + verificationCode + ".json";
        return com.masary.anamasary.data.net.ApiConnection.createGET(apiUrl).requestSyncCall();
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
            (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }
}
