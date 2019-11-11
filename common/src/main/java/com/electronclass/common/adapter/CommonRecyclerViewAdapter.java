package com.electronclass.common.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.electronclass.common.R;
import com.electronclass.common.base.BaseViewHolder;

import java.util.List;

public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private List<T> data;
    private int emptyViewId;
    private boolean hasFooter;
    private static final int ITEM_TYPE_EMPTY_VIEW = -1;
    static final int ITEM_TYPE_NORMAL = -2;
    private static final int ITEM_TYPE_FOOTER = -3;
    private static final int ITEM_TYPE_ERROR = -4;
    private boolean hasEmpty;
    private SparseIntArray layoutResourceMap;
    private boolean isShowError;

    protected CommonRecyclerViewAdapter(int layout) {
        this(layout, true, true, null);
    }

    protected CommonRecyclerViewAdapter(SparseIntArray layoutResourceMap) {
        this(0, true, true, layoutResourceMap);
    }

    protected CommonRecyclerViewAdapter(int layout, boolean hasFooter, boolean hasEmpty) {
        this(layout, hasFooter, hasEmpty, null);
    }

    protected CommonRecyclerViewAdapter(int layout, boolean hasFooter, boolean hasEmpty, SparseIntArray layoutResourceMap) {
        if (layoutResourceMap == null) {
            layoutResourceMap = new SparseIntArray();
            layoutResourceMap.put(ITEM_TYPE_NORMAL, layout);
        }
        isShowError = false;
        this.hasFooter = hasFooter;
        this.hasEmpty = hasEmpty;
        this.emptyViewId = R.layout.item_empty;
        this.layoutResourceMap = layoutResourceMap;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowError) {
            return ITEM_TYPE_ERROR;
        }
        if (isDataEmpty()) {
            return ITEM_TYPE_EMPTY_VIEW;
        }
        if (hasFooter && position == data.size()) {
            return ITEM_TYPE_FOOTER;
        }
        return ITEM_TYPE_NORMAL;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_EMPTY_VIEW:
            case ITEM_TYPE_ERROR:
                return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty, parent, false));
            case ITEM_TYPE_FOOTER:
                return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_more_data, parent, false));
            default:
                return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutResourceMap.get(viewType), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int position) {
        if (isShowError) {
            showWholeView(baseViewHolder, R.layout.item_error);
            isShowError = false;
        }
        if (isDataEmpty()) {
            if (hasEmpty) {
//                showWholeView(baseViewHolder, emptyViewId);
            }
        } else if (position < data.size()) {
            convert(baseViewHolder, data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (isDataEmpty())
            return 1;
        return data.size() + (hasFooter ? 1 : 0);
    }

    private void showWholeView(@NonNull BaseViewHolder baseViewHolder, int layout) {
        LinearLayout llEmptyView = (LinearLayout) baseViewHolder.getView(R.id.llEmptyView);
        llEmptyView.removeAllViews();
        LayoutInflater.from(llEmptyView.getContext()).inflate(layout, llEmptyView, true);
    }

    private boolean isDataEmpty() {
        return data == null || data.isEmpty();
    }

    protected boolean isLastItem(int position) {
        return position == data.size() - 1;
    }

    public abstract void convert(BaseViewHolder baseViewHolder, T item);

    public void bindRecyclerView(RecyclerView recyclerView) {
        bindRecyclerView(recyclerView, new LinearLayoutManager(recyclerView.getContext()));
    }

    public void bindRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
    }

    public void setEmptyView(int resId) {
        emptyViewId = resId;
    }

    public void showError() {
        isShowError = true;
        notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void addData(List<T> data) {
        if (data == null) {
            return;
        }
        if (this.data != null) {
            this.data.addAll(data);
        } else {
            setData(data);
        }
    }

    public List<T> getData() {
        return data;
    }
}
