package com.example.weblogin

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var editId: EditText
    private lateinit var editPwd: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtResult: TextView
    private lateinit var webview1: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //뷰 연결
        editId = findViewById(R.id.editId)
        editPwd = findViewById(R.id.editPwd)
        btnLogin = findViewById(R.id.btnLogin)
        txtResult = findViewById(R.id.txtResult)
        webview1 = findViewById(R.id.webview1)

        //웹뷰 설정
        webview1.settings.javaScriptEnabled = true    //자바스크립트 활성화
        webview1.webViewClient = WebViewClient()   //새 창 안 뜨게
        webview1.addJavascriptInterface(AndroidBridge(), "android")
                                        //연결클래스          이름
        webview1.loadUrl("http://192.168.0.104:8080/jweb/member/login.jsp")

        //로그인 버튼 클릭 시 자바스크립트 함수 호출
        btnLogin.setOnClickListener {
            val id = editId.text.toString()
            val pwd = editPwd.text.toString()
            webview1.loadUrl("javascript:login('$id', '$pwd')")
        }
    }

    //자바스크립트에서 Android로 메시지 보낼 때 사용
    inner class AndroidBridge {
        @JavascriptInterface
        fun setMessage(arg: String) {
            runOnUiThread {
                txtResult.text = arg.trim()
            }
        }
    }
}