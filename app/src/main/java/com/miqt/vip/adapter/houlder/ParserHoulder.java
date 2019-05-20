package com.miqt.vip.adapter.houlder;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.miqt.vip.R;
import com.miqt.vip.WebVideoPlayerActy;
import com.miqt.vip.bean.ParserCfg;
import com.miqt.wand.anno.AddToFixPatch;
import com.miqt.wand.utils.R2;


/**
 * Created by t54 on 2019/4/4.
 */
@AddToFixPatch
public class ParserHoulder extends THolder<ParserCfg> {
    private final TextView tv_name;

    public ParserHoulder(View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R2.id_("R.id.tv_name"));
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
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtils.showLong("彩蛋");
                return false;
            }
        });
    }
}
