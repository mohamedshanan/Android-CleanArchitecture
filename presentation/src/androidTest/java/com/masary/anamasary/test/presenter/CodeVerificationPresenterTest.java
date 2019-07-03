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
package com.masary.anamasary.test.presenter;

import android.content.Context;

import com.masary.anamasary.domain.interactor.VerifyCode;
import com.masary.anamasary.presentation.mapper.EncryptionKeyModelDataMapper;
import com.masary.anamasary.presentation.presenter.CodeVerificationPresenter;
import com.masary.anamasary.presentation.view.CodeVerificationView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.observers.DisposableObserver;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CodeVerificationPresenterTest {

    private static final String FAKE_VERIFICATION_CODE = "1234";

    private CodeVerificationPresenter codeVerificationPresenter;

  @Mock private Context mockContext;
    @Mock
    private CodeVerificationView mockCodeVerificationView;
    @Mock
    private VerifyCode mockVerifyCodeUseCase;
    @Mock
    private EncryptionKeyModelDataMapper mockEncryptionKeyModelDataMapper;

  @Before
  public void setUp() {
      codeVerificationPresenter = new CodeVerificationPresenter(mockVerifyCodeUseCase, mockEncryptionKeyModelDataMapper);
      codeVerificationPresenter.setView(mockCodeVerificationView);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUserDetailsPresenterInitialize() {
      given(mockCodeVerificationView.context()).willReturn(mockContext);

      codeVerificationPresenter.initialize();

      verify(mockCodeVerificationView).hideRetry();
      verify(mockCodeVerificationView).showLoading();
      verify(mockVerifyCodeUseCase).execute(any(DisposableObserver.class), any(VerifyCode.Params.class));
  }
}
