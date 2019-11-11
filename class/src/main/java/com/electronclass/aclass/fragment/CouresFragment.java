package com.electronclass.aclass.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electronclass.aclass.R;
import com.electronclass.aclass.contract.CouresContract;
import com.electronclass.aclass.databinding.FragmentCouresBinding;
import com.electronclass.aclass.gloab.Week;
import com.electronclass.aclass.presenter.CouresPresenter;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.Tools;
import com.electronclass.pda.mvp.entity.Coures;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程
 */
public class CouresFragment extends BaseFragment<CouresContract.Presenter> implements CouresContract.View {

    private FragmentCouresBinding             binding;
    private List<Coures>                      coures1 = new ArrayList<>();
    private List<Coures>                      coures2 = new ArrayList<>();
    private List<Coures>                      coures3 = new ArrayList<>();
    private List<Coures>                      coures4 = new ArrayList<>();
    private List<Coures>                      coures5 = new ArrayList<>();
    private List<Coures>                      coures6 = new ArrayList<>();
    private List<Coures>                      coures7 = new ArrayList<>();
    private CommonRecyclerViewAdapter<Coures> weekAdapter1;
    private CommonRecyclerViewAdapter<Coures> weekAdapter2;
    private CommonRecyclerViewAdapter<Coures> weekAdapter3;
    private CommonRecyclerViewAdapter<Coures> weekAdapter4;
    private CommonRecyclerViewAdapter<Coures> weekAdapter5;
    private CommonRecyclerViewAdapter<Coures> weekAdapter6;
    private CommonRecyclerViewAdapter<Coures> weekAdapter7;
    private boolean                           isHave  = false;//是否已经获取到数据

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_coures, container, false );
        View view = binding.getRoot();
        init( view );
        return view;
    }

    @NotNull
    @Override
    protected CouresContract.Presenter getPresenter() {
        return new CouresPresenter();
    }

    @Override
    protected void initView(View view) {
        setBackGroundColor();
        setWeekOne();
        setWeekTwo();
        setWeekThree();
        setWeekTour();
        setWeekTive();
        setWeekSix();
        setWeekSeven();
    }

    @Override
    protected void initData() {
//        if (GlobalParam.getClassInfo() == null) {
//            logger.debug( "CouresFragment:initData null" );
//            Tools.displayToast( "请先绑定班牌班级！" );
//            return;
//        }
//        mPresenter.getCoures();
    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }

    @Override
    protected void getData() {
        logger.info( "CouresFragment--getData" );
        if (GlobalParam.getClassInfo() == null) {
            logger.debug( "CouresFragment:initData null" );
            Tools.displayToast( "请先绑定班牌班级！" );
            return;
        }
        if (isHave) {
            return;
        }
        mPresenter.getCoures();
    }

    @Override
    public void onCoures(List<Coures> coures) {
        coures1.clear();
        coures2.clear();
        coures3.clear();
        coures4.clear();
        coures5.clear();
        coures6.clear();
        coures7.clear();
        for (Coures couresItem : coures) {
            switch (couresItem.getWeek()) {
                case Week.WEEK1:
                    coures1.add( couresItem );
                    break;
                case Week.WEEK2:
                    coures2.add( couresItem );
                    break;
                case Week.WEEK3:
                    coures3.add( couresItem );
                    break;
                case Week.WEEK4:
                    coures4.add( couresItem );
                    break;
                case Week.WEEK5:
                    coures5.add( couresItem );
                    break;
                case Week.WEEK6:
                    coures6.add( couresItem );
                    break;
                case Week.WEEK7:
                    coures7.add( couresItem );
                    break;
            }
        }
        weekAdapter1.setData( coures1 );
        weekAdapter2.setData( coures2 );
        weekAdapter3.setData( coures3 );
        weekAdapter4.setData( coures4 );
        weekAdapter5.setData( coures5 );
        weekAdapter6.setData( coures6 );
        weekAdapter7.setData( coures7 );
        weekAdapter1.notifyDataSetChanged();
        weekAdapter2.notifyDataSetChanged();
        weekAdapter3.notifyDataSetChanged();
        weekAdapter4.notifyDataSetChanged();
        weekAdapter5.notifyDataSetChanged();
        weekAdapter6.notifyDataSetChanged();
        weekAdapter7.notifyDataSetChanged();
        isHave = true;
    }


    @Override
    public void onError(String errorMessage) {
        super.onError( errorMessage );
    }

    private void setBackGroundColor() {
        binding.tvWeek1.setBackgroundDrawable( getResources().getDrawable( R.drawable.shape_6_class1 ) );
        binding.tvWeek2.setBackgroundDrawable( getResources().getDrawable( R.drawable.shape_6_class2 ) );
        binding.tvWeek3.setBackgroundDrawable( getResources().getDrawable( R.drawable.shape_6_class3 ) );
        binding.tvWeek4.setBackgroundDrawable( getResources().getDrawable( R.drawable.shape_6_class4 ) );
        binding.tvWeek5.setBackgroundDrawable( getResources().getDrawable( R.drawable.shape_6_class5 ) );
        binding.tvWeek6.setBackgroundDrawable( getResources().getDrawable( R.drawable.shape_6_class6 ) );
        binding.tvWeek7.setBackgroundDrawable( getResources().getDrawable( R.drawable.shape_6_class7 ) );

    }

    private void setWeekOne() {
        weekAdapter1 = new CommonRecyclerViewAdapter<Coures>( R.layout.class_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, Coures item) {
                baseViewHolder.setText( R.id.classItem, StringUtils.isNotEmpty( item.getCourseName() ) ? item.getCourseName() : "" );
                baseViewHolder.setBackgroundResources( R.id.classCl, R.drawable.shape_6_class1 );
            }
        };
        weekAdapter1.bindRecyclerView( binding.monDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekTwo() {
        weekAdapter2 = new CommonRecyclerViewAdapter<Coures>( R.layout.class_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, Coures item) {
                baseViewHolder.setText( R.id.classItem, StringUtils.isNotEmpty( item.getCourseName() ) ? item.getCourseName() : "" );
                baseViewHolder.setBackgroundResources( R.id.classCl, R.drawable.shape_6_class2 );
            }
        };
        weekAdapter2.bindRecyclerView( binding.tuesDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekThree() {
        weekAdapter3 = new CommonRecyclerViewAdapter<Coures>( R.layout.class_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, Coures item) {
                baseViewHolder.setText( R.id.classItem, StringUtils.isNotEmpty( item.getCourseName() ) ? item.getCourseName() : "" );
                baseViewHolder.setBackgroundResources( R.id.classCl, R.drawable.shape_6_class3 );
            }
        };
        weekAdapter3.bindRecyclerView( binding.wednesDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekTour() {
        weekAdapter4 = new CommonRecyclerViewAdapter<Coures>( R.layout.class_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, Coures item) {
                baseViewHolder.setText( R.id.classItem, StringUtils.isNotEmpty( item.getCourseName() ) ? item.getCourseName() : "" );
                baseViewHolder.setBackgroundResources( R.id.classCl, R.drawable.shape_6_class4 );
            }
        };
        weekAdapter4.bindRecyclerView( binding.thursDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekTive() {
        weekAdapter5 = new CommonRecyclerViewAdapter<Coures>( R.layout.class_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, Coures item) {
                baseViewHolder.setText( R.id.classItem, StringUtils.isNotEmpty( item.getCourseName() ) ? item.getCourseName() : "" );
                baseViewHolder.setBackgroundResources( R.id.classCl, R.drawable.shape_6_class5 );
            }
        };
        weekAdapter5.bindRecyclerView( binding.friDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekSix() {
        weekAdapter6 = new CommonRecyclerViewAdapter<Coures>( R.layout.class_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, Coures item) {
                baseViewHolder.setText( R.id.classItem, StringUtils.isNotEmpty( item.getCourseName() ) ? item.getCourseName() : "" );
                baseViewHolder.setBackgroundResources( R.id.classCl, R.drawable.shape_6_class6 );
            }
        };
        weekAdapter6.bindRecyclerView( binding.saturDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekSeven() {
        weekAdapter7 = new CommonRecyclerViewAdapter<Coures>( R.layout.class_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, Coures item) {
                baseViewHolder.setText( R.id.classItem, StringUtils.isNotEmpty( item.getCourseName() ) ? item.getCourseName() : "" );
                baseViewHolder.setBackgroundResources( R.id.classCl, R.drawable.shape_6_class7 );
            }
        };
        weekAdapter7.bindRecyclerView( binding.sunDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        logger.info( "CouresFragment--onHiddenChanged:" + hidden );

    }
}
