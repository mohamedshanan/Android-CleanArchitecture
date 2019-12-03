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
package com.masary.anamasary.data.cache;

import android.content.Context;

import com.masary.anamasary.data.cache.serializer.Serializer;
import com.masary.anamasary.data.entity.EncryptionKeyEntity;
import com.masary.anamasary.data.exception.KeyNotFoundException;
import com.masary.anamasary.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link EncryptionKeyCache} implementation.
 */
@Singleton
public class EncryptionKeyCacheImpl implements EncryptionKeyCache {

    private static final String SETTINGS_FILE_NAME = "com.masary.anamasary.SETTINGS";
  private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "key_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final File cacheDir;
  private final Serializer serializer;
  private final FileManager fileManager;
  private final ThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link EncryptionKeyCacheImpl}.
   *
   * @param context A
   * @param serializer {@link Serializer} for object serialization.
   * @param fileManager {@link FileManager} for saving serialized objects to the file system.
   */
  @Inject
  public EncryptionKeyCacheImpl(Context context, Serializer serializer,
                                FileManager fileManager, ThreadExecutor executor) {
    if (context == null || serializer == null || fileManager == null || executor == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.context = context.getApplicationContext();
    this.cacheDir = this.context.getCacheDir();
    this.serializer = serializer;
    this.fileManager = fileManager;
    this.threadExecutor = executor;
  }

    @Override
    public Observable<EncryptionKeyEntity> get() {
    return Observable.create(emitter -> {
        final File encryptionKeyFile = EncryptionKeyCacheImpl.this.buildFile();
        final String fileContent = EncryptionKeyCacheImpl.this.fileManager.readFileContent(encryptionKeyFile);
        final EncryptionKeyEntity encryptionKeyEntity =
                EncryptionKeyCacheImpl.this.serializer.deserialize(fileContent, EncryptionKeyEntity.class);

        if (encryptionKeyEntity != null) {
            emitter.onNext(encryptionKeyEntity);
        emitter.onComplete();
      } else {
            emitter.onError(new KeyNotFoundException());
      }
    });
  }

    @Override
    public void put(EncryptionKeyEntity encryptionKeyEntity) {
        if (encryptionKeyEntity != null) {
            final File encryptionKeyEntityFile = this.buildFile();
            if (!isCached()) {
                final String jsonString = this.serializer.serialize(encryptionKeyEntity, EncryptionKeyEntity.class);
                this.executeAsynchronously(new CacheWriter(this.fileManager, encryptionKeyEntityFile, jsonString));
        setLastCacheUpdateTimeMillis();
      }
    }
  }

    @Override
    public boolean isCached() {
        final File userEntityFile = this.buildFile();
    return this.fileManager.exists(userEntityFile);
  }

  @Override public boolean isExpired() {
    long currentTime = System.currentTimeMillis();
    long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

    boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

    if (expired) {
      this.evictAll();
    }

    return expired;
  }

  @Override public void evictAll() {
    this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
  }

  /**
   * Build a file, used to be inserted in the disk cache.
   *
   * @return A valid file.
   */
  private File buildFile() {
    final StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(this.cacheDir.getPath());
    fileNameBuilder.append(File.separator);
    fileNameBuilder.append(DEFAULT_FILE_NAME);

    return new File(fileNameBuilder.toString());
  }

  /**
   * Set in millis, the last time the cache was accessed.
   */
  private void setLastCacheUpdateTimeMillis() {
    final long currentMillis = System.currentTimeMillis();
    this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
  }

  /**
   * Get in millis, the last time the cache was accessed.
   */
  private long getLastCacheUpdateTimeMillis() {
    return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
        SETTINGS_KEY_LAST_CACHE_UPDATE);
  }

  /**
   * Executes a {@link Runnable} in another Thread.
   *
   * @param runnable {@link Runnable} to execute
   */
  private void executeAsynchronously(Runnable runnable) {
    this.threadExecutor.execute(runnable);
  }

  /**
   * {@link Runnable} class for writing to disk.
   */
  private static class CacheWriter implements Runnable {
    private final FileManager fileManager;
    private final File fileToWrite;
    private final String fileContent;

    CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
      this.fileManager = fileManager;
      this.fileToWrite = fileToWrite;
      this.fileContent = fileContent;
    }

    @Override public void run() {
      this.fileManager.writeToFile(fileToWrite, fileContent);
    }
  }

  /**
   * {@link Runnable} class for evicting all the cached files
   */
  private static class CacheEvictor implements Runnable {
    private final FileManager fileManager;
    private final File cacheDir;

    CacheEvictor(FileManager fileManager, File cacheDir) {
      this.fileManager = fileManager;
      this.cacheDir = cacheDir;
    }

    @Override public void run() {
      this.fileManager.clearDirectory(this.cacheDir);
    }
  }
}
