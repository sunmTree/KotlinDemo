package com.kotlin.module.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.kotlin.demo.R;

public abstract class MatchBaseDialog extends BaseDialog implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener {
    private static final long ANIM_DURATION = 300L;

    public MatchBaseDialog(Context context) {
        super(context);
        mDialog.setOnDismissListener(this);
        mDialog.setOnShowListener(this);
        container.setBackgroundResource(R.drawable.match_dialog_bg);
    }

    @Override
    public void show() {
        super.show();
        shadow.setAlpha(0F);
        containerParent.setAlpha(0F);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        shadow.animate().alpha(0F).setDuration(ANIM_DURATION).start();
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        containerParent.animate().alpha(0F).translationY(screenHeight / 2).setDuration(ANIM_DURATION).start();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        shadow.animate().alpha(1F).setDuration(ANIM_DURATION).start();
        int screenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        containerParent.setTranslationY(screenHeight / 2);
        containerParent.animate().alpha(1F).translationY(0).setDuration(ANIM_DURATION).start();
    }
}
