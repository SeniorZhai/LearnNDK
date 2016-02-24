package com.seniorzhai.learnndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataType((short) 1, 2, 3, 4f, 5.0, 'c', "String", true, (byte) 0, "S", new int[]{1, 2, 3});
            }
        });
    }

    public native void dataType(short s, int i, long l, float f, double d, char c, String str, boolean bool, byte b, Object obj, int[] arr);

    static {
        System.loadLibrary("hello-jni");
    }
}
