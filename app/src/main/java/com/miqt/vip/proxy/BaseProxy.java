package com.miqt.vip.proxy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miqt.vip.R;
import com.miqt.wand.activity.ActivityProxy;
import com.miqt.wand.activity.ProxyActivity;
import com.miqt.wand.anno.AddToFixPatch;

/**
 * Created by t54 on 2019/4/4.
 */
@AddToFixPatch
public class BaseProxy extends ActivityProxy {
    private ProgressDialog dialog;
    private View title_bar;
    private ViewHolder holder;

    public View getTitle_bar() {
        return title_bar;
    }

    public ViewHolder getTitleHolder() {
        return holder;
    }

    public BaseProxy(ProxyActivity acty) {
        super(acty);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        dialog = new ProgressDialog(mActy);
        trySerTitleBar("");
    }

    public void trySerTitleBar(String title) {
        try {
            title_bar = mActy.findViewById(R.id.title_bar);
            holder = new ViewHolder(title_bar);
            holder.tv_title.setText(title);
            holder.ll_lift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActy.finish();
                }
            });
        } catch (Exception e) {

        }
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

    static class ViewHolder {
        View view;
        ImageView iv_lift;
        LinearLayout ll_lift;
        TextView tv_title;
        ImageView iv_right;
        LinearLayout ll_right;

        ViewHolder(View view) {
            this.view = view;
            this.iv_lift = (ImageView) view.findViewById(R.id.iv_lift);
            this.ll_lift = (LinearLayout) view.findViewById(R.id.ll_lift);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.iv_right = (ImageView) view.findViewById(R.id.iv_right);
            this.ll_right = (LinearLayout) view.findViewById(R.id.ll_right);
        }
    }
}
