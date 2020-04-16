package com.news.ui.content

import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.news.R
import kotlinx.android.synthetic.main.content_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


class ContentFragment : Fragment(), CoroutineScope by MainScope() {

    companion object {
        fun newInstance() = ContentFragment()
    }

    private lateinit var viewModel: ContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_fragment, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ContentViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setContent()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setContent() {
        var url = "http://gallery.dev.webant.ru/media/"
        arguments?.let {
            it.getString("url")
            url += it.getString("file")
            titleContentNews.text = it.getString("name")
            text.text = it.getString("description")
        }


        try {
                Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .into(contentNewsImageView)

        } catch (e: Exception) {
            Toast.makeText(context, R.string.errorContent, Toast.LENGTH_SHORT).show()
        }
    }

}
