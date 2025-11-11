package com.example.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var txtResult: TextView
    private val items = arrayOf("사과", "포도", "레몬", "수박", "바나나", "체리")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtResult = findViewById(R.id.txtResult)
        findViewById<RecyclerView>(R.id.rv).apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = MyAdapter(items)
        }
    }

    inner class MyAdapter(private val data: Array<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount() = data.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            private val textView: TextView = view.findViewById(R.id.text1)

            init {
                view.setOnClickListener{
                    val pos = layoutPosition
                    Toast.makeText(view.context, "position: $pos text: ${textView.text}", Toast.LENGTH_SHORT).show()
                    txtResult.text = items[pos]
                }
            }

            fun bind(text: String) {
                textView.text = text
            }
        }
    }
}