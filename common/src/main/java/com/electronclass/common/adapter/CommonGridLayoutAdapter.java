package com.electronclass.common.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;

import com.electronclass.common.base.BaseViewHolder;

import java.util.List;

public abstract class CommonGridLayoutAdapter<T> {
    private GridLayout gridLayout;
    private int itemResourceId;
    private List<T> data;
    private int column;

    protected CommonGridLayoutAdapter(GridLayout gridLayout, int itemResourceId, int column) {
        this.gridLayout = gridLayout;
        this.itemResourceId = itemResourceId;
        this.column = column;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void notifyDataChanged() {
        if (data == null) {
            return;
        }
        gridLayout.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            addView(i, true);
        }
        if (data.size() % column == 0) {
            return;
        }
        for (int i = 0; i < column - data.size() % column; i++) {
            addView(i, false);
        }
    }

    private void addView(int position, boolean visible) {
        View view = LayoutInflater.from(gridLayout.getContext()).inflate(itemResourceId, gridLayout, false);
        GridLayout.Spec rowSpec = GridLayout.spec(position / column, 1.0f);     //设置它的行和列
        GridLayout.Spec columnSpec = GridLayout.spec(position % column, 1.0f);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
        layoutParams.setGravity(Gravity.FILL_HORIZONTAL);
        setMargins(layoutParams, position);
        if (visible) {
            convert(new BaseViewHolder(view), data.get(position), position);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
        gridLayout.addView(view, layoutParams);
    }

    protected boolean isFirstRow(int position) {
        return position < column;
    }

    protected boolean isLastRow(int position) {
        return position >= (data.size() / column) * column;
    }

    protected boolean isFirstColumn(int position) {
        return position % column == 0;
    }

    protected boolean isLastColumn(int position) {
        return position % column == column - 1;
    }

    public abstract void convert(BaseViewHolder baseViewHolder, T item, int position);

    public abstract void setMargins(GridLayout.LayoutParams layoutParams, int position);
}
