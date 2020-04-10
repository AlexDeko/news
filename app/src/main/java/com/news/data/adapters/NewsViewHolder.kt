package com.news.data.adapters

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.news.R
import com.news.data.dto.News
import kotlinx.android.synthetic.main.image_item_list.view.*

class NewsViewHolder(adapter: NewsRecyclerAdapter, view: View) : BaseViewHolder(adapter, view) {
    init {
        with(itemView) {
            imageView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                  //  Intent intent = new Intent

                }
            }
        }
    }

    override fun bind(news: News) {
        with(itemView) {


        }
    }
}