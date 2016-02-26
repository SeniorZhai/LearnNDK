package com.seniorzhai.learnndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.callS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callJavaStaticMethod();
            }
        });
        findViewById(R.id.callI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callJavaInstanceMethod();
            }
        });
    }

    public static native void callJavaStaticMethod();

    public static native void callJavaInstanceMethod();


    static {
        System.loadLibrary("method");
    }
}
