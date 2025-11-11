package com.example.survey

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SummaryActivity : AppCompatActivity() {
    lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        result = findViewById(R.id.result)
        loadSummary()
    }

    fun loadSummary() {
        Thread {
            val url = URL("http://192.168.0.104:8080/Survey/summary_json.do?survey_idx=1")
            val conn = url.openConnection() as HttpURLConnection
            val text = conn.inputStream.bufferedReader().readText()
            conn.disconnect()

            val json = JSONObject(text)
            val arr = json.getJSONArray("sendData")

            var output = "설문조사 통계\n\n"
            output += String.format("%10s%10s%10s\n", "번호", "집계", "투표율")

            for(i in 0 until arr.length()) {
                val row = arr.getJSONObject(i)
                val num = row.getInt("num")
                val sum = row.getInt("sum_num")
                val rate = row.getDouble("rate")
                output += String.format("%10d%15d%14.1f%%\n", num, sum, rate)
            }

            runOnUiThread {
                result.text = output
            }
        }.start()
    }
}