package com.electronclass.home;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.GlobalParameter;
import com.electronclass.common.database.InformType;
import com.electronclass.common.event.SettingsEvent;
import com.electronclass.common.util.Tools;
import com.electronclass.common.util.VerticalTextView;
import com.electronclass.home.activity.ImageActivity;
import com.electronclass.home.activity.VideoActivity;
import com.electronclass.home.contract.HomeContract;
import com.electronclass.home.databinding.FragmentHomeBinding;
import com.electronclass.home.imageviewplay.ImageViewPlay;
import com.electronclass.home.presenter.HomePresenter;
import com.electronclass.pda.mvp.entity.ClassMienMessage;
import com.electronclass.pda.mvp.entity.Inform;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.sivin.BannerAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 主页
 */
public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    private static Logger              logger       = LoggerFactory.getLogger( HomeFragment.class );
    private        FragmentHomeBinding binding;
    private        ArrayList<String>   schoolInform = new ArrayList<String>();
    private        boolean             isGetSetting = false;
    private        int                 timeout      = 60 * 60 * 1000;
    private        ClassMienAdapter    classMienAdapter;
    //    private        boolean             isClassHava  = false; //班级通知是否开始滚动
    private        boolean             isSchoolHava = false;//校园通知是否开始滚动

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_home, container, false );
        View view = binding.getRoot();
        init( view );
        return view;
    }


    @NotNull
    @Override
    protected HomeContract.Presenter getPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView(View view) {
        setAdapter();
        logger.info( "getMAC:" + GlobalParameter.ECARDNO == null ? GlobalParameter.getMacAddress() : GlobalParameter.ECARDNO );
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
        logger.info( "切换界面刷新" );
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
        if (inform.get( 0 ).getType() == 0) {
            setClassInfrom( inform );
        } else {
            setSchoolinform( inform );
        }

    }

    @Override
    public void onClassMien(List<ClassMienMessage> classMienMessages) {
        classMienAdapter.setNewData( classMienMessages );
    }

    @Override
    public void loadMoreFail() {
//        classMienAdapter.loadMoreFail();
    }

    @Override
    public void loadMoreEnd() {
        classMienAdapter.loadMoreEnd();
    }

    @Override
    public void EnableLoadMore() {
        classMienAdapter.setEnableLoadMore( true );
    }

    @Override
    public void addSheltermaterials(List<ClassMienMessage> classMienMessages) {
        classMienAdapter.addData( classMienMessages );
        classMienAdapter.loadMoreComplete();
    }

    @Override
    public void onNoData() {
        classMienAdapter.setNewData( null );
    }

    /**
     * 设置班级通知
     *
     * @param inform
     */
    private void setClassInfrom(List<Inform> inform) {
//        switch (inform.get( 0 ).getTextType()) {
//            case 0:   //文本
//                setTextBanner( inform );
//                break;
//            case 1:   //图片
        binding.tvClassInform.setVisibility( View.GONE );
        binding.banner.setVisibility( View.VISIBLE );
        setImageBanner( inform );
//                break;
//        }

    }


    /**
     * 设置校园通知
     *
     * @param inform
     */
    private void setSchoolinform(List<Inform> inform) {
        setTextBanner( inform );
    }

    /**
     * 设置班级通知图片滚动
     */
    private void setImageBanner(List<Inform> inform) {
        BannerAdapter adapter = new BannerAdapter<Inform>( inform ) {
            @Override
            protected void bindTips(TextView tv, Inform bannerModel) {
//                if (bannerModel.getTextType() == 0)
//                tv.setText(bannerModel.getText() );
            }

            @Override
            public void bindImage(ImageView imageView, Inform bannerModel) {
                if (bannerModel.getTextType() == 1) {
                    Glide.with( getActivity() )
                            .load( bannerModel
                                    .getText() )
                            .into( imageView );
                } else {
                    TextView textView = new TextView( getActivity() );
                    textView.setText( bannerModel.getText() );
                    textView.setDrawingCacheEnabled( true );
                    textView.measure( View.MeasureSpec.makeMeasureSpec( 0, View.MeasureSpec.UNSPECIFIED ), View.MeasureSpec.makeMeasureSpec( 0, View.MeasureSpec.UNSPECIFIED ) );
                    textView.layout( 1, 76, 1000, 519 );
                    textView.setTextSize( 50 );
                    textView.setMaxLines( 10 );
                    textView.setTextColor( Color.parseColor( "#ff272727" ) );
                    textView.setBackgroundColor( Color.parseColor( "#00000000" ) );
                    textView.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL );
                    Bitmap bitmap = Bitmap.createBitmap( textView.getDrawingCache() );
                    textView.destroyDrawingCache();
                    Glide.with( getActivity() )
                            .load( bitmap )
                            .into( imageView );
                }

            }
        };
        binding.banner.setBannerAdapter( adapter );
        binding.banner.notifiDataHasChanged();
    }


    /**
     * 通知文本
     *
     * @param inform
     */
    private void setTextBanner(List<Inform> inform) {
        schoolInform.clear();
        for (Inform text : inform) {
            schoolInform.add( text.getText() );
        }
        if (inform.get( 0 ).getType() == InformType.SCHOOL) {
//            binding.tvClassInform.setTextList( schoolInform );
//            if (!isClassHava) {
//                binding.tvClassInform.setVisibility( View.VISIBLE );
//                binding.banner.setVisibility( View.GONE );
//                binding.tvClassInform.setMaxLines( 10 );
//                binding.tvClassInform.setText( 50, 5, Color.parseColor( "#ff272727" ) );//设置属性
//                binding.tvClassInform.setTextStillTime( 3000 );//设置停留时长间隔
//                binding.tvClassInform.setBackground( Color.parseColor( "#00000000" ) );//设置停留时长间隔
//                binding.tvClassInform.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL );
//                binding.tvClassInform.setHorizontalAnimTime( 300 );//设置进入和退出的时间间隔
//            }
//            binding.tvClassInform.startAutoScroll();
//            isClassHava = true;
//        } else {
            binding.tvInform.setTextList( schoolInform );
            if (!isSchoolHava) {
                binding.tvInform.setMaxLines( 1 );
                binding.tvInform.setText( 38, 5, Color.parseColor( "#ff575757" ) );//设置属性
                binding.tvInform.setTextStillTime( 3000 );//设置停留时长间隔
                binding.tvInform.setGravity( Gravity.CENTER | Gravity.LEFT );//设置停留时长间隔
                binding.tvInform.setVerticalAnimTime( 300 );//设置进入和退出的时间间隔
            }
            binding.tvInform.startAutoScroll();
            binding.page.setText( schoolInform.size() + "" );
            binding.tvInform.setOnItemRollListener( new VerticalTextView.OnItemRollListener() {
                @Override
                public void onItemRoll(int position) {
                    binding.pageNumber.setText( position + 1 + "" );
                }
            } );
            isSchoolHava = true;
        }

    }


    /**
     * 播放图片
     */
    private void showImage(String url) {
//        String extension   = MimeTypeMap.getFileExtensionFromUrl( url );
//        String mimeType    = MimeTypeMap.getSingleton().getMimeTypeFromExtension( extension );
//        Intent mediaIntent = new Intent( Intent.ACTION_VIEW );
//        mediaIntent.setDataAndType( Uri.parse( url ), mimeType );
//        startActivity( mediaIntent );

        Intent intent = new Intent( getActivity(), ImageActivity.class );
        intent.putExtra( "Image", url );
        startActivity( intent );
    }

    private void showVoide(String url) {
        Intent intent = new Intent( getActivity(), VideoActivity.class );
        intent.putExtra( "Video", url );
        startActivity( intent );
    }

    /**
     * 调用接口
     */
    private void getDatas() {
        if (GlobalParam.getEcardNo() != null && isGetSetting) {

            new Timer().schedule( new TimerTask() {
                @Override
                public void run() {
                    if (isSchoolHava) {
                        binding.tvInform.stopAutoScroll();
                    }
//                    if (isClassHava) {
//                        binding.tvClassInform.stopAutoScroll();
//                    }
                    // type通知类型 0 - 班级通知 1校园通知
                    // isAvaliable  0-获取未过期的所有通知 (包含正在生效和未生效)， 1 获取正在生效的通知 即（开始时间小于当前时间，截止时间大于当前时间）
                    mPresenter.getInform( GlobalParameter.getMacAddress(), "", GlobalParam.getSchoolInfo().getSchoolId(), InformType.SCHOOL, 1 );//获取学校通知
                    if (GlobalParam.getClassInfo() != null) {
                        mPresenter.getInform( GlobalParameter.getMacAddress(), "", GlobalParam.getClassInfo().getClassId(), InformType.CLASS, 1 );//获取班级通知
                        mPresenter.getClassMien( GlobalParameter.getMacAddress(), "", GlobalParam.getClassInfo().getClassId(), 1, 9 );//获取班级风采
                    }
                }
            }, 0, timeout );
        } else {
            if (GlobalParam.getEcardNo() == null) {
                logger.debug( "界面初始化未完成" );
            }
            if (!isGetSetting) {
                logger.debug( "设置未获得" );
            }
        }
    }

    private void setAdapter() {
        classMienAdapter = new ClassMienAdapter();
        classMienAdapter.setOnLoadMoreListener( new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getClassMien( GlobalParam.getEcardNo(), "", GlobalParam.getClassInfo().getClassId(), 2, 9 );//获取班级通知
            }
        } );
        binding.classMien.setLayoutManager( new GridLayoutManager( getActivity(), 3 ) );
        binding.classMien.setAdapter( classMienAdapter );

        binding.classMien.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with( getActivity() ).resumeRequests();//恢复Glide加载图片
                } else {
                    Glide.with( getActivity() ).pauseRequests();//禁止Glide加载图片
                }
            }
        } );
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onSettingsEvent(SettingsEvent event) {
        logger.info( "收到event" );
        isGetSetting = true;
        binding.className.setText( GlobalParam.getClassInfo() == null ? "" : GlobalParam.getClassInfo().getClassName() );
        getDatas();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        logger.info( "onHiddenChanged：" + hidden );
        if (hidden) {
            if (isSchoolHava) {
                binding.tvInform.stopAutoScroll();
            }
//            if (isClassHava) {
//                binding.tvClassInform.stopAutoScroll();
//            }
        } else {
            if (GlobalParam.getClassInfo() == null) {
                Tools.displayToast( "请先绑定班牌班级" );
                return;
            }
            getDatas();//刷新数据
        }
    }


    class ClassMienAdapter extends BaseQuickAdapter<ClassMienMessage, BaseViewHolder> {

        ClassMienAdapter() {
            super( R.layout.classmien_item );
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, final ClassMienMessage item) {

            ImageViewPlay image = baseViewHolder.getView( R.id.image );

            Glide.with( getActivity() ).load( GlobalParam.pUrl + item.getUrl() ).into( image );

            baseViewHolder.setOnClickListener( R.id.clClassItem, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getType() == 1) {
                        showImage( GlobalParam.pUrl + item.getUrl() );
                    } else {
                        showVoide( GlobalParam.pUrl + item.getUrl() );
                    }
                }
            } );
        }
    }
}
