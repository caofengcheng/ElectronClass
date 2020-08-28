package com.electronclass.aclass.presenter;

import com.electronclass.aclass.contract.CouresContract;
import com.electronclass.aclass.model.CouresModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.Coures;
import com.electronclass.pda.mvp.entity.CouresNode;

import java.util.List;

public class CouresPresenter extends BasePresenter<CouresContract.Model, CouresContract.View> implements CouresContract.Presenter {

    @Override
    protected void initModel() {
        mModel = new CouresModel();
        mModel.setPresenter( this );
    }

    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
    }

    @Override
    public void getCoures() {
        mModel.getCoures();
    }

    @Override
    public void onCoures(List<CouresNode> coures) {
         mView.onCoures( coures );
    }
}
