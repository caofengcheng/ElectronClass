package com.electronclass.aclass;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electronclass.aclass.activity.ui.UpdateDutyActivity;
import com.electronclass.aclass.databinding.FragmentNewClassBinding;
import com.electronclass.aclass.fragment.CouresFragment;
import com.electronclass.aclass.fragment.DutyFragment;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.event.EventRight;
import com.electronclass.pda.mvp.base.BasePresenterInterface;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * 班级信息
 */
public class ClassFragment extends BaseFragment {
    public  Logger               logger       = LoggerFactory.getLogger(getClass());
    private List<Fragment>       fragmentList = new ArrayList<>();
    private FragmentNewClassBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        logger.info( "classFragment--onCreate" );
        EventBus.getDefault().post(new EventRight(false));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        logger.info( "classFragment--onCreateView" );
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_new_class, container, false );
        View view = binding.getRoot();
        binding.inclusiveRadioGroup.check( R.id.classItem );
        setFragmentList();
        setViewPager();
        setOnClick();
        return view;
    }

    @NotNull
    @Override
    protected BasePresenterInterface getPresenter() {
        return null;
    }

    @Override
    protected void initView(View view) {

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

    @Override
    protected void getData() {

    }


    private void setViewPager(){
        binding.viewPager.setAdapter( new FragmentPagerAdapter(this.getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get( i );
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        } );
        binding.viewPager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        binding.inclusiveRadioGroup.check(R.id.classItem);

                        break;
                    case 1:
                        binding.inclusiveRadioGroup.check(R.id.dutyItem);

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        } );

    }

    private void setOnClick(){
        binding.classItem.setOnClickListener( v -> binding.viewPager.setCurrentItem(0,true) );

        binding.dutyItem.setOnClickListener( v -> binding.viewPager.setCurrentItem(1,true) );

        binding.setDuty.setOnClickListener( v -> {
            Intent intent = new Intent( getActivity(), UpdateDutyActivity.class );
            intent.putExtra( GlobalParam.TO_DUTY, GlobalParam.ADD_DUTY );
            startActivityForResult( intent,2 );
        } );
    }


    private void setFragmentList() {
        fragmentList.add( new CouresFragment() );
        fragmentList.add( new DutyFragment() );
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        logger.info( "classFragment--onHiddenChanged:"+hidden );
        EventBus.getDefault().post(new EventRight(hidden));
    }
}
