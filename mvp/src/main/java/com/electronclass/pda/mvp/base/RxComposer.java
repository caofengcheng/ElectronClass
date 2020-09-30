package com.electronclass.pda.mvp.base;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 页面：
 */
public class RxComposer {

    public static final int CHECK_NULL     = 1;
    public static final int NOT_CHECK_NULL = 2;

    public static <T> ObservableTransformer<T, T> composeObservable() {
        // 返回一个 Single 类型的观察者的 Transformer
        return upstream -> {
            // 给"上游"的 Single<T> 设置线程的调度与网络判断后传递给下游
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }

    public static <T> SingleTransformer<T, T> composeSingle() {
        // 返回一个 Single 类型的观察者的 Transformer
        return upstream -> {
            // 给"上游"的 Single<T> 设置线程的调度与网络判断后传递给下游
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }

    public static <T> SingleTransformer<T, T> composeSingle1() {
        // 返回一个 Single 类型的观察者的 Transformer
        return upstream -> {
            // 给"上游"的 Single<T> 设置线程的调度与网络判断后传递给下游
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        };
    }
}
