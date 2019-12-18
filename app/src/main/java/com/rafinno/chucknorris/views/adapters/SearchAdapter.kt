package com.rafinno.chucknorris.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafinno.chucknorris.R
import com.rafinno.chucknorris.commons.Utilities
import com.rafinno.chucknorris.networks.Jokes

class SearchAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var jokesList: List<Jokes>? = null

    fun setData(jokesList: List<Jokes>?) {
        this.jokesList = jokesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return JokesHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        jokesList?.get(position)?.let {
            (holder as JokesHolder).bind(it)
        }
    }

    override fun getItemCount(): Int {
        jokesList?.let { return it.size }
        return 0
    }

    class JokesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(jokes: Jokes) {
            val jokesValueText = itemView.findViewById<TextView>(R.id.jokesValue)
            val jokesUpdatedText = itemView.findViewById<TextView>(R.id.jokesUpdated)
            val jokesIcon = itemView.findViewById<ImageView>(R.id.jokesIcon)

            jokes?.let {
                jokesValueText.text = it.value
                Glide.with(itemView.context)
                    .load(it.icon_url)
                    .into(jokesIcon)
                jokesUpdatedText.text = "updated at ${Utilities.convertDatetime(it.updated_at, "yyyy-MM-dd hh:mm:ss.SSS", "dd MMM yyyy hh:mm a")}"
            }
        }
    }
}