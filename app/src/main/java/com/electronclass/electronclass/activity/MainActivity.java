package com.electronclass.electronclass.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.electronclass.aclass.ClassFragment;
import com.electronclass.application.ApplicationFragment;
import com.electronclass.attendance.AttendanceFragment;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.InformType;
import com.electronclass.common.database.MacAddress;
import com.electronclass.common.event.EventRight;
import com.electronclass.common.event.EventTime;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.Tools;
import com.electronclass.electronclass.AppApplication;
import com.electronclass.electronclass.R;
import com.electronclass.electronclass.adapter.FragmentTabAdapter;
import com.electronclass.electronclass.contract.MainContract;
import com.electronclass.electronclass.databinding.ActivityNew2MainBinding;
import com.electronclass.electronclass.presenter.MainPresenter;
import com.electronclass.home.HomeFragment;
import com.electronclass.pda.mvp.entity.Inform;
import com.tencent.bugly.Bugly;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {
    private ActivityNew2MainBinding binding;
    private List<Fragment> fragmentList = new ArrayList<>();
    private boolean isGetSetting = false;
    private CommonRecyclerViewAdapter<Inform> schoolInformAdapter;
    private Timer timer;
    private int timeout = 60 * 60 * 1000;
    private int COUNTS = 4;// 点击次数
    private long DURATION = 1000;// 规定有效时间
    private long[] mHits = new long[COUNTS];
    private PopupWindow settingWindow;
    private RequestOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new2_main);
        EventBus.getDefault().register(this);
        init();
    }

    @NotNull
    @Override
    protected MainContract.Presenter getPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        initPicUtil();
        initBugly();
        setFragmentList();
        setFragment();
        setClassName();
        setTime();
        onClick();
    }

    @Override
    protected void initData() {

        /**
         * 展示学校信息
         */
        AppApplication.getInstance().setSchoolInfo(() -> {

            logger.info("收到SchoolInfo");
            isGetSetting = true;
            Glide.with(MainActivity.this)
                    .load(GlobalParam.getSchoolInfo().getLogo())
                    .into(binding.schoolLogo);
            binding.schoolName.setText(GlobalParam.getSchoolInfo() == null ? "" : GlobalParam.getSchoolInfo().getName());
            getDates();
        });

        /**
         * 刷卡动画
         */
        AppApplication.getInstance().setBulb(b -> {
            runOnUiThread(() -> {
                if (b) {
                    binding.bulb.setImageResource(R.drawable.bulb_true);
                } else {
                    binding.bulb.setImageResource(R.drawable.bulb_false);
                }
            });
        });
    }

    /**
     * 是否展示校园通知栏
     *
     * @param eventRight
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventRight(EventRight eventRight) {
        if (eventRight.isVisit()) {
            binding.constraintLayout3.setVisibility(View.VISIBLE);
        } else {
            binding.constraintLayout3.setVisibility(View.GONE);
        }
    }

    /**
     * 考勤时间修改
     *
     * @param eventTime
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventTime(EventTime eventTime) {
        binding.attendanceTime.setText(GlobalParam.getEventTime());
    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }

    @Override
    protected void onRestart() {
        logger.info("onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        logger.info("onResume");
        super.onResume();
        if (GlobalParam.getClassInfo() != null)
            binding.className.setText(GlobalParam.getClassInfo().getClassName());

        if (GlobalParam.getSchoolInfo() != null)
            binding.schoolName.setText(GlobalParam.getSchoolInfo().getName());


    }

    @Override
    protected void onPause() {
        logger.info("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        logger.info("onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        logger.info("onDestroy");
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onInform(List<Inform> inform) {
        if (inform == null)
            return;
        setSchoolInfo(inform);
    }
    /**
     * 设置学校通知
     * @param inform
     */
    private void setSchoolInfo(List<Inform> inform) {
        binding.banner.setVisibility(View.VISIBLE);
        binding.banner.setImageLoader(new GlideImageLoader());
        binding.banner.setImages(inform);
        binding.banner.isAutoPlay(true);
        binding.banner.setDelayTime(3000);
        binding.banner.setIndicatorGravity(BannerConfig.RIGHT);
        binding.banner.start();
    }

    private void Event() {
        if (GlobalParam.getClassInfo() != null) {
            binding.className.setText(GlobalParam.getClassInfo().getClassName());
        }
    }

    private class GlideImageLoader implements ImageLoaderInterface<View> {

        @Override
        public void displayImage(Context context, Object path, View imageView) {
            TextView  tv = imageView.findViewById(R.id.tv_school_info);
            ImageView image = imageView.findViewById(R.id.iv_school_info);

            Inform data = (Inform) path;
            tv.setText(data.getText());
            if (org.apache.commons.lang3.StringUtils.isEmpty(data.getPicUrl())){
                Glide.with(context).load(getResources().getDrawable(R.drawable.soccer)).apply(options).into(image);
            }else {
                Glide.with(context).load(GlobalParam.pUrl + data.getPicUrl()).apply(options).into(image);
            }

        }

        @Override
        public View createImageView(Context context) {
            return getLayoutInflater().inflate(R.layout.school_info, null);
        }

    }


    /**
     * 初始化校园通知控件
     */
    private void initPicUtil(){
        RoundedCorners roundedCorners = new RoundedCorners(20);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        options = RequestOptions.bitmapTransform(roundedCorners);
    }

    /**
     * 点击事件
     */
    private void onClick() {
        /**
         * 点击校园logo弹出密码框
         */
        binding.toSettingView.setOnClickListener(v -> continuousClick());
    }


    /**
     * 调用接口
     */
    private void getDates() {
        if (GlobalParam.getEcardNo() != null && isGetSetting) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // type通知类型 0 - 班级通知 1校园通知
                    // isAvaliable  0-获取未过期的所有通知 (包含正在生效和未生效)， 1 获取正在生效的通知 即（开始时间小于当前时间，截止时间大于当前时间）
                    mPresenter.getInform("1", "10", InformType.SCHOOL);//获取学校通知

                }
            }, 0, timeout);
        } else {
            if (GlobalParam.getEcardNo() == null) {
                logger.debug("界面初始化未完成");
            }
            if (!isGetSetting) {
                logger.debug("设置未获得");
            }
        }
    }

    /**
     * 全局更新
     */
    private void initBugly() {
        Bugly.init(getApplicationContext(), GlobalParam.ECARD_UPDATEID, false);
//        if (EcardType.getType() == EcardType.ML) {
//            Bugly.init(getApplicationContext(), GlobalParam.MULAN_UPDATEID, false);
//        } else if (EcardType.getType() == EcardType.HHD) {
//            Bugly.init(getApplicationContext(), GlobalParam.HENGHONGDA_UPDATEID, false);
//        } else if (EcardType.getType() == EcardType.HK) {
//            logger.debug("获取更新");
//            Bugly.init(getApplicationContext(), GlobalParam.HK_UPDATEID, false);
//        }
    }

    /**
     * 设置时间
     */
    private void setTime() {
        binding.attendanceTime.setText(GlobalParam.getEventTime());
        binding.ecardNo.setText(StringUtils.isEmpty(GlobalParam.getEcardNo()) ? MacAddress.getDeviceMacAddrress() : GlobalParam.getEcardNo());
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    Date date = DateUtil.getNowDate();
                    binding.tvTime.setText(DateUtil.dateToString(date, "HH:mm:ss"));
                });

            }
        }, 0, 1000);
    }


    private void setFragment() {
        FragmentTabAdapter fragmentTabAdapter = new FragmentTabAdapter(this, fragmentList, R.id.frameLayout, binding.radio);
    }

    /**
     * 设置模块列表
     */
    private void setFragmentList() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ApplicationFragment());
        fragmentList.add(new ClassFragment());
        fragmentList.add(new AttendanceFragment());
//        fragmentList.add( new LoginFragment() );
    }


    /**
     * 设置班级名称
     */
    private void setClassName() {
        AppApplication.getInstance().setTopEvent(this::Event);
    }




    /**
     * 跳转到设置界面
     */
    private void setting() {
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }


    /**
     * logo连点判断
     */
    private void continuousClick() {
        //每次点击时，数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            mHits = new long[COUNTS];//重新初始化数组
            showSettingPwd();
        }
    }


    /**
     * 展示进入设置界面的密码框
     */
    private void showSettingPwd() {
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowView = inflater.inflate(R.layout.setting_window, null);
        settingWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        settingWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        settingWindow.setOutsideTouchable(false);
        TextView tv = popupWindowView.findViewById(R.id.textView12);
        EditText settingPwd = popupWindowView.findViewById(R.id.settingPwd);
        TextView sure = popupWindowView.findViewById(R.id.sure);
        ConstraintLayout setWindowCl = popupWindowView.findViewById(R.id.setWindowCl);
        tv.setVisibility(View.GONE);

        sure.setOnClickListener(v -> {
            String pwd = settingPwd.getText().toString().trim();
            if (StringUtils.isEmpty(pwd)) {
                Tools.displayToast("请输入密码");
            } else {
                if (org.apache.commons.lang3.StringUtils.contains(GlobalParam.toSettingPwd, pwd)) {
                    settingWindow.dismiss();
                    setting();
                } else {
                    tv.setVisibility(View.VISIBLE);
                }
            }
        });
        setWindowCl.setOnClickListener(v -> settingWindow.dismiss());

        settingWindow.showAtLocation(binding.mainCl, Gravity.CENTER, 0, 0);
    }
}
