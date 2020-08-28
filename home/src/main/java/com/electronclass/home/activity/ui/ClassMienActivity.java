package com.electronclass.home.activity.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.MacAddress;
import com.electronclass.common.util.Tools;
import com.electronclass.home.R;
import com.electronclass.home.activity.ImageActivity;
import com.electronclass.home.activity.VideoActivity;
import com.electronclass.home.activity.contract.ClassMienContract;
import com.electronclass.home.activity.presenter.ClassMienPresenter;
import com.electronclass.home.databinding.ActivityClassMienBinding;
import com.electronclass.pda.mvp.entity.ClassMienMessage;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

/**
 * 班级风采
 */
public class ClassMienActivity extends BaseActivity<ClassMienContract.Presenter> implements ClassMienContract.View {

    private ClassMienAdapter         classMienAdapter;
    private ActivityClassMienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_class_mien );
        init();
    }

    @Override
    protected ClassMienPresenter getPresenter() {
        return new ClassMienPresenter();
    }

    @Override
    protected void initView() {
        setAdapter();
    }

    @Override
    protected void initData() {
        if (GlobalParam.getClassInfo() != null) {
            mPresenter.getClassMien( MacAddress.getMacAddress( this ), "", GlobalParam.getClassInfo().getClassId(), 1, 9 );//获取班级风采
        } else {
            Tools.displayToast( "为获取到班级信息，请稍后再试" );
        }
    }

    @Override
    protected void showData() {

        binding.back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );

    }

    @Override
    protected void releaseData() {

    }

    @Override
    public void onClassMien(List<ClassMienMessage> classMienMessages) {
        classMienAdapter.setNewData( classMienMessages );
    }

    @Override
    public void loadMoreFail() {
        classMienAdapter.loadMoreFail();
    }

    @Override
    public void loadMoreEnd() {
        classMienAdapter.loadMoreEnd( true );
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
     * 播放图片
     */
    private void showImage(String url) {
        Intent intent = new Intent( ClassMienActivity.this, ImageActivity.class );
        intent.putExtra( "Image", url );
        startActivity( intent );
    }

    /**
     * 播放视频
     */
    private void showVoide(String url) {
        Intent intent = new Intent( ClassMienActivity.this, VideoActivity.class );
        intent.putExtra( "Video", url );
        startActivity( intent );
    }

    class ClassMienAdapter extends BaseQuickAdapter<ClassMienMessage, BaseViewHolder> {

        ClassMienAdapter() {
            super( R.layout.class_mien_item );
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, final ClassMienMessage item) {

            SimpleDraweeView image = baseViewHolder.getView( R.id.image );
            image.setImageURI( GlobalParam.pUrl + item.getPicUrl() );
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource( Uri.parse( GlobalParam.pUrl + item.getPicUrl() ) )
                    .setResizeOptions( new ResizeOptions( Tools.dp2px( 192 ), Tools.dp2px( 256 ) ) )
                    .build();
            image.setController( Fresco.newDraweeControllerBuilder()
                    .setOldController( image.getController() )
                    .setImageRequest( request )
                    .build() );
//            RoundedCorners   roundedCorners = new RoundedCorners( 6 );
//            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions options = RequestOptions.bitmapTransform( roundedCorners );

//            Glide.with( ClassMienActivity.this ).load( GlobalParam.pUrl + item.getUrl() ).apply( options ).into( image );
            image.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (item.getType() == 1) {
                        showImage( GlobalParam.pUrl + item.getPicUrl() );
//                    } else {
//                        showVoide( GlobalParam.pUrl + item.getUrl() );
//                    }
                }
            } );

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
        binding.recycler.setLayoutManager( new GridLayoutManager( ClassMienActivity.this, 3 ) );
        binding.recycler.setAdapter( classMienAdapter );

//        binding.recycler.addOnScrollListener( new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && !ClassMienActivity.this.isDestroyed()) {
//                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                        Glide.with( ClassMienActivity.this ).resumeRequests();//恢复Glide加载图片
//                    } else {
//                        Glide.with( ClassMienActivity.this ).pauseRequests();//禁止Glide加载图片
//                    }
//                }
//            }
//        } );
    }
}






