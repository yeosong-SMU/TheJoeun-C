package com.example.web

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webview1: WebView
    private lateinit var editUrl: EditText
    private lateinit var btnOk: Button
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button
    private lateinit var btnCancel: Button
    private lateinit var pb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview1 = findViewById(R.id.webview1)
        editUrl = findViewById(R.id.editUrl)
        btnOk = findViewById(R.id.btnOk)
        btnBack = findViewById(R.id.btnBack)
        btnNext = findViewById(R.id.btnNext)
        btnCancel = findViewById(R.id.btnCancel)
        pb = findViewById(R.id.pb)

        val settings: WebSettings = webview1.settings
        settings.javaScriptEnabled = true
        settings.builtInZoomControls = true

        webview1.webViewClient = WebViewClient()
        webview1.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                pb.progress = newProgress
                pb.visibility = if (newProgress == 100) View.GONE else View.VISIBLE
            }
        }

        webview1.loadUrl("http://google.com")

        btnOk.setOnClickListener {
            var url = editUrl.text.toString()
            if(!url.startsWith("http://")) {
                url = "http://$url"
            }
            webview1.loadUrl(url)
        }

        btnCancel.setOnClickListener {
            editUrl.setText("")
        }

        btnBack.setOnClickListener {
            if(webview1.canGoBack()) {
                webview1.goBack()
            }
        }

        btnNext.setOnClickListener {
            if(webview1.canGoForward()) {
                webview1.goForward()
            }
        }
    }
}