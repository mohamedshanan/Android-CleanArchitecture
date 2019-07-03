/**
 * Copyright (C) 2014 anamasary.org. All rights reserved.
 * @author Fernando Cejas (the anamasary coder)
 */
package com.masary.anamasary.presentation.view;

import com.masary.anamasary.presentation.model.EncryptionKeyModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a user profile.
 */
public interface CodeVerificationView extends LoadDataView {
  /**
   * Render a user in the UI.
   *
   * @param encryptionKeyModel The {@link EncryptionKeyModel} that will be shown.
   */
  void renderKey(EncryptionKeyModel encryptionKeyModel);
}
