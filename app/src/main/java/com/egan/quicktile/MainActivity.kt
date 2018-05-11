package com.egan.quicktile

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        text_view.setOnClickListener { startActivity(Intent(this, CaptureActivity::class.java)) }

        if (intent != null) {
            val url = intent.getStringExtra("url")
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    Log.d("YSK", "加载的 URl >>> " + view?.url)
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }

            if (url != null && url.isNotEmpty()) {
                webView.loadUrl(url)
            }
        }
    }
}