package com.miqt.vip.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by t54 on 2019/4/4.
 */

public class HotFix extends BmobObject {
    BmobFile dexFile;
    BmobFile apkFile;
    int versionCode;
    int channel;
    boolean debug;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public BmobFile getDexFile() {
        return dexFile;
    }

    public void setDexFile(BmobFile dexFile) {
        this.dexFile = dexFile;
    }

    public BmobFile getApkFile() {
        return apkFile;
    }

    public void setApkFile(BmobFile apkFile) {
        this.apkFile = apkFile;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
