package com.news.data.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.news.data.dto.News

abstract class BaseViewHolder(val adapter: NewsRecyclerAdapter, view: View) :
    RecyclerView.ViewHolder(view) {
    abstract fun bind(news: News)
}