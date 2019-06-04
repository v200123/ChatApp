package com.coffee_just.mvp.BaseMvp.Presenter;

import android.content.Context;

import com.coffee_just.mvp.BaseMvp.MvpView.MvpView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V> {
   protected WeakReference<V> mViewRef;
   //进行绑定

    /**
     * 将传入的View接口实例，通过弱引用（WeakReference）把Presenter与View进行绑定。
     * @param view  界面更新接口实例
     */
    public void attachView(V view )
    {
        mViewRef = new WeakReference<>(view);
    }

    /**
     * 进行解绑
     */
    public void datch(){
        mViewRef.clear();
    }




}
