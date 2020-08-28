package com.electronclass.common.basemvp.presenter;

import android.content.Context;

import com.electronclass.common.basemvp.contract.ApplicationContract;
import com.electronclass.common.basemvp.model.ApplicationModel;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.EcardType;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassInfo;
import com.electronclass.pda.mvp.entity.ECardDetail;
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
    public void getClassAndSchool(Context context) {
        mModel.getClassAndSchool( context );
    }

    @Override
    public void onClassAndSchool(ECardDetail eCardDetail) {
        ClassInfo classInfo  = new ClassInfo();
        classInfo.setClassId(eCardDetail.getClassId());
        classInfo.setClassName(eCardDetail.getClassName());

        SchoolInfo schoolInfo = new SchoolInfo();
        schoolInfo.setSchoolId(eCardDetail.getSchoolId());
        schoolInfo.setName(eCardDetail.getSchoolName());

        GlobalParam.setClassInfo( classInfo );
        GlobalParam.setSchoolInfo( schoolInfo );
        GlobalParam.setEcardNo( eCardDetail.getEcardNo() );
        GlobalParam.setJKIP(eCardDetail.getIp());
        EcardType.setType(eCardDetail.getType());//设置班牌类型
        mView.onClassAndSchool();
    }

    @Override
    public void getCardAttendance(String studentCardNo) {
        mModel.getCardAttendance( studentCardNo );
    }

    @Override
    public void onCardAttendance(String msg) {
        mView.onCardAttendance( msg );
    }

}
