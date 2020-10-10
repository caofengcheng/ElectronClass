package com.electronclass.application;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.electronclass.application.databinding.FragmentApplicationBinding;
import com.electronclass.application.monitor.MonitorActivity;
import com.electronclass.application.web.WebActivity;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalPage;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.module.AppModule;
import com.electronclass.common.util.EcardType;
import com.electronclass.pda.mvp.entity.AppItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 应用中心
 *
 * @author caofengcheng
 */
public class ApplicationFragment extends Fragment {

    private FragmentApplicationBinding         binding;
    private List<AppItem>                      appItems   = new ArrayList<>();
    private boolean                            firstStart = true;
    private CommonRecyclerViewAdapter<AppItem> commonRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ApplicationFragment", "onCreate");
        setAdapter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_application, container, false);
        View view = binding.getRoot();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        binding.recyclerView.setAdapter(commonRecyclerViewAdapter);
        if (firstStart) {
            init();
            firstStart = false;
        }
        Log.i("ApplicationFragment", "onCreateView");
        return view;
    }

    private void init() {
        if (appItems != null && appItems.size() > 0) {
            appItems.clear();
        }
        AppItem appItem1 = new AppItem("智腾食堂", AppModule.FOOD, R.drawable.food);
        assert appItems != null;
        appItems.add(appItem1);

        AppItem appItem2 = new AppItem("树莓校园德育", AppModule.dyH5, R.drawable.shumei);
        appItems.add(appItem2);


        if (EcardType.getType() == EcardType.HK) {
            AppItem appItem3 = new AppItem("教室监控", AppModule.VIDEO, R.drawable.class_video);
            appItems.add(appItem3);
        }
        if (isMobile_spExist_WeiXin()) {//判断是否安装微信
            AppItem appItem4 = new AppItem("微信", AppModule.WEIXIN, R.drawable.shumei);
            appItems.add(appItem4);
        }

        commonRecyclerViewAdapter.setData(appItems);
        commonRecyclerViewAdapter.notifyDataSetChanged();
    }


    private void setAdapter() {
        commonRecyclerViewAdapter = new CommonRecyclerViewAdapter<AppItem>(R.layout.app_item, false, false) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final AppItem item) {
                baseViewHolder.setBackgroundResources(R.id.imageView, item.getImage());
                baseViewHolder.setText(R.id.appName, item.getName());
                baseViewHolder.setOnClickListener(R.id.clAppItem, v -> getClick(item.getCode()));
            }
        };

    }


    private void getClick(int code) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        switch (code) {
            case AppModule.FOOD:
                intent.putExtra(GlobalParam.APPURL, GlobalParam.FoodAppUrl);
                startActivity(intent);
                break;
            case AppModule.dyH5:
                String schoolId = GlobalParam.getSchoolInfo() == null ? "" : GlobalParam.getSchoolInfo().getSchoolId();
                String classId = GlobalParam.getClassInfo() == null ? "" : GlobalParam.getClassInfo().getClassId();
                String url = GlobalParam.DYH5 + "{\"schoolId\":\"" + schoolId + "\",\"departId\":\"" + classId + "\"}";
                Log.i("h5--url:", url);
                intent.putExtra(GlobalParam.APPURL, url);
                startActivity(intent);
                break;
            case AppModule.VIDEO:
                if (StringUtils.isEmpty(GlobalParam.getJKIP())) {
                    Toast.makeText(getActivity(), "该设备未绑定监控", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent1 = new Intent(getActivity(), MonitorActivity.class);
                String ip = GlobalParam.getJKIP();
                Log.i("监控ip:", ip);
                intent1.putExtra(GlobalParam.APPURL, ip);
                startActivity(intent1);
                break;
            case AppModule.WEIXIN:
                String uri="weixin://";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
                break;
            default:
                break;
        }
    }


    public boolean isMobile_spExist_WeiXin() {
        PackageManager    manager = Objects.requireNonNull(getActivity()).getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase("com.tencent.mm"))
                return true;
        }
        return false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("ApplicationFragment:", "onHiddenChanged==" + hidden + "     firstStart==" + firstStart);
        if (!hidden && !firstStart) {
            init();
        }
    }
}
