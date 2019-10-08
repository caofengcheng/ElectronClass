package com.electronclass.electronclass.presenter;

import com.electronclass.electronclass.contract.CardContract;
import com.electronclass.electronclass.model.CardModel;
import com.electronclass.pda.mvp.base.BasePresenter;

public class CardPresenter extends BasePresenter<CardContract.Model,CardContract.View> implements CardContract.Presenter {


    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
    }

    @Override
    protected void initModel() {
         mModel = new CardModel();
         mModel.setPresenter( this );
    }

    @Override
    public void getCardAttendance(String studentCardNo) {
        mModel.getCardAttendance( studentCardNo );
    }

    @Override
    public void onCardAttendance(boolean sure) {
        mView.onCardAttendance( sure );
    }
}
