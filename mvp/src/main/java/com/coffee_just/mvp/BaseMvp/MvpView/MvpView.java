package com.coffee_just.mvp.BaseMvp.MvpView;

import android.content.Context;

public interface    MvpView {
    Context getContext();
    void showLoadingDialog();
    void dismissLoadingDialog();
    void showLoadingBar();
    void dismissLoadingBar();
    void clearLoading();

}
