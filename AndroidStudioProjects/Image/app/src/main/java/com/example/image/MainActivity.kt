package com.example.image

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Random
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var imgYut = intArrayOf(R.drawable.yut_0, R.drawable.yut_1)
    var strYut = arrayOf("윷", "걸", "개", "도", "모")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener{
            var rand = Random()
            var n1 = rand.nextInt(2)
            var n2 = rand.nextInt(2)
            var n3 = rand.nextInt(2)
            var n4 = rand.nextInt(2)
            var sum = n1 + n2+ n3+ n4
            val img1 = findViewById<ImageView>(R.id.img1)
            val img2 = findViewById<ImageView>(R.id.img2)
            val img3 = findViewById<ImageView>(R.id.img3)
            val img4 = findViewById<ImageView>(R.id.img4)
            img1.setImageResource(imgYut[n1])
            img2.setImageResource(imgYut[n2])
            img3.setImageResource(imgYut[n3])
            img4.setImageResource(imgYut[n4])
            val txtResult = findViewById<TextView>(R.id.txtResult)
            txtResult.text = strYut[sum]
        }
    }
}