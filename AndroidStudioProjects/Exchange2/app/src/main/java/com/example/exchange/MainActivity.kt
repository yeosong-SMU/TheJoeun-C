package com.example.exchange

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dollar = findViewById<EditText>(R.id.dollar)   //val 불변 (상수)
        val button = findViewById<Button>(R.id.button)
        val txtResult = findViewById<TextView>(R.id.txtResult)
        button.setOnClickListener {    //버튼을 클릭하면
            if (dollar.text.toString() == "") {     //equals 안 쓰고 == 써도 ㄱㅊ
                Toast.makeText(this, "숫자를 입력하세요", Toast.LENGTH_LONG).show()   //메시지박스 출력
            } else {
                var intDollar = dollar.text.toString().toInt()    //var 가변
                var money = intDollar * 1500
                txtResult.text = "${intDollar}달러=${money}원입니다."
            }
        }
    }
}