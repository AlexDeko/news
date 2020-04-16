package com.news.data.adapters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.R
import com.news.data.dto.News
import com.news.ui.popular.PopularFragment
import kotlinx.android.synthetic.main.image_item_list.view.*

class NewsViewHolder(adapter: NewsRecyclerAdapter, view: View) : BaseViewHolder(adapter, view) {
    init {
        with(itemView) {
            Glide.with(adapter.context)
                .asBitmap()
                .load("http://gallery.dev.webant.ru/media/${adapter.list[adapterPosition].image?.name}")
                .into(imageView)

            imageView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (adapter.context == PopularFragment::getContext)
                        Navigation.createNavigateOnClickListener(
                            R.id.action_popularFragment_to_contentFragment,
                            Bundle().apply {
                                putString("url", adapter.list[adapterPosition].id.toString())
                                putString("file", adapter.list[adapterPosition].image?.name)
                                putString("name", adapter.list[adapterPosition].name)
                                putString("description", adapter.list[adapterPosition].description)
                            })
                    else Navigation.createNavigateOnClickListener(
                        R.id.action_newsFragment_to_contentFragment,
                        Bundle().apply {
                            putString("url", adapter.list[adapterPosition].id.toString())
                            putString("file", adapter.list[adapterPosition].image?.name)
                            putString("name", adapter.list[adapterPosition].name)
                            putString("description", adapter.list[adapterPosition].description)
                        })
                }
            }
        }
    }



    override fun bind(news: News) {
        with(itemView) {



        }
    }
}
