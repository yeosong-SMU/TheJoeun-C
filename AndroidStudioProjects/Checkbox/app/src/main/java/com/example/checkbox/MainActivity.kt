package com.example.checkbox

import android.graphics.Color
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val main = findViewById<ConstraintLayout>(R.id.main);
        val check = findViewById<CheckBox>(R.id.check)
        check.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                check.text = "체크된 상태"
            } else {
                check.text = "체크되지 않은 상태"
            }
        }

        val ckRed = findViewById<CheckBox>(R.id.ckRed);
        ckRed.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                main.setBackgroundColor(Color.RED);
            } else{
                main.setBackgroundColor(Color.WHITE);
            }
        }

        val ckGreen = findViewById<CheckBox>(R.id.ckGreen);
        ckGreen.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                main.setBackgroundColor(Color.GREEN);
            } else{
                main.setBackgroundColor(Color.WHITE);
            }
        }

        val ckBlue = findViewById<CheckBox>(R.id.ckBlue);
        ckBlue.setOnCheckedChangeListener{_, isChecked ->
            if(isChecked){
                main.setBackgroundColor(Color.BLUE);
            } else{
                main.setBackgroundColor(Color.WHITE);
            }
        }
    }
}