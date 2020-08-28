package com.electronclass.aclass.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electronclass.aclass.R;
import com.electronclass.aclass.activity.ui.UpdateDutyActivity;
import com.electronclass.aclass.contract.DutyContract;
import com.electronclass.aclass.databinding.FragmentDutyBinding;
import com.electronclass.aclass.gloab.Week;
import com.electronclass.aclass.presenter.DutyPresenter;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.Tools;
import com.electronclass.pda.mvp.entity.Duty;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 值日
 */
public class DutyFragment extends BaseFragment<DutyContract.Presenter> implements DutyContract.View {

    private FragmentDutyBinding             binding;
    private CommonRecyclerViewAdapter<Duty> weekAdapter1;
    private CommonRecyclerViewAdapter<Duty> weekAdapter2;
    private CommonRecyclerViewAdapter<Duty> weekAdapter3;
    private CommonRecyclerViewAdapter<Duty> weekAdapter4;
    private CommonRecyclerViewAdapter<Duty> weekAdapter5;
    private CommonRecyclerViewAdapter<Duty> weekAdapter6;
    private CommonRecyclerViewAdapter<Duty> weekAdapter7;
    private List<Duty>                      duty1  = new ArrayList<>();
    private List<Duty>                      duty2  = new ArrayList<>();
    private List<Duty>                      duty3  = new ArrayList<>();
    private List<Duty>                      duty4  = new ArrayList<>();
    private List<Duty>                      duty5  = new ArrayList<>();
    private List<Duty>                      duty6  = new ArrayList<>();
    private List<Duty>                      duty7  = new ArrayList<>();
    private boolean                         isHave = false;//是否已经获取到数据

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_duty, container, false );
        View view = binding.getRoot();
        init( view );
        return view;
    }


    @NotNull
    @Override
    protected DutyContract.Presenter getPresenter() {
        return new DutyPresenter();
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
//            logger.debug( "DutyFragment:initData null" );
//            Tools.displayToast( "请先绑定班牌班级！" );
//            return;
//        }
//        mPresenter.getDuty();
    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }

    @Override
    protected void getData() {
        logger.info( "DutyFragment--getData" );
        if (GlobalParam.getClassInfo() == null) {
            logger.debug( "DutyFragment:initData null" );
            Tools.displayToast( "请先绑定班牌班级！" );
            return;
        }
        mPresenter.getDuty();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );

    }

    @Override
    public void onDuty(List<Duty> duties) {
        duty1.clear();
        duty2.clear();
        duty3.clear();
        duty4.clear();
        duty5.clear();
        duty6.clear();
        duty7.clear();

        for (Duty dutyItem : duties) {
            switch (dutyItem.getWeek()) {
                case Week.WEEK1:
                    duty1.add( dutyItem );
                    break;
                case Week.WEEK2:
                    duty2.add( dutyItem );
                    break;
                case Week.WEEK3:
                    duty3.add( dutyItem );
                    break;
                case Week.WEEK4:
                    duty4.add( dutyItem );
                    break;
                case Week.WEEK5:
                    duty5.add( dutyItem );
                    break;
                case Week.WEEK6:
                    duty6.add( dutyItem );
                    break;
                case Week.WEEK7:
                    duty7.add( dutyItem );
                    break;
            }
        }
        weekAdapter1.setData( duty1 );
        weekAdapter2.setData( duty2 );
        weekAdapter3.setData( duty3 );
        weekAdapter4.setData( duty4 );
        weekAdapter5.setData( duty5 );
        weekAdapter6.setData( duty6 );
        weekAdapter7.setData( duty7 );
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
        weekAdapter1 = new CommonRecyclerViewAdapter<Duty>( R.layout.duty_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final Duty item) {
                baseViewHolder.setText( R.id.task, item.getTask() );
                baseViewHolder.setText( R.id.tvSweepName, item.getName() );
                baseViewHolder.setBackgroundResources( R.id.cl1, R.drawable.shape_6_class1 );
                baseViewHolder.setOnClickListener( R.id.cl1, v -> toUpdate( item ) );
            }
        };
        weekAdapter1.bindRecyclerView( binding.inMonday, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekTwo() {
        weekAdapter2 = new CommonRecyclerViewAdapter<Duty>( R.layout.duty_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final Duty item) {
                baseViewHolder.setText( R.id.task, item.getTask() );
                baseViewHolder.setText( R.id.tvSweepName, item.getName() );
                baseViewHolder.setBackgroundResources( R.id.cl1, R.drawable.shape_6_class2 );
                baseViewHolder.setOnClickListener( R.id.cl1, v -> toUpdate( item ) );
            }
        };
        weekAdapter2.bindRecyclerView( binding.inTuesDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekThree() {
        weekAdapter3 = new CommonRecyclerViewAdapter<Duty>( R.layout.duty_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final Duty item) {
                baseViewHolder.setText( R.id.task, item.getTask() );
                baseViewHolder.setText( R.id.tvSweepName, item.getName() );
                baseViewHolder.setBackgroundResources( R.id.cl1, R.drawable.shape_6_class3 );
                baseViewHolder.setOnClickListener( R.id.cl1, v -> toUpdate( item ) );
            }
        };
        weekAdapter3.bindRecyclerView( binding.inWednesDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekTour() {
        weekAdapter4 = new CommonRecyclerViewAdapter<Duty>( R.layout.duty_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final Duty item) {
                baseViewHolder.setText( R.id.task, item.getTask() );
                baseViewHolder.setText( R.id.tvSweepName, item.getName() );
                baseViewHolder.setBackgroundResources( R.id.cl1, R.drawable.shape_6_class4 );
                baseViewHolder.setOnClickListener( R.id.cl1, v -> toUpdate( item ) );
            }
        };
        weekAdapter4.bindRecyclerView( binding.inThursDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekTive() {
        weekAdapter5 = new CommonRecyclerViewAdapter<Duty>( R.layout.duty_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final Duty item) {
                baseViewHolder.setText( R.id.task, item.getTask() );
                baseViewHolder.setText( R.id.tvSweepName, item.getName() );
                baseViewHolder.setBackgroundResources( R.id.cl1, R.drawable.shape_6_class5 );
                baseViewHolder.setOnClickListener( R.id.cl1, v -> toUpdate( item ) );
            }
        };
        weekAdapter5.bindRecyclerView( binding.inFriDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekSix() {
        weekAdapter6 = new CommonRecyclerViewAdapter<Duty>( R.layout.duty_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final Duty item) {
                baseViewHolder.setText( R.id.task, item.getTask() );
                baseViewHolder.setText( R.id.tvSweepName, item.getName() );
                baseViewHolder.setBackgroundResources( R.id.cl1, R.drawable.shape_6_class6 );
                baseViewHolder.setOnClickListener( R.id.cl1, v -> toUpdate( item ) );
            }
        };
        weekAdapter6.bindRecyclerView( binding.inSaturDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void setWeekSeven() {
        weekAdapter7 = new CommonRecyclerViewAdapter<Duty>( R.layout.duty_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final Duty item) {
                baseViewHolder.setText( R.id.task, item.getTask() );
                baseViewHolder.setText( R.id.tvSweepName, item.getName() );
                baseViewHolder.setBackgroundResources( R.id.cl1, R.drawable.shape_6_class7 );
                baseViewHolder.setOnClickListener( R.id.cl1, v -> toUpdate( item ) );
            }
        };
        weekAdapter7.bindRecyclerView( binding.inSunDay, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void toUpdate(Duty item) {
        Intent intent = new Intent( getActivity(), UpdateDutyActivity.class );
        intent.putExtra( GlobalParam.TO_DUTY, GlobalParam.UPDATE_DUTY );
        intent.putExtra( GlobalParam.UPDATE_DUTY_ITEM, (Serializable) item);
        startActivityForResult( intent, 1 );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (GlobalParam.getClassInfo() == null) {
            logger.debug( "请先绑定班牌！" );
            Tools.displayToast( "请先绑定班牌！" );
            return;
        }
        if (mPresenter != null) {
            mPresenter.getDuty();
        }
    }
}
