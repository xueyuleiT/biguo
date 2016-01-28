package com.example.zenghui.overyearspaper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.zenghui.overyearspaper.Utils.Common;
import com.example.zenghui.overyearspaper.View.DrawableClickableEditText;
import com.example.zenghui.overyearspapers.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {
    // UI references.
    private DrawableClickableEditText mPhoneView;
    private DrawableClickableEditText mPasswordView, mSendVerifyMsg;
    private View mProgressView;
    private View mRegisterFormView;
    private View mPasswordContainer;
    private CheckBox eye;
    private Button sendVerifyMsgButton;
    private Toolbar mToolbar;
    private TextView mTitle;
    private int failedSms;
    private CountDownTimer timer;
    private TextView agreementTextView0, agreementTextView1;
    private CheckBox agreement;
    private String previousPhone;
    private String previousPhoneViewNumber;
    Button registerButton;

    @Override
    void initViews() {
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.title);
        mToolbar.setTitle("");
        mTitle.setText(getString(R.string.register));

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initSdk();
    }

    EventHandler eventHandler;

    private void initSdk() {

        SMSSDK.initSDK(this, "f1474c747d34", "bafd28ecd03852b1a8ce7963de633bc6");
        eventHandler = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                Log.d("", "result ======>" + result);
                Log.d("", "event ======>" + event);
                Message msg = handler.obtainMessage();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eventHandler);

    }

    @Override
    void initDatas() {
    }

    @Override
    void doOther() {

    }

    private void initView() {
        mPhoneView = (DrawableClickableEditText) findViewById(R.id.phone);

        mPhoneView.setDrawableRightListener(new DrawableClickableEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                if (mPhoneView.isEnabled()) {
                    mPhoneView.setText("");
                    mPhoneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_shoujihao, 0, 0, 0);
                }
            }
        });

        mPhoneView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mPhoneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_shoujihao, 0, 0, 0);
                } else {
                    if (mPhoneView.getText().length() == 0) {
                        mPhoneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_shoujihao, 0, 0, 0);
                    } else {
                        mPhoneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_shoujihao, 0, R.drawable.icon_delete, 0);
                    }
                }
            }
        });

        mPhoneView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String phoneNumber = mPhoneView.getText().toString();
                if (Common.isPhoneValid(phoneNumber)) {
                    previousPhoneViewNumber = phoneNumber;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mPhoneView.getText().length() == 0) {
                    mPhoneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_shoujihao, 0, 0, 0);
                } else {
                    mPhoneView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_shoujihao, 0, R.drawable.icon_delete, 0);
                }

                if (Common.isPhoneValid(mPhoneView.getText().toString())) {
                    mPhoneView.setTextColor(getResources().getColor(R.color.edit_text_color));
                    if (!TextUtils.isEmpty(previousPhoneViewNumber)) {
                        if (!mPhoneView.getText().toString().equals(previousPhoneViewNumber)) {
                            if (!thread.isAlive()) {
                                sendVerifyMsgButton.setEnabled(true);
                            }
                            failedSms = 0;
                            sendVerifyMsgButton.setText(R.string.send_verify_msg);
                            sendVerifyMsgButton.setBackgroundResource(R.drawable.sms_captcha_on);
                        }
                    } else {
                        sendVerifyMsgButton.setBackgroundResource(R.drawable.sms_captcha_on);
                    }
                } else {
                    sendVerifyMsgButton.setBackgroundResource(R.drawable.sms_captcha_off);
                }
            }
        });

        mPasswordContainer = findViewById(R.id.password_container);
        eye = (CheckBox) findViewById(R.id.eye);

        mPasswordView = (DrawableClickableEditText) findViewById(R.id.password);

        mPasswordView.setDrawableRightListener(new DrawableClickableEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                if (mPasswordView.isEnabled()) {
                    mPasswordView.setText("");
                    mPasswordView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mima, 0, 0, 0);
                }
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
                    mPasswordView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mima, 0, 0, 0);
                } else {
                    mPasswordView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mima, 0, R.drawable.icon_delete, 0);
                }
            }
        });

        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mPasswordContainer != null)
                        mPasswordContainer.setBackgroundResource(R.drawable.edit_form_on);
                    if (mPasswordView.getText().length() == 0) {
                        mPasswordView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mima, 0, 0, 0);
                    } else {
                        mPasswordView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mima, 0, R.drawable.icon_delete, 0);
                    }
                    eye.setVisibility(View.VISIBLE);
                } else {
                    if (mPasswordContainer != null)
                        mPasswordContainer.setBackgroundResource(R.drawable.edit_form_off);
                    mPasswordView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_mima, 0, 0, 0);
                    eye.setVisibility(View.INVISIBLE);

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


        mSendVerifyMsg = (DrawableClickableEditText) findViewById(R.id.sms_captcha);

        mSendVerifyMsg.setDrawableRightListener(new DrawableClickableEditText.DrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                if (mSendVerifyMsg.isEnabled()) {
                    mSendVerifyMsg.setText("");
                    mSendVerifyMsg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_yanzhengma, 0, 0, 0);
                }
            }
        });

        mSendVerifyMsg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mSendVerifyMsg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_yanzhengma, 0, 0, 0);
//                    if(mSendVerifyMsg.getText().length()>=4){
//                        String phone = mPhoneView.getText().toString();
//                        String captcha =mSendVerifyMsg.getText().toString();
//                        if(!TextUtils.isEmpty(previousPhone) ){
//                            if(!TextUtils.isEmpty(phone) && !previousPhone.equals(phone)){
//                                checkMobile(phone,captcha,0);
//                            }
//                        }else{
//                            checkMobile(phone,captcha,0);
//                        }
//                    }
                } else {
                    if (mSendVerifyMsg.getText().length() == 0) {
                        mSendVerifyMsg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_yanzhengma, 0, 0, 0);
                    } else {
                        mSendVerifyMsg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_yanzhengma, 0, R.drawable.icon_delete, 0);
                    }
                }
            }
        });

        mSendVerifyMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mSendVerifyMsg.getText().length() == 0) {
                    mSendVerifyMsg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_yanzhengma, 0, 0, 0);
                } else {
                    mSendVerifyMsg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_yanzhengma, 0, R.drawable.icon_delete, 0);
                }
            }
        });
        sendVerifyMsgButton = (Button) findViewById(R.id.sendVerifyMsg);
        sendVerifyMsgButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister(R.id.sendVerifyMsg);
            }
        });

        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister(R.id.register_button);
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mRegisterFormView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mRegisterFormView.getWindowToken(), 0);
            }
        });
        mProgressView = findViewById(R.id.register_progress);

    }

    private void attemptRegister(int viewId) {

        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();
        String message = mSendVerifyMsg.getText().toString();

        // Check for a valid phone.
        if (TextUtils.isEmpty(phone)) {
            showToast(String.format(getString(R.string.error_field_required), getString(R.string.phone)));
            return;
        } else if (!Common.isPhoneValid(phone)) {
            showToast(getString(R.string.error_invalid_phone_number));
            mPhoneView.setTextColor(getResources().getColor(R.color.orange));
            return;
        }

        if (viewId == R.id.sendVerifyMsg) {
            if (!TextUtils.isEmpty(previousPhone)) {
                if (!mPhoneView.getText().toString().equals(previousPhone)) {
                    sendMsg(phone);
                }
            } else {
                sendMsg(phone);
            }
            return;
        }

        if (TextUtils.isEmpty(message)) {
            showToast(String.format(getString(R.string.error_field_required), getString(R.string.captcha)));
            return;
        } else if (!TextUtils.isEmpty(message) && !Common.isMessageValid(message)) {
            showToast(getString(R.string.error_invalid_message));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast(String.format(getString(R.string.error_field_required), getString(R.string.password)));
            return;
        } else if (!TextUtils.isEmpty(password) && !Common.isPasswordValid(password)) {
            showToast(getString(R.string.error_invalid_password));
            return;
        }

        if (viewId == R.id.register_button) {
            SMSSDK.submitVerificationCode("86", phone, message);
            return;
        }
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        mRegisterFormView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        registerButton.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRegisterFormView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
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

    private void sendMsg(String phoneNums) {
        SMSSDK.getVerificationCode("86", phoneNums);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(eventHandler);
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }

        super.onDestroy();
    }


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            int i = 60;
            while (i >= 0) {
                try {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = 999;
                    msg.arg2 = i;
                    handler.sendMessage(msg);
                    Thread.sleep(1000);
                    i--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.arg1 == 2) {//短信验证码
                if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {//获取验证码成功
                    sendVerifyMsgButton.setEnabled(false);
                    sendVerifyMsgButton.setBackgroundResource(R.drawable.sms_captcha_off);
                    thread.start();
                } else if (msg.arg2 == SMSSDK.RESULT_ERROR) {//验证码失败
                    sendVerifyMsgButton.setText("重新获取");
                }
            } else if (msg.arg1 == 3) {
                if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {//验证成功
                    showProgress(true);
                    register();
                } else if (msg.arg2 == SMSSDK.RESULT_ERROR) {//验证失败
                    sendVerifyMsgButton.setText("重新获取");
                }
            } else if (msg.arg1 == 999) {
                int time = msg.arg2;
                if (time == 0) {
                    sendVerifyMsgButton.setText("获取验证码");
                    sendVerifyMsgButton.setBackgroundResource(R.drawable.sms_captcha_on);
                } else {
                    sendVerifyMsgButton.setText("请" + time + "秒后重发");
                }
            }
        }
    };

    private void register() {

    }


}

