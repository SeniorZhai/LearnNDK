package com.seniorzhai.learnndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) v).setText(handleString("Button按钮"));
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logStringCritical(" I have a dream that one day this nation will rise up, live up to the true meaning of its creed: “We hold these truths to be self-evident; that all men are created equal.”");
            }
        });
    }

    public native String handleString(String str);

    public native void logStringCritical(String str);

    static {
        System.loadLibrary("string-handle");
    }
}
