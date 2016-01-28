/*
 * LoginActController.java
 * classes : com.example.activitycontroller.LoginActController
 * @author zenghui
 * V 1.0.0
 * Create at 2015-10-17 下午1:15:48
 */
package com.example.zenghui.overyearspaper.Controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.BaseActivity;
import com.example.zenghui.overyearspaper.MainActivity;
import com.example.zenghui.overyearspaper.Utils.Common;
import com.example.zenghui.overyearspaper.View.DrawableClickableEditText;
import com.example.zenghui.overyearspaper.View.ShadowView;
import com.example.zenghui.overyearspaper.View.SwitchChoice;
import com.example.zenghui.overyearspapers.R;


/**
 * com.example.activitycontroller.LoginActController
 *
 * @author zenghui <br/>
 *         create at 2015-10-17 下午1:15:48
 */
public class LoginActController {
    Activity act;
    SwitchChoice check;
    private DrawableClickableEditText mPhoneView;
    private DrawableClickableEditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private CheckBox eye;
    private TextView forgetPassword;

    private LinearLayout phoneLayout, pwdLayout;
    ShadowView mLoginButton;

    public LoginActController(Activity activity) {
        act = activity;
    }


    public SwitchChoice initDate(EditText nameText, EditText pwdText, SwitchChoice check) {
        SharedPreferences sp = act.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        nameText.setText(sp.getString("USER_NAME", ""));
        pwdText.setText(sp.getString("USER_PWD", ""));
        if (sp.getBoolean("USER_CHECK", false)) {
            check = new SwitchChoice(act, null, false);
        } else {
            check = new SwitchChoice(act, null);
        }
        return check;
    }

    public void commitDate(EditText nameText, EditText pwdText) {
        SharedPreferences sp = act.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Editor edt = sp.edit();
        edt.putBoolean("USER_CHECK", true);
        edt.putString("USER_NAME", nameText.getText().toString().trim());
        edt.putString("USER_PWD", pwdText.getText().toString().trim());
        edt.commit();
    }

    public void clearDate() {
        SharedPreferences sp = act.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Editor edt = sp.edit();
        edt.putBoolean("USER_CHECK", false);
        edt.putString("USER_NAME", "");
        edt.putString("USER_PWD", "");
        edt.commit();
    }

    public <T> void startIntent(Class<T> class1, Bundle bd) {
        Intent it = new Intent(act, class1);
        if (bd != null)
            it.putExtras(bd);
        act.startActivity(it);
    }

    public void setCheckView(SwitchChoice check, int w, int h) {
        LayoutParams lp = new LinearLayout.LayoutParams(w, h);
        check.setLayoutParams(lp);
        LinearLayout linear = (LinearLayout) act.findViewById(R.id.checkLayout);
        linear.addView(check, 0);
    }

    public void showProgress(final boolean show) {
        int shortAnimTime = act.getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginButton.setVisibility(show ? View.GONE : View.VISIBLE);
        forgetPassword.setVisibility(show ? View.GONE : View.VISIBLE);
        check.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }


    public void initView() {
        mPhoneView = (DrawableClickableEditText) act.findViewById(R.id.phone);
        forgetPassword = (TextView) act.findViewById(R.id.forget_password);
        eye = (CheckBox) act.findViewById(R.id.eye);
        mPasswordView = (DrawableClickableEditText) act.findViewById(R.id.password);
        phoneLayout = (LinearLayout) act.findViewById(R.id.phoneLayout);
        pwdLayout = (LinearLayout) act.findViewById(R.id.pwdLayout);
        mLoginFormView = act.findViewById(R.id.login_form);
        mProgressView = act.findViewById(R.id.login_progress);
        mLoginButton = (ShadowView) act.findViewById(R.id.sign_in_button);
        forgetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetPassword.getPaint().setAntiAlias(true);
        check = initDate(mPhoneView, mPasswordView, check);
        setCheckView(check, Common.screamWidth / 7, Common.screamHeight / 16);
        initListener();
    }

    void initListener() {
        mPhoneView.setDrawableRightListener(new DrawableClickableEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                mPhoneView.setText("");
                mPhoneView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });
        mPhoneView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    phoneLayout.setBackgroundResource(R.drawable.edit_form_off);
                    mPhoneView.setTextSize(TypedValue.COMPLEX_UNIT_PX, act.getResources().getDimensionPixelSize(R.dimen.normal_text_font));
                    mPhoneView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    phoneLayout.setBackgroundResource(R.drawable.edit_form_on);
                    Common.showSoftIput(act, mPhoneView);
                    mPhoneView.setTextSize(TypedValue.COMPLEX_UNIT_PX, act.getResources().getDimensionPixelSize(R.dimen.larger_text_font));
                    if (mPhoneView.getText().length() == 0) {
                        mPhoneView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    } else {
                        mPhoneView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_delete, 0);
                    }
                }
            }
        });
        mPhoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mPhoneView.getText().length() == 0) {
                    mPhoneView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    mPhoneView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_delete, 0);
                }
                if (Common.isPhoneValid(mPhoneView.getText().toString())) {
                    mPhoneView.setTextColor(act.getResources().getColor(R.color.edit_text_color));
                }
            }
        });

        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Common.showSoftIput(act, mPasswordView);
                    pwdLayout.setBackgroundResource(R.drawable.edit_form_on);
                    mPasswordView.setTextSize(TypedValue.COMPLEX_UNIT_PX, act.getResources().getDimensionPixelSize(R.dimen.larger_text_font));
                    if (mPasswordView.getText().length() == 0) {
                        mPasswordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    } else {
                        mPasswordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_delete, 0);
                    }
                    eye.setVisibility(View.VISIBLE);
                } else {
                    pwdLayout.setBackgroundResource(R.drawable.edit_form_off);
                    mPasswordView.setTextSize(TypedValue.COMPLEX_UNIT_PX, act.getResources().getDimensionPixelSize(R.dimen.normal_text_font));
                    mPasswordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    eye.setVisibility(View.INVISIBLE);

                }

            }
        });
        mPasswordView.setDrawableRightListener(new DrawableClickableEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                mPasswordView.setText("");
                mPasswordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });
        mPasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mPasswordView.getText().length() == 0) {
                    mPasswordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    mPasswordView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_delete, 0);
                }
            }
        });
        eye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mPasswordView != null) {
                    mPasswordView.setInputType(isChecked ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordView.setSelection(mPasswordView.getText().length());
                }
            }
        });


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        mLoginFormView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) act.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mLoginFormView.getWindowToken(), 0);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void attemptLogin() {
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();


        if (TextUtils.isEmpty(phone)) {
            ((BaseActivity) act).showToast(String.format(act.getString(R.string.error_field_required), act.getString(R.string.phone)));
            return;
        } else if (!Common.isPhoneValid(phone)) {
            ((BaseActivity) act).showToast(act.getResources().getString(R.string.error_invalid_phone_number));
            mPhoneView.setTextColor(act.getResources().getColor(R.color.orange));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ((BaseActivity) act).showToast(String.format(act.getString(R.string.error_field_required), act.getString(R.string.password)));
            return;
        } else if (!TextUtils.isEmpty(password) && !Common.isPasswordValid(password)) {
            ((BaseActivity) act).showToast(act.getString(R.string.error_invalid_password));
            return;
        }
        if (check.isChecked()) {
            commitDate(mPhoneView, mPasswordView);
        } else {
            clearDate();
        }

        showProgress(true);
        startIntent(MainActivity.class, null);
        act.finish();
//        login(phone, password);
    }
}
