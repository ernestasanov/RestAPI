package com.learning.restapi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ApiViewHolder(apiView: RelativeLayout) : RecyclerView.ViewHolder(apiView) {
    private val textRepoNameView: TextView = apiView.findViewById(R.id.text_repo_name)
    private val textRepoDescriptionView: TextView = apiView.findViewById(R.id.text_repo_description)
    private val textLanguageView: TextView = apiView.findViewById(R.id.text_language)
    private val textStarsView: TextView = apiView.findViewById(R.id.text_stars)
    fun bind(api: Api) {
        textRepoNameView.text = api.name
        textRepoDescriptionView.text = api.description
        textLanguageView.text = "Language: ${api.language}"
        textStarsView.text = "Stars: ${api.stargazersCount}"
    }

}

class ApiAdapter(var apiList: List<Api>) : RecyclerView.Adapter<ApiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val apiView = inflater.inflate(R.layout.api, parent, false) as RelativeLayout
        return ApiViewHolder(apiView)
    }

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        holder.bind(apiList[position])
    }

    override fun getItemCount(): Int {
        return apiList.size
    }

}