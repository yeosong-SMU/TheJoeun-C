package com.example.xml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xml.R
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val bookList = ArrayList<BookDTO>()
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv)
        adapter = BookAdapter(bookList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        fetchJsonData()
        //fetchXmlData()
    }

    private fun fetchJsonData() {
        Thread {
            try {
                val url = URL("http://192.168.0.104:8080/book/json.do")
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 5000
                conn.useCaches = false

                if(conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val input = conn.inputStream.bufferedReader().readText()
                    val jsonObj = JSONObject(input)
                    val jArray = jsonObj.getJSONArray("sendData")

                    bookList.clear()
                    for(i in 0 until jArray.length()) {
                        val obj = jArray.getJSONObject(i)
                        val book = BookDTO(
                            book_name = obj.getString("book_name"),
                            press = obj.getString("press")
                        )
                        bookList.add(book)
                    }
                    runOnUiThread { adapter.notifyDataSetChanged() }
                }
                conn.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun fetchXmlData() {
        Thread {
            try {
                val url = URL("http://192.168.0.104:8080/book/xml.do")
                val inputStream = url.openStream()

                val factory = XmlPullParserFactory.newInstance()
                val parser = factory.newPullParser()
                parser.setInput(inputStream, "utf-8")

                var eventType = parser.eventType
                var currentBook: BookDTO? = null
                var tagName: String

                bookList.clear()   //기존 목록 비우기

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when(eventType) {
                        XmlPullParser.START_TAG -> {
                            tagName = parser.name
                            if(tagName == "book") {
                                currentBook = BookDTO()
                            } else if(tagName == "book_name") {
                                currentBook?.book_name = parser.nextText()
                            } else if(tagName == "press") {
                                currentBook?.press = parser.nextText()
                            }
                        }

                        XmlPullParser.END_TAG -> {
                            if(parser.name == "book" && currentBook != null) {
                                bookList.add(currentBook)
                            }
                        }
                    }
                    eventType = parser.next()
                }

                runOnUiThread { adapter.notifyDataSetChanged() }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}