package com.electronclass.electronclass.UI;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.xhapimanager.XHApiManager;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalPage;
import com.electronclass.common.util.DateUtil;
import com.electronclass.electronclass.BuildConfig;
import com.electronclass.electronclass.R;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TopBar extends ConstraintLayout {
    private    Handler handler;
    private Timer   timer;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    private void init(Context context) {
        View                 view           = LayoutInflater.from(context).inflate(R.layout.top_bar, this, true);
        final BaseViewHolder baseViewHolder = new BaseViewHolder(view);
        ImageView schoolName = view.findViewById( R.id.schoolName );
        if (BuildConfig.GUARD_PACKAGE == GlobalPage.MULAN ) {
            schoolName.setImageResource( R.drawable.topbar );
        } else if (BuildConfig.GUARD_PACKAGE == GlobalPage.HENGHONGDA) {
            schoolName.setImageResource( R.drawable.xx );
        }

        handler = new ViewHandler();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.obj = baseViewHolder;
                handler.sendMessage(message);

            }
        }, 0, 1000);

    }

    static class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Date           date           = DateUtil.getNowDate();
            BaseViewHolder baseViewHolder = (BaseViewHolder) msg.obj;
            baseViewHolder.setText(R.id.tvDate, DateUtil.dateToString(date, "yyyy/MM/dd"));
            baseViewHolder.setText(R.id.tvTime, DateUtil.dateToString(date, "HH:mm"));
            baseViewHolder.setText(R.id.tvWeek, DateUtil.getWeekOfDate(date));
        }
    }
}
