package com.news.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.R
import com.news.data.dto.News
import com.news.ui.news.NewsFragment
import com.news.ui.popular.PopularFragment
import kotlinx.android.synthetic.main.image_item_list.view.*

class NewsRecyclerAdapter(val list: MutableList<News>) :
    RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecyclerAdapter.NewsViewHolder =
        NewsViewHolder(
            this,
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.image_item_list, parent,
                    false
                )
        )

    inner class NewsViewHolder(private val adapter: NewsRecyclerAdapter, view: View) :
        RecyclerView.ViewHolder(view) {
        init {
            itemView.imageView.setOnClickListener {
                if (view.context == NewsFragment().context)
                    NewsFragment().navigate(
                        adapter.list[adapterPosition].image?.name!!,
                        adapter.list[adapterPosition].name!!,
                        adapter.list[adapterPosition].description!!
                    )
                else PopularFragment().navigate(
                    adapter.list[adapterPosition].image?.name!!,
                    adapter.list[adapterPosition].name!!,
                    adapter.list[adapterPosition].description!!
                )
            }

        }

        fun bind(news: News) {
            with(itemView) {
                Glide.with(context)
                    .load("https://gallery.dev.webant.ru/media/${adapter.list[adapterPosition].image?.name.toString()}")
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView)
            }
        }
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = list[position].id.toLong()

    override fun onBindViewHolder(holder: NewsRecyclerAdapter.NewsViewHolder , position: Int) {
        holder.bind(list[position])

    }
}

