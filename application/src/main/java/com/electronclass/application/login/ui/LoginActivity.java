package com.electronclass.application.login.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.electronclass.application.R;
import com.electronclass.application.databinding.ActivityLoginBinding;
import com.electronclass.application.login.contract.LoginContract;
import com.electronclass.application.login.presenter.LoginPresenter;
import com.electronclass.application.set.ui.SetActivity;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.util.KeyboardUtils;
import com.electronclass.common.util.Tools;
import org.apache.commons.lang3.StringUtils;


public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    private ActivityLoginBinding binding;

    //只做11位判断，号段交给后台去判断
    private String telRegex = "^\\d{11}$";

    private boolean canClick = true;

    private CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_login );
        init();
    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        setOnClick();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }


    private void setOnClick() {
        binding.deselect.setOnClickListener( v -> finish() );

        /**
         *获取验证码
         */
        binding.getPhoneCode.setOnClickListener( v -> {
            if (canClick) {
                canClick = false;
                String phoneNum = binding.phoneNumber.getText().toString().trim();

                if (StringUtils.isNoneEmpty( phoneNum ) && phoneNum.matches( telRegex )) {
                    mPresenter.sendSms( phoneNum );
                    timer = new CountDownTimer( 60 * 1000, 1000 ) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            binding.getPhoneCode.setTextColor( Color.parseColor( "#ff999999" ) );
                            binding.getPhoneCode.setText( millisUntilFinished / 1000 + "秒" );
                        }

                        @Override
                        public void onFinish() {
                            binding.getPhoneCode.setTextColor( Color.parseColor( "#ff273eb0" ) );
                            binding.getPhoneCode.setText( "获取验证码" );
                            canClick = true;
                        }
                    }.start();
                } else {
                    canClick = true;
                    Tools.displayToast( "请检查号码是否输入正确" );
                }
            }
        } );

        binding.login.setOnClickListener( v -> KeyboardUtils.hideKeyboard( binding.login ) );

        /**
         * 登陆
         */
        binding.tvSure.setOnClickListener( v -> {
            String phoneNum = binding.phoneNumber.getText().toString().trim();
            String codeNum  = binding.phoneCode.getText().toString().trim();

            if (StringUtils.isNoneEmpty( phoneNum ) && StringUtils.isNoneEmpty( codeNum )) {
                if (timer != null) {
                    timer.cancel();
                }
                mPresenter.login( phoneNum, codeNum );
            } else {
                Tools.displayToast( "电话号码或验证码不能为空" );
            }
        } );
    }

    @Override
    public void onSms(boolean isSuccess) {
        if (isSuccess) {
            Tools.displayToast( "验证码发送成功" );
        } else {
            Tools.displayToast( "验证码发送失败" );
        }
    }

    @Override
    public void onlogin(boolean str) {
        if (str){
            Intent intent = new Intent( LoginActivity.this, SetActivity.class );
            startActivity( intent );
            finish();
        }
    }

    @Override
    public void onError(String errorMessage) {
        super.onError( errorMessage );
        Tools.displayToast( errorMessage );
    }
}
