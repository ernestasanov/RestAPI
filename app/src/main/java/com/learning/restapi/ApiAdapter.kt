package com.learning.restapi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ApiViewHolder(apiView: ConstraintLayout) : RecyclerView.ViewHolder(apiView) {
    private val apiNameView: TextView = apiView.findViewById(R.id.apiName)
    private val apiLinkView: TextView = apiView.findViewById(R.id.apiLink)
    fun bind(api: Api) {
        apiNameView.text = api.API
        apiLinkView.text = api.Link
    }

}

class ApiAdapter(var apiList: List<Api>) : RecyclerView.Adapter<ApiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val apiView = inflater.inflate(R.layout.api, parent, false) as ConstraintLayout
        return ApiViewHolder(apiView)
    }

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        holder.bind(apiList[position])
    }

    override fun getItemCount(): Int {
        return apiList.size
    }

}