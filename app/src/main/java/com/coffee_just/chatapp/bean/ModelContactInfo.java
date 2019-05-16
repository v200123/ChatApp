package com.coffee_just.chatapp.bean;

import android.content.Context;

import com.coffee_just.chatapp.Untils.L;

import org.litepal.LitePal;

import java.util.ArrayList;

public class ModelContactInfo {
    private static ModelContactInfo sModelContactInfo= null;
    private ArrayList<contactInfo> mContactInfos  ;
    public static ModelContactInfo instance(Context context){
        if(sModelContactInfo==null)
        sModelContactInfo = new ModelContactInfo(context);
        return sModelContactInfo;
    }

    private ModelContactInfo(Context context) {
    }

    public ArrayList<contactInfo> getContactInfos(){
        mContactInfos = (ArrayList<contactInfo>) LitePal.findAll(contactInfo.class,new long[] {});
        L.d("数据得到更新了");
        return mContactInfos;
    }
}
