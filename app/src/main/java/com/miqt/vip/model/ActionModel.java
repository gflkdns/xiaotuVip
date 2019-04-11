package com.miqt.vip.model;

import com.blankj.utilcode.util.ToastUtils;
import com.miqt.vip.bean.Action;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by t54 on 2019/4/4.
 */

public class ActionModel {
    List<Action> result;

    public ActionModel() {
        result = new ArrayList<>();
        result.add(new Action(
                "腾讯视频",
                "https://v.qq.com/",
                "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3774094549,311598158&fm=179&app=42&f=JPEG?w=56&h=56"));
        result.add(new Action(
                "爱奇艺视频",
                "https://www.iqiyi.com/",
                "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2596009146,1243342679&fm=179&app=42&f=PNG?w=56&h=56"));
        Action action = new Action(
                "芒果视频",
                "https://www.mgtv.com/",
                "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=199783531,2798712360&fm=179&app=42&f=JPEG?w=56&h=56");
        BmobQuery<Action> query = new BmobQuery<>();
        query
                .findObjects(new FindListener<Action>() {
                    @Override
                    public void done(List<Action> object, BmobException e) {
                        if (e == null) {
                            result.clear();
                            result.addAll(object);
                        } else {
                            ToastUtils.showShort("获取平台信息失败：" + e.getMessage());
                        }
                    }
                });
    }

    public List<Action> getActions() {
        return result;
    }
}
