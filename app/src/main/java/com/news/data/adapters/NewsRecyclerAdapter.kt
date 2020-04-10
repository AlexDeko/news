package com.news.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.news.R
import com.news.data.dto.News
import com.news.data.dto.NewsType

const val VIEW_TYPE_NEWS = 1

fun viewTypeToPostType(viewType: Int) = when (viewType) {
    VIEW_TYPE_NEWS -> NewsType.News
    else -> TODO("unknown view type")
}

class NewsRecyclerAdapter(context: Context, private val list: MutableList<News>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewTypeToPostType(viewType)) {
            NewsType.News -> NewsViewHolder(
                this,
                LayoutInflater.from(parent.context).inflate(
                    R.layout.image_item_list,
                    parent,
                    false
                )
            )

        }

    override fun getItemCount() = list.size

    //override fun getItemId(position: Int) = list[position].

    //override fun getItemViewType(position: Int) = when (list[position].newsType) {
   //     NewsType.News -> VIEW_TYPE_NEWS
   // }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as BaseViewHolder) {
            bind(list[position])
        }
    }
}

