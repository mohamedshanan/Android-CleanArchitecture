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
package com.masary.anamasary.data.repository;

import com.masary.anamasary.data.entity.EncryptionKeyEntity;
import com.masary.anamasary.data.entity.mapper.EncryptionKeyEntityDataMapper;
import com.masary.anamasary.data.repository.datasource.EncryptionKeyDataStore;
import com.masary.anamasary.data.repository.datasource.EncryptionKeyDataStoreFactory;
import com.masary.anamasary.domain.EncryptionKey;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserDataRepositoryTest {

    private static final String FAKE_VERIFICATION_CODE = "1234";

    private EncryptionKeyDataRepository encryptionKeyDataRepository;

    @Mock
    private EncryptionKeyDataStoreFactory mockKeyDataStoreFactory;
    @Mock
    private EncryptionKeyEntityDataMapper mockKeyEntityDataMapper;
    @Mock
    private EncryptionKeyDataStore mockKeyDataStore;
    @Mock
    private EncryptionKeyEntity mockKeyEntity;
    @Mock
    private EncryptionKey mockKey;

  @Before
  public void setUp() {
      encryptionKeyDataRepository = new EncryptionKeyDataRepository(mockKeyDataStoreFactory, mockKeyEntityDataMapper);
      given(mockKeyDataStoreFactory.create(anyString())).willReturn(mockKeyDataStore);
      given(mockKeyDataStoreFactory.createCloudDataStore(anyString())).willReturn(mockKeyDataStore);
  }


  @Test
  public void testGetUserHappyCase() {
      EncryptionKeyEntity encryptionKeyEntity = new EncryptionKeyEntity();
      given(mockKeyDataStore.verifyCode(FAKE_VERIFICATION_CODE)).willReturn(Observable.just(encryptionKeyEntity));
      encryptionKeyDataRepository.verifyCode(FAKE_VERIFICATION_CODE);

      verify(mockKeyDataStoreFactory).create(FAKE_VERIFICATION_CODE);
      verify(mockKeyDataStore).verifyCode(FAKE_VERIFICATION_CODE);
  }
}
