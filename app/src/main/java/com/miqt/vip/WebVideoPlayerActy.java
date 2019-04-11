package com.miqt.vip;

import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.miqt.vip.proxy.WebVideoPlayerActyProxy;
import com.miqt.wand.activity.ProxyActivity;
import com.miqt.wand.anno.BindProxy;

/**
 * Created by t54 on 2019/4/11.
 */
@BindProxy(clazz = WebVideoPlayerActyProxy.class)
public class WebVideoPlayerActy extends ProxyActivity {
    public static void start(String url, boolean showMenu) {
        Intent intent = new Intent(ActivityUtils.getTopActivity(),
                WebVideoPlayerActy.class);
        intent.putExtra("url", url);
        intent.putExtra("showMenu", showMenu);
        ActivityUtils.startActivity(intent);
    }
}
