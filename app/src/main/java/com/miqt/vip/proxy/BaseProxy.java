package com.miqt.vip.proxy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.miqt.wand.activity.ActivityProxy;
import com.miqt.wand.activity.ProxyActivity;
import com.miqt.wand.anno.AddToFixPatch;

/**
 * Created by t54 on 2019/4/4.
 */
@AddToFixPatch
public class BaseProxy extends ActivityProxy {
    private ProgressDialog dialog;

    public BaseProxy(ProxyActivity acty) {
        super(acty);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dialog = new ProgressDialog(mActy);
    }

    public void showProgressDialog(String meg) {
        if (dialog.isShowing()) {
            return;
        } else {
            dialog.setMessage(meg);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
