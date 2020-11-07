package com.learning.restapi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private var apiAdapter: ApiAdapter? = null
    private var apiListRecyclerView: RecyclerView? = null
    private var responseTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        responseTextView = findViewById(R.id.responseView)
        val apiListView : RecyclerView = findViewById(R.id.apiList)
        apiListView.layoutManager = LinearLayoutManager(this)

        /*val observable = Observable.range(1, 1000000) // observable
        disposable = observable
            .toFlowable(BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value -> // observer
                    Log.d("RxJava", value.toString())
                }, { error ->
                    Log.d("RxJava", "error", error)
                }, {
                    Log.d("RxJava", "finish")
                }
            )*/

        val disposeButton = findViewById<Button>(R.id.disposeButton)
        disposeButton.setOnClickListener {
            disposable?.dispose()
        }
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(ApiService::class.java)
        disposable = service.getStarredRepos("ernestasanov")
            .flatMapObservable { Observable.fromIterable(it) }
            .flatMapSingle { service.getRepo(it) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                apiAdapter?.apiList = it
                apiAdapter?.notifyDataSetChanged()
            }, {
                responseTextView?.text = it.localizedMessage
            })

        val adapter = ApiAdapter(emptyList())
        apiListView.adapter = adapter
        apiAdapter = adapter
        apiListRecyclerView = apiListView
    }
}