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
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.electronclass.aclass.R;
import com.electronclass.aclass.activity.contract.UpdateDutyContract;
import com.electronclass.aclass.activity.presenter.UpdateDutyPresenter;
import com.electronclass.aclass.databinding.ActivityUpdateDutyBinding;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.database.GlobalPage;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.event.CardType;
import com.electronclass.common.event.SettingsEvent;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.KeyboardUtils;
import com.electronclass.common.util.ReadThreadUtil;
import com.electronclass.common.util.SerialportManager;
import com.electronclass.common.util.Tools;
import com.electronclass.common.util.wheelview.OnWheelChangedListener;
import com.electronclass.common.util.wheelview.OnWheelScrollListener;
import com.electronclass.common.util.wheelview.WheelView;
import com.electronclass.common.util.wheelview.adapter.NumericWheelAdapter;
import com.electronclass.pda.mvp.entity.Duty;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

/**
 * 修改或添加值日
 */
public class UpdateDutyActivity extends BaseActivity<UpdateDutyPresenter> implements UpdateDutyContract.View {

    private ActivityUpdateDutyBinding binding;
    private String                    type       = null;
    private Duty                      duty;
    private boolean                   isCard     = false;
    private String                    id         = null;
    private String                    cardNumber = null;
    private ReadThreadUtil            readThreadUtil;
    private int                       curYear;
    private int                       curMonth;
    private WheelView                 wl_start_year;
    private WheelView                 wl_start_month;
    private WheelView                 wl_start_day;
    private PopupWindow               popupWindow;
    private LinearLayout              sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_update_duty );
        EventBus.getDefault().register(this);
        init();
        GlobalParam.setCardType( GlobalParam.UPDATEACTIVITY );
        binding.card.setTextColor( Color.parseColor( "#ff273eb0" ) );
        binding.card.setText( "请刷卡" );
    }

    @Override
    protected UpdateDutyPresenter getPresenter() {
        return new UpdateDutyPresenter();
    }

    @Override
    protected void initView() {
        setOnClick();
        type = getIntent().getStringExtra( GlobalParam.TO_DUTY );
        if (type.equals( GlobalParam.ADD_DUTY )) {
            binding.tvTime.setText( DateUtil.getNowDate( DateUtil.DatePattern.ONLY_DAY ) );
            binding.save.setVisibility( View.VISIBLE );
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onCardType(CardType cardType) {
        logger.info( "update---cardType:" + cardType.number );
        if (StringUtils.isNoneEmpty( cardType.number )) {
            cardNumber = cardType.number;
            binding.card.setTextColor( Color.parseColor( "#ff3ab581" ) );
            binding.card.setText( "请继续添加" );
        }else {
            binding.card.setTextColor( Color.parseColor( "#ff273eb0" ) );
            binding.card.setText( "请刷卡" );
        }
    }

//    //刷卡绑定
//    private void initSerialPort() {

//        if (GlobalPage.pageConfig == GlobalPage.MULAN) {
//            logger.debug( "启动木兰刷卡" );
//            SerialportManager.getInstance().init();
//            SerialportManager.getInstance().addListener( this );
//        } else if (GlobalPage.pageConfig == GlobalPage.HENGHONGDA) {
//            logger.debug( "启动恒宏达刷卡" );
//            readThreadUtil = new ReadThreadUtil();
//            readThreadUtil.startReadThread( (type, cardNum) -> {
//                runOnUiThread( () -> {
//                    if (type == 0) {
//                        logger.info( "卡号：" + cardNum );
//                        cardNumber = cardNum;
//                        binding.card.setTextColor( Color.parseColor( "#ff3ab581" ) );
//                        binding.card.setText( "请继续添加" );
//                        isCard = true;
//                    } else {
//                        Tools.displayToast( "读取出错，不兼容的卡" );
//                        binding.card.setTextColor( Color.parseColor( "#ff273eb0" ) );
//                        binding.card.setText( "请刷卡" );
//                        isCard = false;
//                    }
//                } );
//
//            } );
//        }
//    }

    private void setOnClick() {
        binding.back.setOnClickListener( v -> {
            finish();
        } );

        binding.duty.setOnClickListener( v -> KeyboardUtils.hideKeyboard( binding.duty ) );

        binding.linearLayout2.setOnClickListener( v -> KeyboardUtils.showSoftInputFromWindow( binding.tvName ) );


        binding.linearLayout3.setOnClickListener( v -> KeyboardUtils.showSoftInputFromWindow( binding.tvType ) );

        binding.tvTime.setOnClickListener( v -> showPop() );

        /**
         * 保存值日
         */
        binding.save.setOnClickListener( v -> toPresenter( "" ) );

        /**
         * 修改值日
         */
        binding.update.setOnClickListener( v -> toPresenter( id ) );

        /**
         * 删除值日
         */
        binding.delete.setOnClickListener( v -> mPresenter.deleteDuty( id ) );
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

//    @Override
//    public void onReceiveData(String cardNo) {
//        logger.info( "卡号：" + cardNo );
//        if (StringUtils.isNoneEmpty( cardNo )) {
//            binding.card.setTextColor( Color.parseColor( "#ff3ab581" ) );
//            binding.card.setText( "请继续添加" );
//            cardNumber = cardNo;
//            isCard = true;
//        } else {
//            binding.card.setTextColor( Color.parseColor( "#ff273eb0" ) );
//            binding.card.setText( "请刷卡" );
//            isCard = false;
//        }
//    }

    private void toPresenter(String id) {
        getCard();
        getName();
        getType();
        mPresenter.addOrUpdateDuty( id, cardNumber, binding.tvType.getText().toString().trim(), binding.tvName.getText().toString().trim(), binding.tvTime.getText().toString().trim() );
    }

    /**
     * 判断是否刷卡
     */
    private void getCard() {
        if (!isCard) {
            Tools.displayToast( "请先刷卡" );
            return;
        }
    }

    /**
     * 判断是否输入名字
     */
    private void getName() {
        if (StringUtils.isEmpty( binding.tvName.getText().toString().trim() )) {
            Tools.displayToast( "请输入学生名字" );
            return;
        }
    }

    /**
     * 判断是否输入值日类型
     */
    private void getType() {
        if (StringUtils.isEmpty( binding.tvType.getText().toString().trim() )) {
            Tools.displayToast( "请输入值日类型" );
            return;
        }
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
        GlobalParam.setCardType( GlobalParam.MAINACTIVITY );
        finish();
    }

//    /**
//     * 关闭刷卡
//     */
//    private void stopCard() {
//        if (GlobalPage.pageConfig == GlobalPage.MULAN) {
//            logger.debug( "关闭木兰刷卡" );
//            SerialportManager.getInstance().removeListener( this );
//        } else if (GlobalPage.pageConfig == GlobalPage.HENGHONGDA) {
//            logger.debug( "关闭恒宏达刷卡" );
//            readThreadUtil.stopReadThread();
//        }
//    }


    /**
     * 开启时间选择器
     */
    private void showPop() {
        LayoutInflater inflater        = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.pop_term_select, null );
        popupWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );
        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        popupWindow.setOutsideTouchable( true );
        initWheelView( popupWindowView );
        popupWindow.showAtLocation( binding.updateCl, Gravity.CENTER, 0, 0 );
    }

    private void initWheelView(View view) {
        Calendar c = Calendar.getInstance();
        curYear = c.get( Calendar.YEAR );
        curMonth = c.get( Calendar.MONTH ) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get( Calendar.DATE );
        /*****************开始时间***********************/
        wl_start_year = view.findViewById( R.id.wl_start_year );
        wl_start_month = view.findViewById( R.id.wl_start_month );
        wl_start_day = view.findViewById( R.id.wl_start_day );
        sure = view.findViewById( R.id.sure );

        NumericWheelAdapter numericWheelAdapterStart1 = new NumericWheelAdapter( this, 2000, 2100 );
        numericWheelAdapterStart1.setLabel( " " );
        wl_start_year.setViewAdapter( numericWheelAdapterStart1 );
        numericWheelAdapterStart1.setTextColor( R.color.black );
        numericWheelAdapterStart1.setTextSize( 20 );
        wl_start_year.setCyclic( true );//是否可循环滑动
        wl_start_year.addScrollingListener( startScrollListener );
        wl_start_year.addChangingListener( (wheel, oldValue, newValue) -> {
            curYear = newValue + 2000;
            initStartDayAdapter();
        } );
        sure.setOnClickListener( v -> popupWindow.dismiss() );

        NumericWheelAdapter numericWheelAdapterStart2 = new NumericWheelAdapter( this, 1, 12, "%02d" );
        numericWheelAdapterStart2.setLabel( " " );
        wl_start_month.setViewAdapter( numericWheelAdapterStart2 );
        numericWheelAdapterStart2.setTextColor( R.color.black );
        numericWheelAdapterStart2.setTextSize( 20 );
        wl_start_month.setCyclic( true );
        wl_start_month.addScrollingListener( startScrollListener );
        wl_start_month.addChangingListener( new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                curMonth = newValue + 1;
                initStartDayAdapter();
            }
        } );
        initStartDayAdapter();
        wl_start_year.setCurrentItem( curYear - 2000 );
        wl_start_month.setCurrentItem( curMonth - 1 );
        wl_start_day.setCurrentItem( curDate - 1 );

    }

    OnWheelScrollListener startScrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_year  = wl_start_year.getCurrentItem() + 2000;//年
            int n_month = wl_start_month.getCurrentItem() + 1;//月
            int n_day   = wl_start_day.getCurrentItem() + 1;//日
            binding.tvTime.setText( n_year + "-" + n_month + "-" + n_day );
        }
    };

    private void initStartDayAdapter() {
        NumericWheelAdapter numericWheelAdapterStart3 = new NumericWheelAdapter( this, 1, DateUtil.getDay( curYear, curMonth ), "%02d" );
        numericWheelAdapterStart3.setLabel( " " );
        wl_start_day.setViewAdapter( numericWheelAdapterStart3 );
        numericWheelAdapterStart3.setTextColor( R.color.black );
        numericWheelAdapterStart3.setTextSize( 20 );
        wl_start_day.setCyclic( true );
        wl_start_day.addScrollingListener( startScrollListener );
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.info( "onDestroy" );
        GlobalParam.setCardType( GlobalParam.MAINACTIVITY );
        EventBus.getDefault().unregister(this);//解除注册
    }


}
