package com.seniorzhai.learnndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) v).setText(String.format("Sum=%d", sum(array)));
            }
        });
        findViewById(R.id.max).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) v).setText(String.format("Max=%d", max(array)));
            }
        });
        findViewById(R.id.min).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) v).setText(String.format("Min=%d", min(array)));
            }
        });
        findViewById(R.id.multiply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) v).setText(String.format("Result=%d", multiply(array)));
            }
        });
    }

    public native int sum(int[] array);

    public native int max(int[] array);

    public native int min(int[] array);

    public native int multiply(int[] array);

    static {
        System.loadLibrary("array");
    }
}
