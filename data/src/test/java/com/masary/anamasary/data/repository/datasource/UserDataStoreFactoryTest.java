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
package com.masary.anamasary.data.repository.datasource;

import com.masary.anamasary.data.ApplicationTestCase;
import com.masary.anamasary.data.cache.EncryptionKeyCacheImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserDataStoreFactoryTest extends ApplicationTestCase {

  private static final String FAKE_KEY = "ABC123";

  private EncryptionKeyDataStoreFactory dataStoreFactory;

  @Mock
  private EncryptionKeyCacheImpl mockCache;

  @Before
  public void setUp() {
    dataStoreFactory = new EncryptionKeyDataStoreFactory(RuntimeEnvironment.application, mockCache);
  }

  @Test
  public void testCreateDiskDataStore() {
    given(mockCache.isCached()).willReturn(true);
    given(mockCache.isExpired()).willReturn(false);

    EncryptionKeyDataStore dataStore = dataStoreFactory.create(FAKE_KEY);

    assertThat(dataStore, is(notNullValue()));
    assertThat(dataStore, is(instanceOf(EncryptionKeyDataStore.class)));

    verify(mockCache).isCached();
    verify(mockCache).isExpired();
  }

  @Test
  public void testCreateCloudDataStore() {
    given(mockCache.isExpired()).willReturn(true);
    given(mockCache.isCached()).willReturn(false);

    EncryptionKeyDataStore dataStore = dataStoreFactory.create(FAKE_KEY);

    assertThat(dataStore, is(notNullValue()));
    assertThat(dataStore, is(instanceOf(CloudEncryptionKeyDataStore.class)));

    verify(mockCache).isExpired();
  }
}
