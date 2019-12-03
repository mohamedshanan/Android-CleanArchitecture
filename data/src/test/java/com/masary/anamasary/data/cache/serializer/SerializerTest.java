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
package com.masary.anamasary.data.cache.serializer;

import com.masary.anamasary.data.entity.EncryptionKeyEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SerializerTest {

    private static final String JSON_RESPONSE = "{\n"
            + "    \"key\": \"ASD123456\",\n"
            + "}";

    private Serializer serializer;

    @Before
    public void setUp() {
        serializer = new Serializer();
    }

    @Test
    public void testSerializeHappyCase() {
        final EncryptionKeyEntity userEntityOne = serializer.deserialize(JSON_RESPONSE, EncryptionKeyEntity.class);
        final String jsonString = serializer.serialize(userEntityOne, EncryptionKeyEntity.class);
        final EncryptionKeyEntity userEntityTwo = serializer.deserialize(jsonString, EncryptionKeyEntity.class);

        assertThat(userEntityOne.getKey(), is(userEntityTwo.getKey()));
    }

    @Test
    public void testDesearializeHappyCase() {
        final EncryptionKeyEntity keyEntity = serializer.deserialize(JSON_RESPONSE, EncryptionKeyEntity.class);

        assertThat(keyEntity.getKey(), is("ASD123456"));
    }
}
