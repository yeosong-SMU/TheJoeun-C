package com.example.bmi

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class BmiResult : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_result)

        val txtResult = findViewById<TextView>(R.id.txtResult)
        val dto = intent.getParcelableExtra("dto", BmiDTO::class.java)

        txtResult.text = "이름: ${dto!!.name}\n나이: ${dto.age}\n신장: ${dto.height}\n체중: ${dto.weight}\nbmi: ${dto.bmi}\n${dto.result}"
    }
}