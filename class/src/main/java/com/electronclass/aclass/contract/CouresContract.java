package com.electronclass.aclass.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.Coures;
import com.electronclass.pda.mvp.entity.CouresNode;

import java.util.List;

public interface CouresContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void getCoures();
    }

    interface View extends BaseView {
        void onCoures(List<CouresNode>coures);
    }

    interface Presenter extends BasePresenterInterface<View>{
        void getCoures();
        void onCoures(List<CouresNode>coures);

    }
}
