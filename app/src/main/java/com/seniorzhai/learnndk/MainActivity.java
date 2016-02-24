package com.seniorzhai.learnndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText(stringFromJNI());
    }

    public native String  stringFromJNI();

    static {
        System.loadLibrary("hello-jni");
    }
}
