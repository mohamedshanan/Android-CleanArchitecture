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
package com.masary.anamasary.presentation.internal.di.modules;

import android.content.Context;

import com.masary.anamasary.data.cache.EncryptionKeyCache;
import com.masary.anamasary.data.cache.EncryptionKeyCacheImpl;
import com.masary.anamasary.data.executor.JobExecutor;
import com.masary.anamasary.data.repository.EncryptionKeyDataRepository;
import com.masary.anamasary.domain.executor.PostExecutionThread;
import com.masary.anamasary.domain.executor.ThreadExecutor;
import com.masary.anamasary.domain.repository.EncryptionKeyRepository;
import com.masary.anamasary.presentation.AndroidApplication;
import com.masary.anamasary.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

    @Provides
    @Singleton
    EncryptionKeyCache provideEncryptionKeyCache(EncryptionKeyCacheImpl keyCache) {
        return keyCache;
    }

    @Provides
    @Singleton
    EncryptionKeyRepository provideEncryptionKeyRepository(EncryptionKeyDataRepository keyDataRepository) {
        return keyDataRepository;
  }
}
