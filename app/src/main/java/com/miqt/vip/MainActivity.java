package com.miqt.vip;


import com.miqt.vip.proxy.ActionProxy;
import com.miqt.wand.activity.ProxyActivity;
import com.miqt.wand.anno.BindProxy;
import com.miqt.wand.anno.ParentalEntrustmentLevel;

@BindProxy(clazz = ActionProxy.class,level = ParentalEntrustmentLevel.NEVER)
public class MainActivity extends ProxyActivity {
}
