package com.miqt.vip.adapter.houlder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miqt.wand.anno.BindProxy;

/**
 * Created by t54 on 2019/3/15.
 */

public abstract class THolder<T> extends RecyclerView.ViewHolder {
    public THolder(View itemView) {
        super(itemView);
    }

    abstract public void setData(T itemData, int p);
}
