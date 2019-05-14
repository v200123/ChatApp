package com.coffee_just.chatapp.Untils;

import android.util.Log;

public class L {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    public static void setLevel(int level) {
        L.level = level;
    }

    public static int level = VERBOSE;
    private static final String TAG = "com.lc.weChat";

    public static void v(String msg) {
        if (level <= VERBOSE) {
            Log.v(TAG, msg);
        }
    }
    public static void d( String msg) {
        if (level <= DEBUG) {
            Log.d(TAG, msg);
        }
    }
    public static void i(String msg) {
        if (level <= INFO) {
            Log.i(TAG, msg);
        }
    }
    public static void w(String msg) {
        if (level <= WARN) {
            Log.w(TAG, msg);
        }
    }
    public static void e( String msg) {
        if (level <= ERROR) {
            Log.e(TAG, msg);
        }
    }
}
