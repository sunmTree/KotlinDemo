package com.kotlin.module.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

public class SelfDrawerLayout extends DrawerLayout {
    private static final String LOG_TAG = "SelfDrawerLayout";

    public SelfDrawerLayout(@NonNull Context context) {
        super(context);
    }

    public SelfDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void openDrawer(@NonNull View drawerView, boolean animate) {
        super.openDrawer(drawerView, animate);
        Log.d(LOG_TAG, " open Drawer " + animate + " " + Log.getStackTraceString(new RuntimeException()));
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.d(LOG_TAG, Log.getStackTraceString(new RuntimeException("compute scroll")));
    }

    @Override
    public void addDrawerListener(@NonNull DrawerListener listener) {
        super.addDrawerListener(listener);
        Log.d(LOG_TAG, " listener " + listener);
    }

}
