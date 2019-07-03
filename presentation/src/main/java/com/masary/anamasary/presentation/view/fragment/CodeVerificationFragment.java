/**
 * Copyright (C) 2014 anamasary.org. All rights reserved.
 * @author Fernando Cejas (the anamasary coder)
 */
package com.masary.anamasary.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.masary.anamasary.presentation.R;
import com.masary.anamasary.presentation.internal.di.components.EncryptionKeyComponent;
import com.masary.anamasary.presentation.model.EncryptionKeyModel;
import com.masary.anamasary.presentation.presenter.CodeVerificationPresenter;
import com.masary.anamasary.presentation.view.CodeVerificationView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment that takes verification code, verify it and show key.
 */
public class CodeVerificationFragment extends BaseFragment implements CodeVerificationView {
    private static final String PARAM_VERIFICATION_CODE = "param_verification_code";

    @Inject
    CodeVerificationPresenter codeVerificationPresenter;


    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;

  @Bind(R.id.rl_progress) RelativeLayout rl_progress;
  @Bind(R.id.rl_retry) RelativeLayout rl_retry;
    @Bind(R.id.btVerify)
    Button btVerify;
  @Bind(R.id.bt_retry) Button bt_retry;

    public CodeVerificationFragment() {
    setRetainInstance(true);
  }

    public static CodeVerificationFragment newInstance(String verificationCode) {
        final CodeVerificationFragment codeVerificationFragment = new CodeVerificationFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(PARAM_VERIFICATION_CODE, verificationCode);
        codeVerificationFragment.setArguments(arguments);
        return codeVerificationFragment;
    }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      this.getComponent(EncryptionKeyComponent.class).inject(this);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
      final View fragmentView = inflater.inflate(R.layout.fragment_code_verification, container, false);
    ButterKnife.bind(this, fragmentView);
    return fragmentView;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
      this.codeVerificationPresenter.setView(this);
  }

  @Override public void onResume() {
    super.onResume();
      this.codeVerificationPresenter.resume();
  }

  @Override public void onPause() {
    super.onPause();
      this.codeVerificationPresenter.pause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
      this.codeVerificationPresenter.destroy();
  }

    @Override
    public void renderKey(EncryptionKeyModel encryptionKeyModel) {
        if (encryptionKeyModel != null) {
            Toast.makeText(getActivity(),
                    "Success: your key is " + encryptionKeyModel.getKey(),
                    Toast.LENGTH_LONG).show();
    }
  }

  @Override public void showLoading() {
    this.rl_progress.setVisibility(View.VISIBLE);
    this.getActivity().setProgressBarIndeterminateVisibility(true);
  }

  @Override public void hideLoading() {
    this.rl_progress.setVisibility(View.GONE);
    this.getActivity().setProgressBarIndeterminateVisibility(false);
  }

  @Override public void showRetry() {
    this.rl_retry.setVisibility(View.VISIBLE);
  }

  @Override public void hideRetry() {
    this.rl_retry.setVisibility(View.GONE);
  }

  @Override public void showError(String message) {
    this.showToastMessage(message);
  }

  @Override public Context context() {
    return getActivity().getApplicationContext();
  }

    @OnClick(R.id.btVerify)
    void onButtonVerifyClick() {
        CodeVerificationFragment.this.verifyCode();
  }

  /**
   * request code verification.
   */
  private void verifyCode() {
      if (this.codeVerificationPresenter != null) {
          this.codeVerificationPresenter.verifyCode(readVerificationCode());
      }
  }

    private String readVerificationCode() {
        return TextUtils.isEmpty(etVerificationCode.getText().toString()) ? null : etVerificationCode.getText().toString();
    }

}