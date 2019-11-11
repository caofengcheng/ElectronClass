package com.electronclass.generalui;

import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.base.BaseFragment;

/**
 * description:
 * author:      dillion
 * create:      2019/1/23 16:10
 */
public class WaitingDialogUtil {

    private static WaitingDialogFragment waitingDialogFragment;

    public static void showWaitingDialog(BaseActivity baseActivity) {
        if (waitingDialogFragment == null) {
            waitingDialogFragment = new WaitingDialogFragment();
            waitingDialogFragment.show(baseActivity.getSupportFragmentManager(), "WaitingDialogFragment");
        } else {
            waitingDialogFragment.dismiss();
            waitingDialogFragment.show(baseActivity.getSupportFragmentManager(), "WaitingDialogFragment");
        }
    }

    public static void closeWaitingDialog() {
        if (waitingDialogFragment != null) {
            waitingDialogFragment.dismiss();
        }
    }

    public static void showWaitingDialogFragment(BaseFragment baseFragment) {
        if (waitingDialogFragment == null) {
            waitingDialogFragment = new WaitingDialogFragment();
            waitingDialogFragment.show(baseFragment.getFragmentManager(), "WaitingDialogFragment");
        } else {
            waitingDialogFragment.dismiss();
            waitingDialogFragment.show(baseFragment.getFragmentManager(), "WaitingDialogFragment");
        }
    }
}
