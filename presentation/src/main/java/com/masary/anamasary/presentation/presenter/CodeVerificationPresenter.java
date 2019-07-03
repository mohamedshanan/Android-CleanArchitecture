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
package com.masary.anamasary.presentation.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.masary.anamasary.domain.EncryptionKey;
import com.masary.anamasary.domain.exception.DefaultErrorBundle;
import com.masary.anamasary.domain.exception.ErrorBundle;
import com.masary.anamasary.domain.interactor.DefaultObserver;
import com.masary.anamasary.domain.interactor.VerifyCode;
import com.masary.anamasary.presentation.exception.ErrorMessageFactory;
import com.masary.anamasary.presentation.exception.InvalidVerificationCodeException;
import com.masary.anamasary.presentation.internal.di.PerActivity;
import com.masary.anamasary.presentation.mapper.EncryptionKeyModelDataMapper;
import com.masary.anamasary.presentation.model.EncryptionKeyModel;
import com.masary.anamasary.presentation.view.CodeVerificationView;

import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class CodeVerificationPresenter implements Presenter {

    private final VerifyCode verifyCodeUseCase;
    private final EncryptionKeyModelDataMapper encryptionKeyModelDataMapper;
    private CodeVerificationView viewDetailsView;

  @Inject
  public CodeVerificationPresenter(VerifyCode verifyCodeUseCase,
                                   EncryptionKeyModelDataMapper encryptionKeyModelDataMapper) {
      this.verifyCodeUseCase = verifyCodeUseCase;
      this.encryptionKeyModelDataMapper = encryptionKeyModelDataMapper;
  }

    public void setView(@NonNull CodeVerificationView view) {
    this.viewDetailsView = view;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
      this.verifyCodeUseCase.dispose();
    this.viewDetailsView = null;
  }

  /**
   * Initializes the presenter by showing/hiding proper views
   * and retrieving user details.
   */
  public void initialize() {
    this.hideViewRetry();
    this.showViewLoading();
  }

    public void verifyCode(String verificationCode) {
        if (TextUtils.isEmpty(verificationCode)) {
            showErrorMessage(new DefaultErrorBundle(new InvalidVerificationCodeException()));
            return;
        }
        this.verifyCodeUseCase.execute(new EncryptionKeyObserver(), VerifyCode.Params.buildParams(verificationCode));
  }

  private void showViewLoading() {
    this.viewDetailsView.showLoading();
  }

  private void hideViewLoading() {
    this.viewDetailsView.hideLoading();
  }

  private void showViewRetry() {
    this.viewDetailsView.showRetry();
  }

  private void hideViewRetry() {
    this.viewDetailsView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.context(),
        errorBundle.getException());
    this.viewDetailsView.showError(errorMessage);
  }

    private void showUserDetailsInView(EncryptionKey encryptionKey) {
        final EncryptionKeyModel encryptionKeyModel = this.encryptionKeyModelDataMapper.transform(encryptionKey);
        this.viewDetailsView.renderKey(encryptionKeyModel);
  }

    private final class EncryptionKeyObserver extends DefaultObserver<EncryptionKey> {

    @Override public void onComplete() {
        CodeVerificationPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
        CodeVerificationPresenter.this.hideViewLoading();
        CodeVerificationPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
//      CodeVerificationPresenter.this.showViewRetry();
    }

        @Override
        public void onNext(EncryptionKey encryptionKey) {
            CodeVerificationPresenter.this.showUserDetailsInView(encryptionKey);
    }
  }
}
