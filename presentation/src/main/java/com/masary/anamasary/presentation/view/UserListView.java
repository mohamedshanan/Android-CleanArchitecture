/**
 * Copyright (C) 2014 anamasary.org. All rights reserved.
 * @author Fernando Cejas (the anamasary coder)
 */
package com.masary.anamasary.presentation.view;

import com.masary.anamasary.presentation.model.EncryptionKeyModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link EncryptionKeyModel}.
 */
public interface UserListView extends LoadDataView {
  /**
   * Render a user list in the UI.
   *
   * @param encryptionKeyModelCollection The collection of {@link EncryptionKeyModel} that will be shown.
   */
  void renderUserList(Collection<EncryptionKeyModel> encryptionKeyModelCollection);

  /**
   * View a {@link EncryptionKeyModel} profile/details.
   *
   * @param encryptionKeyModel The user that will be shown.
   */
  void viewUser(EncryptionKeyModel encryptionKeyModel);
}
