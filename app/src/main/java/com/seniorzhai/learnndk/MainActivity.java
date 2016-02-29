package com.seniorzhai.learnndk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.callS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.name = "name";
                instanceField(student);
                Log.d("sdk_log", student.name);
            }
        });
        findViewById(R.id.callI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staticField();
                Log.d("sdk_log", Student.num + "");
            }
        });
    }

    public static native void instanceField(Student student);

    public static native void staticField();

    static {
        System.loadLibrary("field");
    }
}
