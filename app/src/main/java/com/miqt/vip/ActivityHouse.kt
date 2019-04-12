package com.miqt.vip

import android.content.Intent
import com.blankj.utilcode.util.ActivityUtils
import com.miqt.vip.proxy.ActionProxy
import com.miqt.vip.proxy.WebVideoPlayerActyProxy
import com.miqt.wand.activity.ProxyActivity
import com.miqt.wand.anno.BindProxy
import com.miqt.wand.anno.ParentalEntrustmentLevel

/**
 * 由于使用了wandfix activity代理，activity只需要声明一下就可以了，
 * 所以优化下项目结构，把所有的activity都放在这里声明即可
 */
@BindProxy(clazz = ActionProxy::class, level = ParentalEntrustmentLevel.NEVER)
class MainActivity : ProxyActivity()

@BindProxy(clazz = WebVideoPlayerActyProxy::class, level = ParentalEntrustmentLevel.NEVER)
class WebVideoPlayerActy : ProxyActivity() {
    companion object {
        fun start(url: String, showMenu: Boolean) {
            val intent = Intent(ActivityUtils.getTopActivity(),
                    WebVideoPlayerActy::class.java)
            intent.putExtra("url", url)
            intent.putExtra("showMenu", showMenu)
            ActivityUtils.startActivity(intent)
        }
    }
}
