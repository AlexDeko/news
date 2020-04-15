package com.news.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.news.R
import com.news.data.dto.News
import com.news.data.dto.Photos
import kotlinx.android.synthetic.main.image_item_list.view.*

class NewsRecyclerAdapter(val context: Context, val list: MutableList<News>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        NewsViewHolder(this,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_item_list, parent,
                false
            )
        )

    override fun getItemCount() = list.size

    //override fun getItemId(position: Int) = list[position].id

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as BaseViewHolder) {
            bind(list[position])

        }
    }
}

