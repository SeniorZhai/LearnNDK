package com.seniorzhai.learnndk;

import android.util.Log;

/**
 * Created by zhai on 16/2/29.
 */
public class SubClass extends SupClass {
    @Override
    public void run() {
        Log.d("log", "sub run!!!");
    }
}
