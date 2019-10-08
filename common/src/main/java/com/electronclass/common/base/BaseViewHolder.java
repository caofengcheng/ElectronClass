package com.electronclass.common.base;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.suke.widget.SwitchButton;

/**
 * Created by lion on 2018-03-22.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> sparseArray;

    public BaseViewHolder(View itemView) {
        super(itemView);
        sparseArray = new SparseArray<>();
    }

    public View getItemView() {
        return itemView;
    }

    public View getView(int viewId) {
        View view = this.sparseArray.get(viewId);
        if (view == null) {
            view = this.itemView.findViewById(viewId);
            this.sparseArray.put(viewId, view);
        }
        return view;
    }

    public BaseViewHolder callOnClick(int id) {
        getView(id).callOnClick();
        return this;
    }

    public void setItemVisibility(boolean isVisible) {
        ViewGroup.LayoutParams layoutParams = this.itemView.getLayoutParams();
        if (isVisible) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            this.itemView.setVisibility(View.VISIBLE);
        } else {
            this.itemView.setVisibility(View.GONE);
            layoutParams.height = 0;
            layoutParams.width = 0;
        }

        this.itemView.setLayoutParams(layoutParams);
    }

    public BaseViewHolder setTag(int id, Object tag) {
        getView(id).setTag(tag);
        return this;
    }

    public String getText(int id) {
        return ((TextView) getView(id)).getText().toString();
    }

    public BaseViewHolder setText(int id, String text) {
        setText(id, text, "--");
        return this;
    }

    public BaseViewHolder setText(int id, String text, String defaultText) {
        ((TextView) getView(id)).setText(TextUtils.isEmpty(text) ? defaultText : text);
        return this;
    }

    public BaseViewHolder appendText(int id, String text) {
        TextView textView = (TextView) getView(id);
        textView.setText((textView.getText() + text));
        return this;
    }

    public BaseViewHolder setTextColor(int id, int textColor) {
        ((TextView) getView(id)).setTextColor(textColor);
        return this;
    }

    public BaseViewHolder setImageDrawable(int id, Drawable drawable) {
        ((ImageView) getView(id)).setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageURI(int id, String uri) {
        ((SimpleDraweeView) getView(id)).setImageURI(uri);
        return this;
    }

    public BaseViewHolder setBackgroundColor(int id, int color) {
        getView(id).setBackgroundColor(color);
        return this;
    }
    public BaseViewHolder setBackgroundResources(int id, int Resources) {
        getView(id).setBackgroundResource(Resources);
        return this;
    }

    public boolean isChecked(int id) {
        return ((CompoundButton) getView(id)).isChecked();
    }

    public BaseViewHolder setChecked(int id, boolean checked) {
        ((CompoundButton) getView(id)).setChecked(checked);
        return this;
    }

    public BaseViewHolder setEnabled(int id, boolean enable) {
        getView(id).setEnabled(enable);
        return this;
    }

    public BaseViewHolder setVisibility(int id, int visibility) {
        getView(id).setVisibility(visibility);
        return this;
    }

    public BaseViewHolder setOnClickListener(int id, View.OnClickListener onclickListener) {
        getView(id).setOnClickListener(onclickListener);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(int id, SwitchButton.OnCheckedChangeListener onCheckedChangeListener) {
        ((SwitchButton) getView(id)).setOnCheckedChangeListener(onCheckedChangeListener);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(int id, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        ((CompoundButton) getView(id)).setOnCheckedChangeListener(onCheckedChangeListener);
        return this;
    }
}