package com.electronclass.application;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electronclass.application.databinding.FragmentApplicationBinding;
import com.electronclass.application.web.WebActivity;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.module.AppModule;
import com.electronclass.pda.mvp.entity.AppItem;

import java.util.ArrayList;
import java.util.List;


/**
 * 应用中心
 *
 * @author caofengcheng
 */
public class ApplicationFragment extends Fragment {

    private FragmentApplicationBinding binding;
    private List<AppItem>              appItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_application, container, false );
        View view = binding.getRoot();
        init();
        return view;
    }

    private void init() {
        AppItem appItem = new AppItem();

        appItem.setName( "智腾食堂" );
        appItem.setCode( AppModule.FOOD );
        appItem.setImage( R.drawable.food );

        AppItem appItem1 = new AppItem();
        appItem1.setName( "树莓校园德育" );
        appItem1.setCode( AppModule.dyH5 );
        appItem1.setImage( R.drawable.shumei );

        appItems.add( appItem );
        appItems.add( appItem1 );

        setAdapter();
    }


    private void setAdapter() {
        CommonRecyclerViewAdapter<AppItem> commonRecyclerViewAdapter = new CommonRecyclerViewAdapter<AppItem>( R.layout.app_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final AppItem item) {
                baseViewHolder.setBackgroundResources( R.id.imageView, item.getImage() );
                baseViewHolder.setText( R.id.appName, item.getName() );
                baseViewHolder.setOnClickListener( R.id.clAppItem, v -> getClick( item.getCode() ) );

            }
        };
        commonRecyclerViewAdapter.setData( appItems );
        commonRecyclerViewAdapter.notifyDataSetChanged();
        binding.recyclerView.setLayoutManager( new GridLayoutManager( getActivity(), 5 ) );
        binding.recyclerView.setAdapter( commonRecyclerViewAdapter );
    }


    private void getClick(int code) {
        Intent intent = new Intent( getActivity(), WebActivity.class );
        switch (code) {
            case AppModule.FOOD:

                intent.putExtra( GlobalParam.APPURL, GlobalParam.FoodAppUrl );
                startActivity( intent );
                break;
            case AppModule.dyH5:
                String schoolId = GlobalParam.getSchoolInfo() == null ? "" : GlobalParam.getSchoolInfo().getSchoolId();
                String classId = GlobalParam.getClassInfo() == null ? "" : GlobalParam.getClassInfo().getClassId();
                String url = GlobalParam.DYH5 + "{\"schoolId\":\"" + schoolId + "\",\"departId\":\"" + classId + "\"}";
                Log.i( "h5--url:", url );
                intent.putExtra( GlobalParam.APPURL, url );
                startActivity( intent );
                break;
            default:
                break;
        }
    }

}
