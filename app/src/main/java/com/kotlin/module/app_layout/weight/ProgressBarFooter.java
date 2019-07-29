package com.kotlin.module.app_layout.weight;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.kotlin.demo.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by changjl on 17-11-4.
 */

public class ProgressBarFooter extends RelativeLayout implements RefreshFooter {

    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private ProgressBar mProgressBar;
    protected RefreshKernel mRefreshKernel;
    protected boolean mLoadmoreFinished = false;
    protected int mFinishDuration = 500;

    public ProgressBarFooter(Context context) {
        super(context);
        init(context);
    }

    public ProgressBarFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressBarFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_footer_progress, this, true);
        mProgressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onPullingUp(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onPullReleasing(float percent, int offset, int footerHeight, int extendHeight) {

    }

    @Override
    public void onLoadmoreReleased(RefreshLayout layout, int footerHeight, int extendHeight) {

    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public boolean setLoadmoreFinished(boolean finished) {
        if (!mLoadmoreFinished) {
            mProgressBar.setVisibility(VISIBLE);
            mProgressBar.animate().rotation(36000).setDuration(100000);

        }
        return false;
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        if (!mLoadmoreFinished) {
            mProgressBar.animate().rotation(0).setDuration(300);
            mProgressBar.setVisibility(VISIBLE);
            return mFinishDuration;
        }
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }


    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        mRefreshKernel = kernel;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }
}
