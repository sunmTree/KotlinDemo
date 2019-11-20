package com.kotlin.module.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.kotlin.demo.R;


/**
 * Created by chao on 2017/10/16.
 */

public abstract class BaseDialog {
    public Context mContext;
    AlertDialog mDialog;

    View shadow;
    FrameLayout container;
    LinearLayout containerParent;


    public BaseDialog(Context context) {
        mContext = context;
        mDialog = new AlertDialog.Builder(context).create();
        mDialog.setCanceledOnTouchOutside(canceledOnTouchOutside());
        View parentView = LayoutInflater.from(context).inflate(R.layout.base_dialog_layout, null, false);
        shadow = parentView.findViewById(R.id.shadow);
        parentView.findViewById(R.id.shadow).setOnClickListener(v -> {
            if (canceledOnTouchOutside()) {
                dismiss();
            }
        });
        containerParent = parentView.findViewById(R.id.container_parent);
        container = parentView.findViewById(R.id.container);
        View contentView = setContentView(container);
        container.addView(contentView);
        mDialog.setView(parentView);
    }

    protected abstract View setContentView(ViewGroup parent);

    protected boolean canceledOnTouchOutside() {
        return true;
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
            fixSize();
        }
    }

    private void fixSize() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//        mDialog.getWindow().setWindowAnimations(R.style.bottomDialogAnim);
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (IllegalStateException | IllegalArgumentException ignore) {
                //dismissInternal(true);
                //Can not perform this action after onSaveInstanceState
                //Activity is finishing
            }
        }
    }
}
