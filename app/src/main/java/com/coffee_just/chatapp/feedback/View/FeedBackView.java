package com.coffee_just.chatapp.feedback.View;

import android.content.Context;

import com.coffee_just.mvp.BaseMvp.MvpView.MvpView;

public interface  FeedBackView extends MvpView {
    void showFeedbackSuccess();
    void showFeedBackFailed();

}
