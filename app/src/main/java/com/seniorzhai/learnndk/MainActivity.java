package com.seniorzhai.learnndk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int SCALE_FACTOR = 6;
    private boolean mCompress;
    private TextView mTime;
    private Bitmap mBitmap, mCompressBitmap;
    private ImageView mImageJava, mImageJniPixels, mImageJniBitmap, mImageRSBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBlur();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initBlur() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        mImageJava = (ImageView) findViewById(R.id.image_blur_java);
        mImageJniPixels = (ImageView) findViewById(R.id.image_blur_jni_pixels);
        mImageJniBitmap = (ImageView) findViewById(R.id.image_blur_jni_bitmap);
        mImageRSBitmap = (ImageView) findViewById(R.id.image_blur_rs_bitmap);
        mTime = (TextView) findViewById(R.id.text_blur_time);

        ((ImageView) findViewById(R.id.image_blur_self)).setImageBitmap(mBitmap);

        Matrix matrix = new Matrix();
        matrix.postScale(1.0f / SCALE_FACTOR, 1.0f / SCALE_FACTOR);
        mCompressBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
                mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_blur_isCompress);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCompress = isChecked;
            }
        });
    }

    public void run(View v) {
        applyBlur();
    }


    private void applyBlur() {
        // First clear
        clearDrawable();

        // Run Thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("Blur Time: ");
                    for (int i = 0; i < 4; i++) {
                        sb.append(blur(i)).append(" ");
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTime.setText(sb.toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private long blur(int i) {
        ImageView view = null;
        if (i == 0)
            view = mImageJava;
        else if (i == 1)
            view = mImageJniPixels;
        else if (i == 2)
            view = mImageJniBitmap;
        else view = mImageRSBitmap;

        long startMs = System.currentTimeMillis();

        // Is Compress
        float radius = 20;
        Bitmap overlay = mBitmap;
        if (mCompress) {
            radius = 3;
            overlay = mCompressBitmap;
        }

        // Java
        if (i == 0)
            overlay = Blur.blur(overlay, (int) radius, false);
            // Bitmap JNI Native
        else if (i == 1)
            overlay = Blur.blurNatively(overlay, (int) radius, false);
            // Pixels JNI Native
        else if (i == 2)
            overlay = Blur.blurNativelyPixels(overlay, (int) radius, false);
        else overlay = Blur.blurRenderScript(getBaseContext(), overlay, (int) radius, false);

        // Show
        showDrawable(view, overlay);

        return System.currentTimeMillis() - startMs;
    }

    private void clearDrawable() {
        mImageJava.setImageBitmap(null);
        mImageJniPixels.setImageBitmap(null);
        mImageJniBitmap.setImageBitmap(null);
        mImageRSBitmap.setImageBitmap(null);
    }

    private void showDrawable(final ImageView view, final Bitmap overlay) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setImageBitmap(overlay);
            }
        });
    }
}