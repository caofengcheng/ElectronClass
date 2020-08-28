package com.electronclass.aclass.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import com.electronclass.pda.mvp.entity.CouresNode;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程
 */
public class CouresFragment extends BaseFragment<CouresContract.Presenter> implements CouresContract.View {

    private FragmentCouresBinding binding;
    private List<CouresNode> coures1 = new ArrayList<>();
    private List<CouresNode> coures2 = new ArrayList<>();
    private List<CouresNode> coures3 = new ArrayList<>();
    private List<CouresNode> coures4 = new ArrayList<>();
    private List<CouresNode> coures5 = new ArrayList<>();
    private List<CouresNode> coures6 = new ArrayList<>();
    private List<CouresNode> coures7 = new ArrayList<>();
    private CommonRecyclerViewAdapter<CouresNode> weekAdapter1;
    private CommonRecyclerViewAdapter<CouresNode> weekAdapter2;
    private CommonRecyclerViewAdapter<CouresNode> weekAdapter3;
    private CommonRecyclerViewAdapter<CouresNode> weekAdapter4;
    private CommonRecyclerViewAdapter<CouresNode> weekAdapter5;
    private CommonRecyclerViewAdapter<CouresNode> weekAdapter6;
    private CommonRecyclerViewAdapter<CouresNode> weekAdapter7;
    private boolean isHave = false;//是否已经获取到数据

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coures, container, false);
        View view = binding.getRoot();
        init(view);
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
        logger.info("CouresFragment--getData");
        if (GlobalParam.getClassInfo() == null) {
            logger.debug("CouresFragment:initData null");
            Tools.displayToast("请先绑定班牌班级！");
            return;
        }
        if (isHave) {
            return;
        }
        mPresenter.getCoures();
    }

    @Override
    public void onCoures(List<CouresNode> coures) {
        clearList();
        for (CouresNode couresItem : coures) {
            switch (couresItem.getWeek()) {
                case Week.WEEK1:
                    coures1.add(couresItem);
                    break;
                case Week.WEEK2:
                    coures2.add(couresItem);
                    break;
                case Week.WEEK3:
                    coures3.add(couresItem);
                    break;
                case Week.WEEK4:
                    coures4.add(couresItem);
                    break;
                case Week.WEEK5:
                    coures5.add(couresItem);
                    break;
                case Week.WEEK6:
                    coures6.add(couresItem);
                    break;
                case Week.WEEK7:
                    coures7.add(couresItem);
                    break;
            }
        }
        setData();
        isHave = true;
    }

    private void setData() {
        weekAdapter1.setData(coures1);
        weekAdapter2.setData(coures2);
        weekAdapter3.setData(coures3);
        weekAdapter4.setData(coures4);
        weekAdapter5.setData(coures5);
        weekAdapter6.setData(coures6);
        weekAdapter7.setData(coures7);
        weekAdapter1.notifyDataSetChanged();
        weekAdapter2.notifyDataSetChanged();
        weekAdapter3.notifyDataSetChanged();
        weekAdapter4.notifyDataSetChanged();
        weekAdapter5.notifyDataSetChanged();
        weekAdapter6.notifyDataSetChanged();
        weekAdapter7.notifyDataSetChanged();
    }

    private void clearList() {
        coures1.clear();
        coures2.clear();
        coures3.clear();
        coures4.clear();
        coures5.clear();
        coures6.clear();
        coures7.clear();
    }


    @Override
    public void onError(String errorMessage) {
        super.onError(errorMessage);
    }

    private void setBackGroundColor() {
        binding.tvWeek1.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_6_class1));
        binding.tvWeek2.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_6_class2));
        binding.tvWeek3.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_6_class3));
        binding.tvWeek4.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_6_class4));
        binding.tvWeek5.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_6_class5));
        binding.tvWeek6.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_6_class6));
        binding.tvWeek7.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_6_class7));

    }

    private void setWeekOne() {
        weekAdapter1 = new CommonRecyclerViewAdapter<CouresNode>(R.layout.class_item, false, false) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, CouresNode item) {
                setBaseViewHolder(baseViewHolder, item, R.drawable.shape_6_class1);
            }
        };
        setWeekAll(weekAdapter1,binding.monDay);
    }

    private void setWeekTwo() {
        weekAdapter2 = new CommonRecyclerViewAdapter<CouresNode>(R.layout.class_item, false, false) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, CouresNode item) {
                setBaseViewHolder(baseViewHolder, item,R.drawable.shape_6_class2);
            }
        };
        setWeekAll(weekAdapter2,binding.tuesDay);
    }

    private void setWeekThree() {
        weekAdapter3 = new CommonRecyclerViewAdapter<CouresNode>(R.layout.class_item, false, false) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, CouresNode item) {
                setBaseViewHolder(baseViewHolder, item, R.drawable.shape_6_class3);

            }
        };
        setWeekAll(weekAdapter3,binding.wednesDay);
    }

    private void setWeekTour() {
        weekAdapter4 = new CommonRecyclerViewAdapter<CouresNode>(R.layout.class_item, false, false) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, CouresNode item) {
                setBaseViewHolder(baseViewHolder, item, R.drawable.shape_6_class4);
            }
        };
        setWeekAll(weekAdapter4,binding.thursDay);
    }

    private void setWeekTive() {
        weekAdapter5 = new CommonRecyclerViewAdapter<CouresNode>(R.layout.class_item, false, false) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, CouresNode item) {
                setBaseViewHolder(baseViewHolder, item, R.drawable.shape_6_class5);
            }
        };
        setWeekAll(weekAdapter5,binding.friDay);
    }

    private void setWeekSix() {
        weekAdapter6 = new CommonRecyclerViewAdapter<CouresNode>(R.layout.class_item, false, false) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, CouresNode item) {
                setBaseViewHolder(baseViewHolder, item, R.drawable.shape_6_class6);
            }
        };
        setWeekAll(weekAdapter6,binding.saturDay);
    }

    private void setWeekSeven() {
        weekAdapter7 = new CommonRecyclerViewAdapter<CouresNode>(R.layout.class_item, false, false) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, CouresNode item) {
                setBaseViewHolder(baseViewHolder, item, R.drawable.shape_6_class7);

            }
        };
        setWeekAll(weekAdapter7,binding.sunDay);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        logger.info("CouresFragment--onHiddenChanged:" + hidden);

    }

    private void setWeekAll(CommonRecyclerViewAdapter<CouresNode> couresNodeCommonRecyclerViewAdapter, RecyclerView id){
        couresNodeCommonRecyclerViewAdapter.bindRecyclerView(id, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private void setBaseViewHolder(BaseViewHolder baseViewHolder, CouresNode item, int bg) {
        baseViewHolder.setText(R.id.classItem, StringUtils.isNotEmpty(item.getCourseName()) ? item.getCourseName() : "");
        baseViewHolder.setBackgroundResources(R.id.classCl, bg);
    }
}
