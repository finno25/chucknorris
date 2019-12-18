package com.rafinno.chucknorris.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rafinno.chucknorris.R

class CategoryAdapter(listener: CategoryAdapter.Listener?) : RecyclerView.Adapter<ViewHolder>() {

    private var categories: List<String>? = null
    private var listener: CategoryAdapter.Listener? = null

    init {
        this.listener = listener
    }

    fun setData(categories: List<String>?) {
        this.categories = categories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        categories?.get(position)?.let {
            (holder as CategoryHolder).bind(it)
            holder.itemView.setOnClickListener(View.OnClickListener { view ->
                listener?.let {
                    it.onItemClicked(view.tag as String)
                }
            })
        }
    }

    override fun getItemCount(): Int {
        categories?.let { return it.size }
        return 0
    }

    class CategoryHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(categoryName: String) {
            val categoryNameLabel = itemView.findViewById<TextView>(R.id.category_name)
            categoryNameLabel?.text = categoryName.toUpperCase()
            itemView.tag = categoryName
        }
    }

    interface Listener {
        fun onItemClicked(categoryName: String)
    }
}