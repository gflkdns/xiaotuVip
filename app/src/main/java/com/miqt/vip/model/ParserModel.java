package com.miqt.vip.model;

import com.blankj.utilcode.util.ToastUtils;
import com.miqt.vip.bean.ParserCfg;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by t54 on 2019/4/4.
 */

public class ParserModel {
    List<ParserCfg> cfgs;
    String url;

    public ParserModel() {
        cfgs = new ArrayList<>();

    }

    public List<ParserCfg> getCfgs() {
        return cfgs;
    }

    public void setTargetUrl(String url) {
        for (int i = 0; i < cfgs.size(); i++) {
            cfgs.get(i).setTargetUrl(url);
        }
    }
}
