package com.learning.restapi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonViewHolder(private val personView: TextView) : RecyclerView.ViewHolder(personView) {
    fun bind(person: Person) {
        personView.text = "${person.name} ${person.surname}, ${person.gender}, from ${person.region}"
    }

}

class PersonAdapter(var personList: List<Person>) : RecyclerView.Adapter<PersonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val textView = inflater.inflate(R.layout.person, parent, false) as TextView
        return PersonViewHolder(textView)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(personList[position])
    }

    override fun getItemCount(): Int {
        return personList.size
    }

}