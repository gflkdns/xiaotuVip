package com.miqt.vip.adapter.houlder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miqt.vip.R;
import com.miqt.vip.WebVideoPlayerActy;
import com.miqt.vip.bean.Action;


/**
 * Created by t54 on 2019/4/3.
 */

public class ActionHolder extends THolder<Action> {
    ImageView iv_image;
    TextView tv_name;

    public ActionHolder(View itemView) {
        super(itemView);
        this.iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
    }

    @Override
    public void setData(final Action itemData, int p) {
        Glide.with(itemView).asBitmap().load(itemData.getImage()).into(iv_image);
        tv_name.setText(itemData.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebVideoPlayerActy.Companion.start(itemData.getUrl(),true);
            }
        });
    }

}
