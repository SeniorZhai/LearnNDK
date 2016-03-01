package com.seniorzhai.learnndk;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int SCALE_FACTOR = 6;
    private boolean mCompress;
    private TextView mTime;
    private Bitmap mBitmap, mCompressBitmap;
    private ImageView mImageJava, mImageJniPixels, mImageJniBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBlur();
        mBlurringView = (BlurringView) findViewById(R.id.blurring_view);
        View blurredView = findViewById(R.id.blurred_view);

        mBlurringView.setBlurredView(blurredView);

        mImageViews[0] = (ImageView) findViewById(R.id.image0);
        mImageViews[1] = (ImageView) findViewById(R.id.image1);
        mImageViews[2] = (ImageView) findViewById(R.id.image2);

    }

    private ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mBlurringView.invalidate();
        }
    };

    public void shift(View view) {
        if (!mShifted) {
            for (ImageView imageView : mImageViews) {
                ObjectAnimator tx = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, (mRandom.nextFloat() - 0.5f) * 500);
                tx.addUpdateListener(listener);
                ObjectAnimator ty = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, (mRandom.nextFloat() - 0.5f) * 500);
                ty.addUpdateListener(listener);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(tx, ty);
                set.setDuration(3000);
                set.setInterpolator(new OvershootInterpolator());
                set.addListener(new AnimationEndListener(imageView));
                set.start();
            }
            mShifted = true;
        } else {
            for (ImageView imageView : mImageViews) {
                ObjectAnimator tx = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, 0);
                tx.addUpdateListener(listener);
                ObjectAnimator ty = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, 0);
                ty.addUpdateListener(listener);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(tx, ty);
                set.setDuration(3000);
                set.setInterpolator(new OvershootInterpolator());
                set.addListener(new AnimationEndListener(imageView));
                set.start();
            }
            mShifted = false;
        }
    }

    private BlurringView mBlurringView;

    private ImageView[] mImageViews = new ImageView[3];

    private Random mRandom = new Random();

    private boolean mShifted;

    private static class AnimationEndListener implements Animator.AnimatorListener {

        View mView;

        public AnimationEndListener(View v) {
            mView = v;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            mView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mView.setLayerType(View.LAYER_TYPE_NONE, null);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            mView.setLayerType(View.LAYER_TYPE_NONE, null);
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyBlur();
    }


    private void initBlur() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        mImageJava = (ImageView) findViewById(R.id.image_blur_java);
        mImageJniPixels = (ImageView) findViewById(R.id.image_blur_jni_pixels);
        mImageJniBitmap = (ImageView) findViewById(R.id.image_blur_jni_bitmap);
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
                applyBlur();
            }
        });
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
                    for (int i = 1; i < 4; i++) {
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
        if (i == 1)
            view = mImageJava;
        else if (i == 2)
            view = mImageJniPixels;
        else if (i == 3)
            view = mImageJniBitmap;

        long startMs = System.currentTimeMillis();

        // Is Compress
        float radius = 20;
        Bitmap overlay = mBitmap;
        if (mCompress) {
            radius = 3;
            overlay = mCompressBitmap;
        }

        // Java
        if (i == 1)
            overlay = Blur.blur(overlay, (int) radius, false);
            // Bitmap JNI Native
        else if (i == 2)
            overlay = Blur.blurNatively(overlay, (int) radius, false);
            // Pixels JNI Native
        else if (i == 3)
            overlay = Blur.blurNativelyPixels(overlay, (int) radius, false);

        // Show
        showDrawable(view, overlay);

        return System.currentTimeMillis() - startMs;
    }

    private void clearDrawable() {
        mImageJava.setImageBitmap(null);
        mImageJniPixels.setImageBitmap(null);
        mImageJniBitmap.setImageBitmap(null);
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