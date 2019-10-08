package com.electronclass.common.basemvp.presenter;

import com.electronclass.common.basemvp.contract.ApplicationContract;
import com.electronclass.common.basemvp.model.ApplicationModel;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassInfo;
import com.electronclass.pda.mvp.entity.ClassMessage;
import com.electronclass.pda.mvp.entity.SchoolInfo;

public class ApplicationPresenter extends BasePresenter<ApplicationContract.Model, ApplicationContract.View> implements ApplicationContract.Presenter {


    @Override
    protected void initModel() {
        mModel = new ApplicationModel();
        mModel.setPresenter( this );
    }

    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
    }

    @Override
    public void getClassAndSchool() {
        mModel.getClassAndSchool();
    }

    @Override
    public void onClassAndSchool(ClassInfo classInfo, SchoolInfo schoolInfo, String ecardNo) {
        GlobalParam.setClassInfo( classInfo );
        GlobalParam.setSchoolInfo( schoolInfo );
        GlobalParam.setEcardNo( ecardNo );
        mView.onClassAndSchool();
    }


}
