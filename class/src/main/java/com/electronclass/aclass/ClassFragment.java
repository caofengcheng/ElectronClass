package com.electronclass.aclass;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electronclass.aclass.adapter.FragmentTabAdapter;
import com.electronclass.aclass.databinding.FragmentClassBinding;
import com.electronclass.aclass.fragment.CouresFragment;
import com.electronclass.aclass.fragment.DutyFragment;
import com.electronclass.common.database.GlobalParam;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Bidi;
import java.util.ArrayList;
import java.util.List;


/**
 * 班级信息
 */
public class ClassFragment extends Fragment {
    public  Logger               logger       = LoggerFactory.getLogger(getClass());
    private FragmentClassBinding binding;
    private List<Fragment>       fragmentList = new ArrayList<>();
    private FragmentTabAdapter   fragmentTabAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_class, container, false );
        View view = binding.getRoot();
        setFragmentList();
        setFragment();
        logger.debug( "ClassFragment--onCreateView.setText" );
        binding.className.setText(  GlobalParam.getClassInfo() == null ? " " : GlobalParam.getClassInfo().getClassName() );
        logger.debug( "ClassFragment--onCreateView.setText--Over" );
        return view;
    }

    private void setFragment() {
        fragmentTabAdapter = new FragmentTabAdapter( this, fragmentList, R.id.frameLayout, binding.radioGroup );
    }

    private void setFragmentList() {
        fragmentList.add( new CouresFragment() );
        fragmentList.add( new DutyFragment() );
    }

}
