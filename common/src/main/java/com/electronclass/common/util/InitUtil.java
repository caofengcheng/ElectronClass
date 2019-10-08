package com.electronclass.common.util;

import com.electronclass.common.base.BaseApplication;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.pda.mvp.rest.RestManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;


public class InitUtil {

    public static void init() {
        makeAppDir();
        initCrashHandler();
        initFresco();
    }

    /**
     * 创建app需要用的文件夹
     */
    private static void makeAppDir() {
        /*创建目录*/
        String path = FileUtil.getAppSDMainPath();
        FileUtil.makeDir(path);

        FileUtil.makeDir(path + Constants.DIR_CRASH_LOGS);
        FileUtil.makeDir(path + Constants.DIR_DOWNLOADS);
        FileUtil.makeDir(path + Constants.DIR_LOGS);
        FileUtil.makeDir(path + Constants.DIR_IMAGE_CACHE);
        FileUtil.makeDir(path + Constants.DIR_RECORDS);
        FileUtil.makeDir(path + Constants.DIR_ZIP);
    }


    private static void initCrashHandler() {
        CrashHandler.getInstance().init( BaseApplication.getInstance().getApplicationContext());
    }

    private static void initFresco() {
        Fresco.initialize(BaseApplication.getInstance().getApplicationContext());
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }

    private static void initRest() {
        RestManager.getInstance().initRest( GlobalParam.pUrl);
    }
}
