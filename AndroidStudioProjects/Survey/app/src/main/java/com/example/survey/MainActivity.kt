package com.example.survey

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    //lateinit 나중에 초기화
    lateinit var question: TextView
    lateinit var radio1: RadioButton
    lateinit var radio2: RadioButton
    lateinit var radio3: RadioButton
    lateinit var radio4: RadioButton
    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        question = findViewById(R.id.question)
        radio1 = findViewById(R.id.radio1)
        radio2 = findViewById(R.id.radio2)
        radio3 = findViewById(R.id.radio3)
        radio4 = findViewById(R.id.radio4)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener { sendAnswer() }
        loadQuestion()
    }

    fun loadQuestion() {
        Thread {
            val url = URL("http://192.168.0.104:8080/Survey/question.do?survey_idx=1")
            val conn = url.openConnection() as HttpURLConnection
            val text = conn.inputStream.bufferedReader().readText()
            val obj = JSONObject(text)

            val q = obj.getString("question")
            val a1 = obj.getString("ans1")
            val a2 = obj.getString("ans2")
            val a3 = obj.getString("ans3")
            val a4 = obj.getString("ans4")

            runOnUiThread {
                question.text = q
                radio1.text = a1
                radio2.text = a2
                radio3.text = a3
                radio4.text = a4
            }
            conn.disconnect()
        }.start()
    }

    fun sendAnswer() {
        val num = when {
            radio1.isChecked -> "1"
            radio2.isChecked -> "2"
            radio3.isChecked -> "3"
            radio4.isChecked -> "4"
            else -> ""
        }

        Thread {
            val url = URL("http://192.168.0.104:8080/Survey/insert.do?survey_idx=1&num=$num")
            val conn = url.openConnection() as HttpURLConnection
            conn.responseCode
            conn.disconnect()

            runOnUiThread {
                startActivity(Intent(this, SummaryActivity::class.java))
            }
        }.start()
    }
}