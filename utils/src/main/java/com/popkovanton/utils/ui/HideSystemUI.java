package com.popkovanton.utils.ui;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Implementation to Activities onCreate with getLifecycle().addObserver(HideSystemUI(...));
 *
 * Add this to app theme:
 * windowFullscreen = true
 * windowActionBar = false
 * windowNoTitle = true
 */
public class HideSystemUI implements LifecycleObserver {

    private int hideSizeMargin = 30;
    private Activity activity;

    public HideSystemUI(Activity activity) {
        this.activity = activity;
    }

    public HideSystemUI(int hideSizeMargin, Activity activity) {
        this.hideSizeMargin = hideSizeMargin;
        this.activity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void hideUI() {
        if (activity != null) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            View decorView = activity.getWindow().getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private void hideUIAgain() {
        if (activity != null) {
            activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect r = new Rect();
                    //r will be populated with the coordinates of your view that area still visible.
                    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);

                    int heightDiff = activity.getWindow().getDecorView().getRootView().getHeight() - (r.bottom - r.top);
                    int widthDiff = activity.getWindow().getDecorView().getRootView().getWidth() - (r.right - r.left);
                    if (heightDiff > hideSizeMargin || widthDiff > hideSizeMargin) {
                        hideUI();
                    }
                }
            });
        }
    }
}
