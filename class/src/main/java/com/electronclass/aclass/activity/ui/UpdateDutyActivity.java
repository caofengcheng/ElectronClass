package com.electronclass.aclass.activity.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.cncoderx.wheelview.WheelView;
import com.electronclass.aclass.R;
import com.electronclass.aclass.activity.contract.UpdateDutyContract;
import com.electronclass.aclass.activity.presenter.UpdateDutyPresenter;
import com.electronclass.aclass.databinding.ActivityUpdateDutyBinding;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.database.GlobalPage;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.KeyboardUtils;
import com.electronclass.common.util.MD5Util;
import com.electronclass.common.util.Tools;
import com.electronclass.pda.mvp.entity.Duty;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;


/**
 * 修改或添加值日
 */
public class UpdateDutyActivity extends BaseActivity<UpdateDutyPresenter> implements UpdateDutyContract.View {

    private ActivityUpdateDutyBinding binding;
    private String                    type = null;
    private Duty                      duty;
    private String                    id   = null;
    private PopupWindow               popupWindow;

    private WheelView    wl_start_day;
    private CharSequence year;
    private CharSequence month;
    private CharSequence day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_update_duty );
//        EventBus.getDefault().register(this);
        init();
//        GlobalParam.setCardType( GlobalParam.UPDATEACTIVITY );
//        binding.card.setTextColor( Color.parseColor( "#ff273eb0" ) );
//        binding.card.setText( "请刷卡" );
    }

    @Override
    protected UpdateDutyPresenter getPresenter() {
        return new UpdateDutyPresenter();
    }

    @Override
    protected void initView() {
        setOnClick();

        //上次确认时间是否小于10分钟，如果小于10分钟或者第一次进入则需要输入密码
        if (GlobalPage.updateDutyTime == 0) {
            binding.pwEt.setText( "" );
        } else {
            logger.info( "时间：" + (TimeUtils.getNowMills() - GlobalPage.updateDutyTime) );
            if (TimeUtils.getNowMills() - GlobalPage.updateDutyTime >= 60 * 10 * 1000) {
                binding.pwEt.setText( "" );
                GlobalPage.updateDutyTime = 0;
            } else {
                binding.pwEt.setText( GlobalPage.updateDutyPsw );
            }
        }

        type = getIntent().getStringExtra( GlobalParam.TO_DUTY );
        if (type.equals( GlobalParam.ADD_DUTY )) {
            binding.tvTime.setText( DateUtil.getNowDate( DateUtil.DatePattern.ONLY_DAY ) );
            binding.save.setVisibility( View.VISIBLE );
            binding.updateTv.setText( "添加值日" );
        } else {
            binding.llUpdate.setVisibility( View.VISIBLE );
            duty = (Duty) getIntent().getExtras().get( GlobalParam.UPDATE_DUTY_ITEM );
            id = duty.getId();
            binding.tvTime.setText( StringUtils.isNoneEmpty( duty.getEventDate() ) ? duty.getEventDate() : DateUtil.getNowDate( DateUtil.DatePattern.ONLY_DAY ) );
            binding.tvName.setText( duty.getName() );
            binding.tvType.setText( duty.getTask() );
        }
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

//    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
//    public void onCardType(CardType cardType) {
//        logger.info( "update---cardType:" + cardType.number );
//        if (StringUtils.isNoneEmpty( cardType.number )) {
//            cardNumber = cardType.number;
//            binding.card.setTextColor( Color.parseColor( "#ff3ab581" ) );
//            binding.card.setText( "请继续添加" );
//        }else {
//            binding.card.setTextColor( Color.parseColor( "#ff273eb0" ) );
//            binding.card.setText( "请刷卡" );
//        }
//    }


    private void setOnClick() {
        /**
         * 返回
         */
        binding.back.setOnClickListener( v -> {
            finish();
        } );

        binding.duty.setOnClickListener( v -> KeyboardUtils.hideKeyboard( binding.duty ) );

        binding.linearLayout2.setOnClickListener( v -> {
            KeyboardUtils.showSoftInputFromWindow( binding.tvName );
            binding.tvName.setSelection( binding.tvName.getText().length() );
        } );


        binding.linearLayout3.setOnClickListener( v -> {
            KeyboardUtils.showSoftInputFromWindow( binding.tvType );
            binding.tvType.setSelection( binding.tvType.getText().length() );
        } );

        binding.pwLL.setOnClickListener( v -> {
            KeyboardUtils.showSoftInputFromWindow( binding.pwEt );
            binding.pwEt.setSelection( binding.pwEt.getText().length() );
        } );

        /**
         * 开启时间选择器
         */
        binding.tvTime.setOnClickListener( v -> showTimePop() );

        /**
         * 保存值日
         */
        binding.save.setOnClickListener( v -> {
            toPresenter( "" );
        } );

        /**
         * 修改值日
         */
        binding.update.setOnClickListener( v -> toPresenter( id ) );

        /**
         * 删除值日
         */
        binding.delete.setOnClickListener( v -> {
            if (StringUtils.isEmpty( binding.pwEt.getText().toString().trim() )) {
                Tools.displayToast( "请输入密码" );
                return;
            }
            String psw = MD5Util.Md5( binding.pwEt.getText().toString().trim() );
            mPresenter.deleteDuty( this, id, psw );
        } );
    }

    @Override
    public void onSuccress(String msg) {
        clientSuccess( msg );
    }

    @Override
    public void onDeleteDuty(String msg) {
        clientSuccess( msg );
    }

    @Override
    public void onError(String errorMessage) {
        super.onError( errorMessage );
        Tools.displayToast( errorMessage );
    }


    private void toPresenter(String id) {
        if (StringUtils.isEmpty( binding.tvName.getText().toString().trim() )) {
            Tools.displayToast( "请输入学生名字" );
            return;
        }
        if (StringUtils.isEmpty( binding.tvType.getText().toString().trim() )) {
            Tools.displayToast( "请输入值日类型" );
            return;
        }
        if (StringUtils.isEmpty( binding.pwEt.getText().toString().trim() )) {
            Tools.displayToast( "请输入密码" );
            return;
        }


        String psw  = MD5Util.Md5( binding.pwEt.getText().toString().trim() );
        String type = binding.tvType.getText().toString().trim();
        String name = binding.tvName.getText().toString().trim();
        String time = binding.tvTime.getText().toString().trim();
        GlobalPage.updateDutyTime = TimeUtils.getNowMills();
        GlobalPage.updateDutyPsw = binding.pwEt.getText().toString().trim();
        mPresenter.addOrUpdateDuty( this, id, psw, type, name, time );
    }


    /**
     * 请求成功返回
     *
     * @param msg
     */
    private void clientSuccess(String msg) {
        Tools.displayToast( msg );
        Intent intent = new Intent();
        setResult( RESULT_OK, intent );
        finish();
    }


    /**
     * //     * 开启时间选择器
     * //
     */
    private void showTimePop() {
        LayoutInflater inflater        = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.pop_attendance_select, null );
        popupWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );
        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        popupWindow.setOutsideTouchable( false );

        final WheelView wl_start_year  = popupWindowView.findViewById( R.id.wl_start_year );
        final WheelView wl_start_month = popupWindowView.findViewById( R.id.wl_start_month );
        wl_start_day = popupWindowView.findViewById( R.id.wl_start_day );
        TextView sureChoose = popupWindowView.findViewById( R.id.sureChoose );
        TextView stopChoose = popupWindowView.findViewById( R.id.stopChoose );
        wl_start_year.setEntries( DateUtil.Year() );//设置年
        wl_start_month.setEntries( DateUtil.Month() );//设置月
        wl_start_day.setEntries( DateUtil.thisDayEntries() );//设置月


        //当年滚动时，刷新日
        wl_start_year.setOnWheelChangedListener( (view, oldIndex, newIndex) -> {
            CharSequence year  = wl_start_year.getCurrentItem();
            CharSequence month = wl_start_month.getCurrentItem();
            updateDayEntries( Integer.parseInt( (String) year ), Integer.parseInt( (String) month ) );
        } );
        //当月滚动时，刷新日
        wl_start_month.setOnWheelChangedListener( (view, oldIndex, newIndex) -> {
            CharSequence year  = wl_start_year.getCurrentItem();
            CharSequence month = wl_start_month.getCurrentItem();
            updateDayEntries( Integer.parseInt( (String) year ), Integer.parseInt( (String) month ) );
        } );

        sureChoose.setOnClickListener( v -> {
            year = wl_start_year.getCurrentItem();
            month = wl_start_month.getCurrentItem();
            day = wl_start_day.getCurrentItem();
            binding.tvTime.setText( year + "-" + month + "-" + day );
            popupWindow.dismiss();
        } );
        stopChoose.setOnClickListener( v -> popupWindow.dismiss() );

        int yearItem  = DateUtil.thisYear();
        int monthItem = DateUtil.thisMonth();
        wl_start_year.setCurrentIndex( yearItem, true );
        wl_start_month.setCurrentIndex( monthItem, true );
        popupWindow.showAtLocation( binding.updateCl, Gravity.CENTER, 0, 0 );
    }

    /**
     * 当滚动年、月的时候判断当前月的天数，并进行设置
     *
     * @param year
     * @param month
     */
    private void updateDayEntries(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.YEAR, year );
        calendar.set( Calendar.MONTH, month - 1 );

        int days = calendar.getActualMaximum( Calendar.DAY_OF_MONTH );
        switch (days) {
            case 28:
                wl_start_day.setEntries( DateUtil.Day28() );
                break;
            case 29:
                wl_start_day.setEntries( DateUtil.Day29() );
                break;
            case 30:
                wl_start_day.setEntries( DateUtil.Day30() );
                break;
            case 31:
                wl_start_day.setEntries( DateUtil.Day31() );
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.info( "onDestroy" );
        GlobalParam.setCardType( GlobalParam.MAINACTIVITY );
        EventBus.getDefault().unregister(this);//解除注册
    }


}
