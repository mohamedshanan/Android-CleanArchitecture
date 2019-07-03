/**
 * Copyright (C) 2014 anamasary.org. All rights reserved.
 *
 * @author Fernando Cejas (the anamasary coder)
 */
package com.masary.anamasary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.masary.anamasary.presentation.R;
import com.masary.anamasary.presentation.internal.di.HasComponent;
import com.masary.anamasary.presentation.internal.di.components.DaggerEncryptionKeyComponent;
import com.masary.anamasary.presentation.internal.di.components.EncryptionKeyComponent;
import com.masary.anamasary.presentation.view.fragment.CodeVerificationFragment;

/**
 * Activity that shows details of a certain user.
 */
public class CodeVerificationActivity extends BaseActivity implements HasComponent<EncryptionKeyComponent> {

  private static final String INTENT_EXTRA_PARAM_USER_ID = "org.anamasary.INTENT_PARAM_USER_ID";
    private static final String INSTANCE_STATE_PARAM_VERIFICATION_CODE = "org.anamasary.STATE_PARAM_USER_ID";
    private String verificationCode;
    private EncryptionKeyComponent encryptionKeyComponent;

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, CodeVerificationActivity.class);
    return callingIntent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.activity_layout);

    this.initializeActivity(savedInstanceState);
    this.initializeInjector();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    if (outState != null) {
        outState.putString(INSTANCE_STATE_PARAM_VERIFICATION_CODE, this.verificationCode);
    }
    super.onSaveInstanceState(outState);
  }

  /**
   * Initializes this activity.
   */
  private void initializeActivity(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
        addFragment(R.id.fragmentContainer, CodeVerificationFragment.newInstance(verificationCode));
    }
  }

  private void initializeInjector() {
      this.encryptionKeyComponent = DaggerEncryptionKeyComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

    @Override
    public EncryptionKeyComponent getComponent() {
        return encryptionKeyComponent;
  }
}
