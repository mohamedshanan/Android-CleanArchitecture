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
package com.masary.anamasary.presentation.internal.di.components;

import com.masary.anamasary.presentation.internal.di.PerActivity;
import com.masary.anamasary.presentation.internal.di.modules.ActivityModule;
import com.masary.anamasary.presentation.internal.di.modules.EncryptionKeyModule;
import com.masary.anamasary.presentation.view.fragment.CodeVerificationFragment;

import dagger.Component;

/**
 * A scope {@link PerActivity} component.
 * Injects user specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, EncryptionKeyModule.class})
public interface EncryptionKeyComponent extends ActivityComponent {
    void inject(CodeVerificationFragment codeVerificationFragment);
}
