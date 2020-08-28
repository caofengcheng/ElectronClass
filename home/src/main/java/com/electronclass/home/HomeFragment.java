package com.electronclass.home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.InformType;
import com.electronclass.common.database.MacAddress;
import com.electronclass.common.event.SettingsEvent;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.GlideCacheUtil;
import com.electronclass.common.util.Tools;
import com.electronclass.home.activity.ImageActivity;
import com.electronclass.home.activity.VideoActivity;
import com.electronclass.home.activity.ui.ClassMienActivity;
import com.electronclass.home.adapter.GlideRoundTransform;
import com.electronclass.home.contract.HomeContract;
import com.electronclass.home.databinding.FragmentNewHomeBinding;
import com.electronclass.home.presenter.HomePresenter;
import com.electronclass.pda.mvp.entity.ClassMienMessage;
import com.electronclass.pda.mvp.entity.Inform;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.ResourceObserver;


/**
 * 主页
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    private static Logger                 logger       = LoggerFactory.getLogger(HomeFragment.class);
    private        FragmentNewHomeBinding binding;
    private        boolean                isGetSetting = false;
    private        int                    timeout      = 60 * 60 * 1000;
    private        int                    firstType    = 1;
    private        String                 firstUrl;
    private        RequestOptions         options;

    private CommonRecyclerViewAdapter<ClassMienMessage> commonClassMienAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_home, container, false);
        View view = binding.getRoot();
        init(view);
        return view;
    }


    @NotNull
    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView(View view) {
        logger.info("home----getMAC:" + (StringUtils.isEmpty(GlobalParam.getEcardNo()) ? MacAddress.getDeviceMacAddrress() : GlobalParam.getEcardNo()));
        initPicUtil();
        setCommonClassMienAdapter();
        setOnClick();
        getDate();
    }

    @Override
    protected void initData() {
        getDatas();
    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }

    @Override
    protected void getData() {
        logger.info("切换界面刷新");
    }

    @Override
    public void onResume() {
        logger.info("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        logger.info("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        logger.info("onStop");
        super.onStop();
    }

    /**
     * 班级和校园通知数据
     *
     * @param inform
     */
    @Override
    public void onInform(List<Inform> inform) {
        if (inform == null)
            return;
        if (inform.get(0).getType() == 0) {
            setClassInfrom(inform);
        }

    }

    @Override
    public void onClassMien(List<ClassMienMessage> classMienMessages) {
        if (classMienMessages != null && classMienMessages.size() > 0) {
            RoundedCorners roundedCorners = new RoundedCorners(10);
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(HomeFragment.this)
                 .load(GlobalParam.pUrl + classMienMessages
                         .get(0).getPicUrl())
                 .apply(options)
                 .into(binding.firstPicture);
//            firstType = classMienMessages.get(0).getType();
            firstUrl = classMienMessages.get(0).getPicUrl();
            if (classMienMessages.size() > 1) {
                List<ClassMienMessage> classMiens = new ArrayList<>();
                for (int i = 0; i < classMienMessages.size(); i++) {
                    if (i == 0) {
                        continue;
                    }
                    classMiens.add(classMienMessages.get(i));
                }
                commonClassMienAdapter.setData(classMiens);
                commonClassMienAdapter.notifyDataSetChanged();
            }
        }

    }

    private void setOnClick() {
        /**
         * 加载更多
         */
        binding.addMOre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClassMienActivity.class);
                startActivity(intent);
            }
        });
        binding.firstPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstType == 1) {
                    showImage(GlobalParam.pUrl + firstUrl);
                } else {
                    showVoide(GlobalParam.pUrl + firstUrl);
                }
            }
        });
    }


    private void getDate() {
        int      nowDate = DateUtil.startWeek(new Date());
        String[] date    = DateUtil.getWeekDay();
        logger.info("本周开始时间：" + DateUtil.getWeekDay().toString());

        binding.weekData1.setText(date[0]);
        binding.weekData2.setText(date[1]);
        binding.weekData3.setText(date[2]);
        binding.weekData4.setText(date[3]);
        binding.weekData5.setText(date[4]);
        binding.weekData6.setText(date[5]);
        binding.weekData7.setText(date[6]);
        setTime(nowDate);


    }


    /**
     * 设置班级通知
     *
     * @param inform
     */
    private void setClassInfrom(List<Inform> inform) {
        binding.banner.setVisibility(View.VISIBLE);
        binding.banner.setImageLoader(new GlideImageLoader());
        binding.banner.setImages(inform);
        binding.banner.isAutoPlay(true);
        binding.banner.setDelayTime(3000);
        binding.banner.setIndicatorGravity(BannerConfig.RIGHT);
        binding.banner.start();
    }


    /**
     * 播放图片
     */
    private void showImage(String url) {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra("Image", url);
        startActivity(intent);
    }

    /**
     * 播放视频
     */
    private void showVoide(String url) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra("Video", url);
        startActivity(intent);
    }

    /**
     * 调用接口
     */
    private void getDatas() {
        if (GlobalParam.getEcardNo() != null && isGetSetting) {

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // type通知类型 0 - 班级通知 1校园通知
                    if (GlobalParam.getClassInfo() != null && mPresenter != null && StringUtils.isNoneEmpty(MacAddress.getMacAddress(getActivity()))) {
                        logger.info("班级信息不为空");
                        mPresenter.getInform("1", "10", InformType.CLASS);//获取班级通知
                        mPresenter.getClassMien(StringUtils.isEmpty(GlobalParam.getEcardNo()) ? MacAddress.getDeviceMacAddrress() : GlobalParam.getEcardNo(), "", GlobalParam.getClassInfo().getClassId(), 1, 9);//获取班级风采
                    }
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

    private void setCommonClassMienAdapter() {
        commonClassMienAdapter = new CommonRecyclerViewAdapter<ClassMienMessage>(R.layout.class_mien) {
            @Override
            public void convert(com.electronclass.common.base.BaseViewHolder baseViewHolder, final ClassMienMessage item) {
                RoundedCorners roundedCorners = new RoundedCorners(10);
                //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                RequestOptions options   = RequestOptions.bitmapTransform(roundedCorners);
                ImageView      imageView = (ImageView) baseViewHolder.getView(R.id.image);
                Glide.with(getActivity())
                     .load(GlobalParam.pUrl + item.getPicUrl())
                     .apply(options)
                     .into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (item.getType() == 1) {
                        showImage(GlobalParam.pUrl + item.getPicUrl());
//                        } else {
//                            showVoide(GlobalParam.pUrl + item.getPicUrl());
//                        }
                    }
                });

            }
        };
        commonClassMienAdapter.bindRecyclerView(binding.classRecycler, new GridLayoutManager(getActivity(), 4));
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onSettingsEvent(SettingsEvent event) {
        logger.info("收到event");
        isGetSetting = true;
        getDatas();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (GlobalParam.getClassInfo() == null) {
            Tools.displayToast("请先绑定班牌班级");
            return;
        }
        if (!hidden) {
            getDatas();//刷新数据
        }

    }


    private void setTime(int nowDate) {
        switch (nowDate) {
            case 1:
                binding.weekTv1.setTextColor(Color.parseColor("#FFFFFF"));
                binding.weekData1.setTextColor(Color.parseColor("#FFFFFF"));
                binding.week1.setBackground(getResources().getDrawable(R.drawable.shape_60_yellow));
                break;
            case 2:
                binding.weekTv2.setTextColor(Color.parseColor("#FFFFFF"));
                binding.weekData2.setTextColor(Color.parseColor("#FFFFFF"));
                binding.week2.setBackground(getResources().getDrawable(R.drawable.shape_60_yellow));
                break;
            case 3:
                binding.weekTv3.setTextColor(Color.parseColor("#FFFFFF"));
                binding.weekData3.setTextColor(Color.parseColor("#FFFFFF"));
                binding.week3.setBackground(getResources().getDrawable(R.drawable.shape_60_yellow));
                break;
            case 4:
                binding.weekTv4.setTextColor(Color.parseColor("#FFFFFF"));
                binding.weekData4.setTextColor(Color.parseColor("#FFFFFF"));
                binding.week4.setBackground(getResources().getDrawable(R.drawable.shape_60_yellow));
                break;
            case 5:
                binding.weekTv5.setTextColor(Color.parseColor("#FFFFFF"));
                binding.weekData5.setTextColor(Color.parseColor("#FFFFFF"));
                binding.week5.setBackground(getResources().getDrawable(R.drawable.shape_60_yellow));
                break;
            case 6:
                binding.weekTv6.setTextColor(Color.parseColor("#FFFFFF"));
                binding.weekData6.setTextColor(Color.parseColor("#FFFFFF"));
                binding.week6.setBackground(getResources().getDrawable(R.drawable.shape_60_yellow));
                break;
            case 7:
                binding.weekTv7.setTextColor(Color.parseColor("#FFFFFF"));
                binding.weekData7.setTextColor(Color.parseColor("#FFFFFF"));
                binding.week7.setBackground(getResources().getDrawable(R.drawable.shape_60_yellow));
                break;
        }
    }

    public class GlideImageLoader implements ImageLoaderInterface<View> {

        @Override
        public void displayImage(Context context, Object path, View imageView) {
            TextView  title = imageView.findViewById(R.id.title);
            TextView  time  = imageView.findViewById(R.id.time);
            TextView  text  = imageView.findViewById(R.id.text);
            ImageView image = imageView.findViewById(R.id.image);

            Inform data = (Inform) path;
            title.setText(data.getTitle());
            time.setText(data.getStartTime());
            text.setText(data.getText());
            if (StringUtils.isEmpty(data.getPicUrl())) {
                Glide.with(context).load(getResources().getDrawable(R.drawable.student)).apply(options).into(image);
            } else {
                Glide.with(context).load(GlobalParam.pUrl + data.getPicUrl()).apply(options).into(image);
            }

        }

        @Override
        public View createImageView(Context context) {
            return getLayoutInflater().inflate(R.layout.class_message_item, null);
        }

    }

    private void initPicUtil() {
         options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH)//优先级
                .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存策略
                .transform(new GlideRoundTransform(20));//转化为圆角
    }

}
