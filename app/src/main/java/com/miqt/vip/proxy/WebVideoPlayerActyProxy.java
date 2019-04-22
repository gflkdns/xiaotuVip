package com.miqt.vip.proxy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import android.widget.FrameLayout;

import com.blankj.utilcode.util.ToastUtils;

import com.miqt.vip.R;
import com.miqt.vip.adapter.TAdapter;
import com.miqt.vip.adapter.houlder.ParserHoulder;
import com.miqt.vip.adapter.houlder.THolder;
import com.miqt.vip.bean.ParserCfg;
import com.miqt.wand.activity.ProxyActivity;
import com.miqt.wand.anno.AddToFixPatch;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by t54 on 2019/4/4.
 */
@AddToFixPatch
public class WebVideoPlayerActyProxy extends BaseProxy implements SwipeRefreshLayout.OnRefreshListener {

    private WebView webView;
    private String url;

    /**
     * 视频全屏参数
     */
    public static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private IX5WebChromeClient.CustomViewCallback customViewCallback;
    private RecyclerView lv_parsers;
    private TAdapter parserAdapter;
    private DrawerLayout dl_layout;
    private List<ParserCfg> data;
    private SwipeRefreshLayout srl_layout;

    public WebVideoPlayerActyProxy(ProxyActivity acty) {
        super(acty);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        url = mActy.getIntent().getStringExtra("url");//传进来视频链接
        mActy.setContentView($("R.layout.activity_web"));
        mActy.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        webView = (WebView) mActy.findViewById($("R.id.webview"));
        srl_layout = (SwipeRefreshLayout) mActy.findViewById($("R.id.srl_layout"));
        srl_layout.setOnRefreshListener(this);
        showProgressDialog("加载中...");
        //如果10秒没加载完，直接关闭dialog
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
            }
        }, 10000);
        lv_parsers = mActy.findViewById($("R.id.lv_parsers"));
        dl_layout = mActy.findViewById($("R.id.dl_layout"));
        dl_layout.setDrawerLockMode(mActy.getIntent().getBooleanExtra("showMenu", false) ?
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED);
        lv_parsers.setLayoutManager(new LinearLayoutManager(mActy));
        data = new ArrayList();
        parserAdapter = new TAdapter<>(data, mActy,$("R.layout.item_parser"),
                ParserHoulder.class);
        lv_parsers.setAdapter(parserAdapter);
        initWebView();
        getdata();
    }

    private void getdata() {
        BmobQuery<ParserCfg> query = new BmobQuery<>();
        query.order("-createdAt")
                .findObjects(new FindListener<ParserCfg>() {
                    @Override
                    public void done(List<ParserCfg> object, BmobException e) {
                        if (e == null) {
                            data.clear();
                            data.addAll(object);
                            parserAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.showShort("获取解析器失败：" + e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
        webView.onResume();
        webView.resumeTimers();
    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onPause() {
        webView.onPause();
    }

    /**
     * 展示网页界面
     **/
    public void initWebView() {
        WebChromeClient wvcc = new WebChromeClient();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        webSettings.setAppCacheEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(true);
        }
        // webSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        webView.setWebChromeClient(wvcc);
        webView.setWebViewClient(new MyWebClient());

        webView.setWebChromeClient(new MyWebChromeClient());
        webView.loadUrl(url);
    }

    public class MyWebChromeClient extends WebChromeClient {
        /*** 视频播放相关的方法 **/

        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(mActy);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            return frameLayout;
        }

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
            showCustomView(view, callback);
            mActy.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//播放时横屏幕，如果需要改变横竖屏，只需该参数就行了

        }

        @Override
        public void onHideCustomView() {
            hideCustomView();
            mActy.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//不播放时竖屏
        }
    }

    private void refMenu(final String url) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setTargetUrl(webView.getUrl());
        }
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            url = url.trim();
            if (!url.startsWith("http")) {
                url = url.replaceAll("([^\\s]+)(?=://)", "http");
                return true;
            }
            webView.loadUrl(url);
            showProgressDialog("加载中...");
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            dismissProgressDialog();
            refMenu(url);
        }
    }

    /**
     * 视频播放全屏
     **/

    private void showCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        mActy.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        mActy.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) mActy.getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(mActy);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /**
     * 隐藏视频全屏
     */
    private void hideCustomView() {
        mActy.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) mActy.getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        webView.loadUrl(webView.getUrl());
        refMenu(webView.getUrl());
        srl_layout.setRefreshing(false);
    }

    /**
     * 全屏容器界面
     */
    public static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(Color.BLACK);
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mActy.getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */
                if (customView != null) {
                    hideCustomView();
                } else if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    mActy.finish();
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroy() {
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

}