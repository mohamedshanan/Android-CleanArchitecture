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

import com.masary.anamasary.domain.executor.PostExecutionThread;
import com.masary.anamasary.domain.executor.ThreadExecutor;
import com.masary.anamasary.domain.repository.EncryptionKeyRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class VerifyCodeTest {

    private static final String VERIFICATION_CODE = "1234";

    private VerifyCode verifyCode;

    @Mock
    private EncryptionKeyRepository mockKeyRepository;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
      verifyCode = new VerifyCode(mockKeyRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testVerifyUseCaseObservableHappyCase() {
      verifyCode.buildUseCaseObservable(VerifyCode.Params.buildParams(VERIFICATION_CODE));

      verify(mockKeyRepository).verifyCode(VERIFICATION_CODE);
      verifyNoMoreInteractions(mockKeyRepository);
    verifyZeroInteractions(mockPostExecutionThread);
    verifyZeroInteractions(mockThreadExecutor);
  }

  @Test
  public void testShouldFailWhenNoOrEmptyParameters() {
    expectedException.expect(NullPointerException.class);
      verifyCode.buildUseCaseObservable(null);
  }
}
