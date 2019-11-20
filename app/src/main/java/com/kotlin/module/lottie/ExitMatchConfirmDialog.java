package com.kotlin.module.lottie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotlin.demo.R;
import com.kotlin.module.dialog.BaseDialog;

public class ExitMatchConfirmDialog extends BaseDialog {

    public ExitMatchConfirmDialog(Context context, View.OnClickListener listener) {
        super(context);
    }

    @Override
    protected View setContentView(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.match_exit_dialog, parent, false);
    }
}
