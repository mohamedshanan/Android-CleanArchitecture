/**
 * Copyright (C) 2014 anamasary.org. All rights reserved.
 * @author Fernando Cejas (the anamasary coder)
 */
package com.masary.anamasary.presentation.view;

import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadDataView {
  /**
   * Show a view with a progress bar indicating a loading process.
   */
  void showLoading();

  /**
   * Hide a loading view.
   */
  void hideLoading();

  /**
   * Show a retry view in case of an error when retrieving data.
   */
  void showRetry();

  /**
   * Hide a retry view shown if there was an error when retrieving data.
   */
  void hideRetry();

  /**
   * Show an error message
   *
   * @param message A string representing an error.
   */
  void showError(String message);

  /**
   * Get a {@link android.content.Context}.
   */
  Context context();
}
