/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.masary.anamasary.data.repository;

import com.masary.anamasary.data.entity.mapper.EncryptionKeyEntityDataMapper;
import com.masary.anamasary.data.repository.datasource.EncryptionKeyDataStore;
import com.masary.anamasary.data.repository.datasource.EncryptionKeyDataStoreFactory;
import com.masary.anamasary.domain.EncryptionKey;
import com.masary.anamasary.domain.repository.EncryptionKeyRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link EncryptionKeyRepository} for verifying verification code and retrieving encryption key.
 */
@Singleton
public class EncryptionKeyDataRepository implements EncryptionKeyRepository {

    private final EncryptionKeyDataStoreFactory encryptionKeyDataStoreFactory;
    private final EncryptionKeyEntityDataMapper userEntityDataMapper;

    /**
     * Constructs a {@link EncryptionKeyRepository}.
     *
     * @param dataStoreFactory              A factory to construct different data source implementations.
     * @param encryptionKeyEntityDataMapper {@link EncryptionKeyEntityDataMapper}.
     */
    @Inject
    public EncryptionKeyDataRepository(EncryptionKeyDataStoreFactory dataStoreFactory,
                                       EncryptionKeyEntityDataMapper encryptionKeyEntityDataMapper) {
        this.encryptionKeyDataStoreFactory = dataStoreFactory;
        this.userEntityDataMapper = encryptionKeyEntityDataMapper;
    }

    @Override
    public Observable<EncryptionKey> verifyCode(String verificationCode) {
        final EncryptionKeyDataStore encryptionKeyDataStore = this.encryptionKeyDataStoreFactory.create(verificationCode);
        return encryptionKeyDataStore.verifyCode(verificationCode).map(this.userEntityDataMapper::transform);
    }
}
