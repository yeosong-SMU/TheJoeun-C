package com.example.networklogin

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var txtResult: TextView
    private lateinit var editId: EditText
    private lateinit var editPwd: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtResult = findViewById(R.id.txtResult)
        editId = findViewById(R.id.editId)
        editPwd = findViewById(R.id.editPwd)

        //로그인 버튼은 xml에서 onClick="login" 설정
    }

    fun login(view: android.view.View) {
        val userId = editId.text.toString()
        val userPwd = editPwd.text.toString()

        Thread {
            try {
                val url = URL("http://192.168.0.104:8080/jlogin/login.do")
                val conn = url.openConnection() as HttpURLConnection

                //요청 설정
                conn.requestMethod = "POST"
                conn.connectTimeout = 3000
                conn.doOutput = true
                conn.useCaches = false

                //파라미터 전송
                val params = "userid=$userId&passwd=$userPwd"
                conn.outputStream.write(params.toByteArray(Charsets.UTF_8))

                //응답 받기
                val reader = BufferedReader(InputStreamReader(conn.inputStream, "utf-8"))
                val response = reader.readText()
                reader.close()
                conn.disconnect()

                //JSON 파싱
                val json = JSONObject(response)
                val name = json.optString("name")

                //결과 출력
                val message = if(name.isNotBlank() && name != "null"){
                    "$name 님 환영합니다."
                } else {
                    "아이디 또는 비밀번호가 일치하지 않습니다."
                }

                runOnUiThread { txtResult.text = message }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread { txtResult.text = "서버 연결 실패 또는 오류 발생" }
            }
        }.start()
    }
}