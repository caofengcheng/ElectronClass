package com.electronclass.common.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class VerticalTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private static final int FLAG_START_AUTO_SCROLL = 0;
    private static final int FLAG_STOP_AUTO_SCROLL  = 1;

    private float mTextSize  = 16;
    private int   mPadding   = 5;
    private int   textColor  = Color.BLACK;
    private int   gravity    = Gravity.CENTER;
    private int   background = Color.parseColor( "#00000000" );
    private int   maxLines   = 1;

    /**
     * @param textSize  字号
     * @param padding   内边距
     * @param textColor 字体颜色
     */
    public void setText(float textSize, int padding, int textColor) {
        mTextSize = textSize;
        mPadding = padding;
        this.textColor = textColor;
    }

    private OnItemClickListener itemClickListener;
    private OnItemRollListener  itemRollListener;
    private Context             mContext;
    private int                 currentId = -1;
    private ArrayList<String>   textList;
    private Handler             handler;
    boolean isStart = false;

    public VerticalTextView(Context context) {
        this( context, null );
        mContext = context;
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super( context, attrs );
        mContext = context;
        textList = new ArrayList<String>();
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
    /**
     * 横向滚动
     *
     * @param animDuration
     */
    public void setHorizontalAnimTime(long animDuration) {
        setFactory( this );
        Animation in = new TranslateAnimation( animDuration, 0, 0, 0 );
        in.setDuration( animDuration );
        in.setInterpolator( new AccelerateInterpolator() );
        Animation out = new TranslateAnimation( 0, -animDuration, 0, 0 );
        out.setDuration( animDuration );
        out.setInterpolator( new AccelerateInterpolator() );
        setInAnimation( in );
        setOutAnimation( out );
    }

    /**
     * 纵向滚动
     *
     * @param animDuration
     */
    public void setVerticalAnimTime(long animDuration) {
        setFactory( this );
        Animation in = new TranslateAnimation( 0, 0, animDuration, 0 );
        in.setDuration( animDuration );
        in.setInterpolator( new AccelerateInterpolator() );
        Animation out = new TranslateAnimation( 0, 0, 0, -animDuration );
        out.setDuration( animDuration );
        out.setInterpolator( new AccelerateInterpolator() );
        setInAnimation( in );
        setOutAnimation( out );
    }

    /**
     * 间隔时间
     *
     * @param time
     */
    public void setTextStillTime(final long time) {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        if (textList.size() > 0) {
                            currentId++;
                            setText( textList.get( currentId % textList.size() ) );
                        }
                        handler.sendEmptyMessageDelayed( FLAG_START_AUTO_SCROLL, time );
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        handler.removeMessages( FLAG_START_AUTO_SCROLL );
                        break;
                }
            }
        };
    }

    /**
     * 设置数据源
     *
     * @param titles
     */
    public void setTextList(ArrayList<String> titles) {
        textList.clear();
        textList.addAll( titles );
        currentId = -1;
    }

    /**
     * 开始滚动
     */
    public void startAutoScroll() {
        isStart = true;
        handler.sendEmptyMessage( FLAG_START_AUTO_SCROLL );
    }


    /**
     * 停止滚动
     */
    public void stopAutoScroll() {
        isStart = false;
        handler.sendEmptyMessage( FLAG_STOP_AUTO_SCROLL );
    }


    /**
     * 设置最大显示行数
     * @param maxLines
     */
    public void setMaxLines(int maxLines){
        this.maxLines = maxLines;
    }
    @Override
    public View makeView() {
        TextView t = new TextView( mContext );
        t.setGravity( gravity );
        t.setMaxLines( maxLines );
        t.setPadding( mPadding, mPadding, mPadding, mPadding );
        t.setTextColor( textColor );
        t.setTextSize( mTextSize );
        t.setBackgroundColor( background );

        t.setClickable( true );

        t.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (itemRollListener != null && textList.size() > 0 && currentId != -1) {
                    itemRollListener.onItemRoll( currentId % textList.size() );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        return t;
    }

    /**
     * 设置点击事件监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {
        /**
         * 点击回调
         *
         * @param position 当前点击ID
         */
        void onItemClick(int position);
    }


    public void setOnItemRollListener(OnItemRollListener itemRollListener) {
        this.itemRollListener = itemRollListener;
    }

    public interface OnItemRollListener {
        /**
         * 点击回调
         *
         * @param position 当前点击ID
         */
        void onItemRoll(int position);
    }

    public boolean isStart() {
        return isStart;
    }
}
