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
package com.masary.anamasary.domain.interactor;

import com.masary.anamasary.domain.EncryptionKey;
import com.masary.anamasary.domain.executor.PostExecutionThread;
import com.masary.anamasary.domain.executor.ThreadExecutor;
import com.masary.anamasary.domain.repository.EncryptionKeyRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * verifying confirmation code received via sms {@link EncryptionKey}.
 */
public class VerifyCode extends UseCase<EncryptionKey, VerifyCode.Params> {

    private final EncryptionKeyRepository encryptionKeyRepository;

  @Inject
  VerifyCode(EncryptionKeyRepository encryptionKeyRepository, ThreadExecutor threadExecutor,
             PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
      this.encryptionKeyRepository = encryptionKeyRepository;
  }

    @Override
    Observable<EncryptionKey> buildUseCaseObservable(@NotNull Params params) {
        return this.encryptionKeyRepository.verifyCode(params.verificationCode);
  }

  public static final class Params {

      private final String verificationCode;

      private Params(String verificationCode) {
          this.verificationCode = verificationCode;
    }

      public static Params buildParams(String verificationCode) {
          return new Params(verificationCode);
    }
  }
}
