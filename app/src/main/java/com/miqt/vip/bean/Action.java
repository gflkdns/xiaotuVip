package com.miqt.vip.bean;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by t54 on 2019/4/3.
 */

public class Action extends BmobObject {
    String name;
    String url;
    String image;

    public Action(String name, String url, String image) {
        this.name = name;
        this.url = url;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
