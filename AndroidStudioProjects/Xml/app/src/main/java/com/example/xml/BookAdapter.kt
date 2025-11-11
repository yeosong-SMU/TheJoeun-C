package com.example.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private val books: List<BookDTO>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtBookName: TextView = view.findViewById(R.id.book_name)
            val txtPress: TextView = view.findViewById(R.id.press)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val item = LayoutInflater.from(parent.context)
                .inflate(R.layout.row, parent, false)
            return ViewHolder(item)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val book = books[position]
            holder.txtBookName.text = book.book_name
            holder.txtPress.text = book.press
        }

        override fun getItemCount(): Int = books.size
    }