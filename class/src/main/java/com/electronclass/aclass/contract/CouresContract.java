package com.electronclass.aclass.contract;

import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.Coures;

import java.util.List;

public interface CouresContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void getCoures();
    }

    interface View extends BaseView {
        void onCoures(List<Coures>coures);
    }

    interface Presenter extends BasePresenterInterface<View>{
        void getCoures();
        void onCoures(List<Coures>coures);

    }
}
