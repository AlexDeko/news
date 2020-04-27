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

class NewsRecyclerAdapter(val list: MutableList<News>, private val onItemClick: (News) -> Unit) :
    RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsRecyclerAdapter.NewsViewHolder =
        NewsViewHolder(
            this,
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.image_item_list, parent,
                    false
                ),
            onItemClick = onItemClick
        )

    class NewsViewHolder(
        private val adapter: NewsRecyclerAdapter,
        view: View,
        onItemClick: (News) -> Unit
    ) :
        RecyclerView.ViewHolder(view) {
        init {
            itemView.imageView.setOnClickListener {
                onItemClick(adapter.list[adapterPosition])
            }
        }

        fun bind() {
            with(itemView) {
                Glide.with(context)
                    .load("http://gallery.dev.webant.ru/media/${adapter.list[adapterPosition].image?.name.toString()}")
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView)
            }
        }
    }

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = list[position].id.toLong()

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind()

    }
}

