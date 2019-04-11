package com.miqt.vip;


import com.miqt.vip.proxy.ActionProxy;
import com.miqt.wand.activity.ProxyActivity;
import com.miqt.wand.anno.BindProxy;

@BindProxy(clazz = ActionProxy.class)
public class MainActivity extends ProxyActivity {
}
