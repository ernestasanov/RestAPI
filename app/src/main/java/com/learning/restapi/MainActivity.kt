package com.learning.restapi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private var apiAdapter: ApiAdapter? = null
    private var apiListRecyclerView: RecyclerView? = null
    private var responseTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        responseTextView = findViewById(R.id.responseView)
        val apiListView : RecyclerView = findViewById(R.id.apiList)
        apiListView.layoutManager = LinearLayoutManager(this)

        val queue = Volley.newRequestQueue(this)
        val plainRequest = StringRequest(
            Request.Method.GET, "https://api.publicapis.org/entries?category=animals",
            { response: String ->
                responseTextView?.text = response
            },
            { error ->
                responseTextView?.text = error.localizedMessage
            }
        )
        //queue.add(plainRequest)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "https://api.publicapis.org/entries?category=animals",
            null, { jsonResponse: JSONObject ->
                val count = jsonResponse.getInt("count")
                val entries = jsonResponse.getJSONArray("entries")
                val apiList: MutableList<Api> = mutableListOf()
                for (i in 0 until count) {
                    val apiEntry = entries.getJSONObject(i)
                    val API = apiEntry.getString("API")
                    val Description = apiEntry.getString("Description")
                    val Auth = apiEntry.getString("Auth")
                    val HTTPS = apiEntry.getBoolean("HTTPS")
                    val Cors = apiEntry.getString("Cors")
                    val Link = apiEntry.getString("Link")
                    val Category = apiEntry.getString("Category")
                    val api = Api(API, Description, Auth, HTTPS, Cors, Link, Category)
                    apiList.add(api)
                }
                apiAdapter?.apiList = apiList
                apiAdapter?.notifyDataSetChanged()
            }, { err ->

            }
        )
        //queue.add(jsonObjectRequest)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.publicapis.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)
        val request : Call<ApiEntries> = service.listApis("animals")
        request.enqueue(object : Callback<ApiEntries> {
            override fun onResponse(call: Call<ApiEntries>, response: Response<ApiEntries>) {
                val entries = response.body()?.entries
                if (entries != null) {
                    apiAdapter?.apiList = entries
                }
                apiAdapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<ApiEntries>, t: Throwable) {
                responseTextView?.text = t.localizedMessage
            }
        })

        val adapter = ApiAdapter(emptyList())
        apiListView.adapter = adapter
        apiAdapter = adapter
        apiListRecyclerView = apiListView
    }
}