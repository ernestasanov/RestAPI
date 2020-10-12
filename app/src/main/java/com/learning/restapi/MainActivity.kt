package com.learning.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    private var personListRecyclerView: RecyclerView? = null
    private var responseTextView: TextView? = null
    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        responseTextView = findViewById(R.id.responseView)
        webView = findViewById(R.id.webView)
        val personListView : RecyclerView = findViewById(R.id.personList)
        personListView.layoutManager = LinearLayoutManager(this)

        val queue = Volley.newRequestQueue(this)
        val personRequest = StringRequest(
            Request.Method.GET, "http://numbersapi.com/76/math",
            { response: String ->
                responseTextView?.text = response
            },
            { error ->
                responseTextView?.text = error.localizedMessage
            }
        )
        queue.add(personRequest)

        var personAdapter = PersonAdapter(emptyList())
        personListView.adapter = personAdapter
        personAdapter.notifyDataSetChanged()
        personListRecyclerView = personListView

        webView?.loadUrl("http://numbersapi.com/76/math")
        webView?.settings?.javaScriptEnabled = true
    }
}