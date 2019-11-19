package com.electronclass.generalui;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.electronclass.common.util.Tools;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;



/**
 * A simple {@link Fragment} subclass.
 */
public class WaitingDialogFragment extends DialogFragment {
    private Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = LayoutInflater.from(context).inflate(R.layout.layout_waiting, null);
        SimpleDraweeView sdvLoading = view.findViewById( R.id.sdvLoading);
        Uri uri = Uri.parse("res:///" + R.drawable.loading);
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        sdvLoading.setController(draweeController);
        builder.setView(view);
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            //设置弹框的占屏宽
            dialog.getWindow().setLayout( Tools.dp2px(120), Tools.dp2px(100));

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.waiting_dialog_background);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        context = null;
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        context = null;
        super.onCancel(dialog);
    }

    @Override
    public void onDetach() {
        context = null;
        super.onDetach();
    }
}
