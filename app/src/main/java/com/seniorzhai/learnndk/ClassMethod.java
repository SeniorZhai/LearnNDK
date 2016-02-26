package com.seniorzhai.learnndk;

import android.util.Log;

/**
 * Created by zhai on 16/2/26.
 */
public class ClassMethod {
    private static void callStaticMethod(String str) {
        Log.d("log",str);
    }

    private void callInstanceMethod(String str,int i){
        Log.d("log",str);
    }
}
