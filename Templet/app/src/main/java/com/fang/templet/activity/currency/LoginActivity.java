package com.fang.templet.activity.currency;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fang.templet.R;
import com.fang.templet.activity.HomeActivity;
import com.fang.templet.activity.presenter.LoginPresenter;
import com.fang.templet.activity.viewinterface.LoginInterface;
import com.fang.templet.base.BaseActivity;
import com.fang.templet.base.MyApplication;
import com.fang.templet.base.constant.Constant;
import com.fang.templet.component.toast.ToastManager;
import com.fang.templet.util.MyCountTimer;
import com.fang.templet.util.RegularValidationUtils;
import com.fang.templet.util.StringUtils;

/**
 * 包名：com.fang.templet.activity
 * 作者：高学斌 on 2015-12-15 0015 14:46   年份：2015
 * 邮箱：13671322615@163.com
 */
public class LoginActivity extends BaseActivity implements LoginInterface, TextWatcher, View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private ImageButton Btn_Del;
    private EditText Edit_Phone;
    private EditText Edit_Validation;
    private Button Btn_Validation;
    private Button Btn_Login;
    private LoginPresenter mLoginPresenter;
    private String mPhoneNum;


    /**
     * 是否在计时
     */
    private boolean isCountTimer = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (MyCountTimer.FINISH == msg.what) {
                Btn_Validation.setEnabled(true);
            }
            if (MyCountTimer.TICK == msg.what) {
                Btn_Validation.setEnabled(false);
                Btn_Validation.setText((msg.arg1) / 1000 + "s");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTitle(getResources().getString(R.string.activity_login));
        setNav(R.drawable.ic_menu_back);

        Btn_Del = (ImageButton) findViewById(R.id.del_button);
        Edit_Phone = (EditText) findViewById(R.id.phone_input);
        Edit_Validation = (EditText) findViewById(R.id.input_verify_number);
        Btn_Validation = (Button) findViewById(R.id.btn_verifynum);
        Btn_Login = (Button) findViewById(R.id.login_button);

        Edit_Phone.addTextChangedListener(this);
        Btn_Del.setOnClickListener(this);
        Btn_Validation.setOnClickListener(this);
        Btn_Login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mLoginPresenter = new LoginPresenter(this);
        startTimer();
    }

    @Override
    protected void onNavClickEvent() {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mPhoneNum = Edit_Phone.getText().toString().trim();
        int visible = (mPhoneNum.length() > 0) ? View.VISIBLE : View.INVISIBLE;
        Btn_Del.setVisibility(visible);
        boolean enabled = (RegularValidationUtils.isMobileNO(mPhoneNum));
        Btn_Validation.setEnabled(enabled);
        int color = enabled ? R.color.main_bg : R.color.graylight;
        Btn_Validation.setTextColor(getResources().getColor(color));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.del_button:
                doDelete();
                break;
            case R.id.btn_verifynum:
                doGetVerifynum();
                break;
            case R.id.login_button:
                doLogin();
                break;
        }
    }

    /**
     * 获取验证码操作操作
     */
    private void doGetVerifynum() {
        mPhoneNum = Edit_Phone.getText().toString().trim();
        if (RegularValidationUtils.isMobileNO(mPhoneNum)) {
            mLoginPresenter.IssueValidationSMS(mContext, mPhoneNum);
        }
    }

    /**
     * 删除操作
     */
    private void doDelete() {
        Edit_Phone.setText("");
    }

    /**
     * 登录操作
     */
    private void doLogin() {
        String str = Edit_Phone.getText().toString().trim();
        if (!isCountTimer) {
            if (StringUtils.isNullOrEmpty(str)) {
                ((ToastManager) (MyApplication.getManager(Constant.ManagerName.TOASTMANAGER))).show("验证码不能为空", Toast.LENGTH_SHORT);
                return;
            } else {
                mLoginPresenter.Validation(mPhoneNum, str);
            }
        }
    }

    /**
     * 登录成功
     */
    @Override
    public void LoginSuccess() {
        startActivityForAnima(new Intent(mContext, HomeActivity.class), this);
        finish();
    }

    @Override
    public void LoginFail() {
        //// TODO: 2016-1-15 0015
        isCountTimer = false;
    }

    @Override
    public void IssueSuccess() {
        startTimer();
    }

    public void startTimer() {
        new CountDownTimer(10000, 1000) {//总时间， 间隔时间

            @Override
            public void onTick(long millisUntilFinished) {
                Btn_Validation.setText("即将开始(" + millisUntilFinished / 1000 + "/s)");
            }

            @Override
            public void onFinish() {
                Btn_Validation.setText("测试");
            }
        }.start();
        isCountTimer = true;
    }
}
