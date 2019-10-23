package com.electronclass.electronclass.UI;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.xhapimanager.XHApiManager;
import com.bumptech.glide.Glide;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalPage;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.event.SettingsEvent;
import com.electronclass.common.event.TopEvent;
import com.electronclass.common.util.DateUtil;
import com.electronclass.electronclass.AppApplication;
import com.electronclass.electronclass.BuildConfig;
import com.electronclass.electronclass.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TopBar extends ConstraintLayout {
    private    Handler handler;
    private Timer   timer;
    ImageView schoolName;
    TextView name;

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
        schoolName = view.findViewById( R.id.schoolName );
        name = view.findViewById( R.id.tvName );
//        if (BuildConfig.GUARD_PACKAGE == GlobalPage.MULAN ) {
//            schoolName.setImageResource( R.drawable.topbar );
//        } else if (BuildConfig.GUARD_PACKAGE == GlobalPage.HENGHONGDA) {
//            schoolName.setImageResource( R.drawable.xx );
//        }

        AppApplication.getInstance().setTopEvent(new TopEvent() {
            @Override
            public void Event() {
                if (GlobalParam.getSchoolInfo() != null) {
                    Glide.with( TopBar.this)
                            .load( GlobalParam.getSchoolInfo().getLogo())
                            .into( schoolName );
                    name.setText(GlobalParam.getSchoolInfo().getName());
                }
            }
        });


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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onTopEvent(TopEvent event) {

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
