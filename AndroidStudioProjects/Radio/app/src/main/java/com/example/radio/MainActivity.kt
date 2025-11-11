package com.example.radio

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {
    private var layout1: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val radio1 = findViewById<RadioGroup>(R.id.radio1)
        layout1 = findViewById(R.id.layout1)
        radio1.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        Log.i("test", "checkedId:$checkedId")
        when(checkedId){
            R.id.rdoRed -> layout1?.setBackgroundColor(Color.RED)
            R.id.rdoGreen -> layout1?.setBackgroundColor(Color.GREEN)
            R.id.rdoBlue -> layout1?.setBackgroundColor(Color.BLUE)
        }
    }
}