package com.news.data.adapters

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.R
import com.news.data.dto.News
import com.news.ui.MainActivity
import com.news.ui.popular.PopularFragment
import kotlinx.android.synthetic.main.image_item_list.view.*

class NewsViewHolder(adapter: NewsRecyclerAdapter, view: View) : BaseViewHolder(adapter, view) {
    init {
        with(itemView) {
            Glide.with(adapter.context)
                .asBitmap()
                .load("http://gallery.dev.webant.ru/media/" + adapter.list[adapterPosition].id)
                .into(imageView)

            imageView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val intent = Intent().apply {
                        putExtra(
                            "url", adapter.list[adapterPosition].id
                        )
                        putExtra(
                            "name", adapter.list[adapterPosition].name
                        )
                        putExtra(
                            "description", adapter.list[adapterPosition].description
                        )
                    }
                    itemView.context.applicationContext.sendBroadcast(intent)



                    if(adapter.context == PopularFragment::getContext)
                        Navigation.createNavigateOnClickListener(R.id.action_popularFragment_to_contentFragment)
                     else Navigation.createNavigateOnClickListener(R.id.action_newsFragment_to_contentFragment)

//                    if(adapter.list[adapterPosition].typePopular == true)
//                        Navigation.createNavigateOnClickListener(R.id.action_popularFragment_to_contentFragment)
//                    else Navigation.createNavigateOnClickListener(R.id.action_newsFragment_to_contentFragment)
                }
            }
        }
    }



    override fun bind(news: News) {
        with(itemView) {



        }
    }
}
