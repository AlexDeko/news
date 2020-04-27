package com.news.ui.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.news.R
import com.news.utils.Utils
import kotlinx.android.synthetic.main.content_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope


class ContentFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setContent()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setContent() {
        if (arguments != null)
            loadImage()
        else
            showError()
    }

    private fun loadImage() {
        val url = "http://gallery.dev.webant.ru/media/${arguments?.getString(Utils.file).toString()}"
        titleContentNews.text = arguments?.getString(Utils.name).toString()
        text.text = arguments?.getString(Utils.description).toString()

        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(contentNewsImageView)
    }

    private fun showError() =
        Toast.makeText(context, R.string.errorContent, Toast.LENGTH_SHORT).show()
}