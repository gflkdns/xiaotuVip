package com.miqt.vip.adapter.houlder;

import android.view.View;
import android.widget.TextView;

import com.miqt.vip.R;
import com.miqt.vip.WebVideoPlayerActy;
import com.miqt.vip.bean.ParserCfg;


/**
 * Created by t54 on 2019/4/4.
 */

public class ParserHoulder extends THolder<ParserCfg> {
    private final TextView tv_name;

    public ParserHoulder(View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name);
    }

    @Override
    public void setData(final ParserCfg itemData, int p) {
        tv_name.setText(itemData.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebVideoPlayerActy.Companion.start(itemData.getUrl(), true);
            }
        });
    }
}
