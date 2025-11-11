package com.example.bmi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editName = findViewById<EditText>(R.id.editName)
        val editAge = findViewById<EditText>(R.id.editAge)
        val editHeight = findViewById<EditText>(R.id.editHeight)
        val editWeight = findViewById<EditText>(R.id.editWeight)
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener {
            var kg = editWeight.text.toString().toDouble()
            var height = editHeight.text.toString().toDouble() / 100
            var bmi = kg / (height * height);
            var result = ""
            if(bmi < 18.5) {
                result = "저체중"
            } else if(bmi >= 18.5 && bmi < 23) {
                result = "정상"
            } else if(bmi >= 23 && bmi < 25){
                result = "과체중"
            } else if(bmi >= 24 && bmi < 30) {
                result = "비만"
            } else if (bmi >= 30){
                result = "고도비만"
            }
            result = "${editName.text.toString()}님의 체중은 ${result}입니다."
            var intent = Intent(this, BmiResult::class.java)
            var dto = BmiDTO(
                editName.text.toString(),
                editAge.text.toString().toInt(),
                editHeight.text.toString().toDouble(),
                editWeight.text.toString().toDouble(),
                bmi.toString().toDouble(), result
            )
            intent.putExtra("dto", dto)
            startActivity(intent)
        }
    }
}