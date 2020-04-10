package com.news.ui.content

import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContentViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setContent() {
        var url = "http://gallery.dev.webant.ru/media/"
        var name: String? = null
        var description: String? = null
        try {
            val arguments =  activity?.intent?.extras
            if (arguments != null) {
                url += arguments["Url"].toString()
                Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .into(contentNewsImageView)
                name = arguments["Name"].toString()
                description = arguments["Description"].toString()
            }
            titleContentNews.text = name
            text.text = description
            contentNewsImageView.setImageURI(Uri.parse(url))
        } catch (e: Exception) {

            Toast.makeText(context, R.string.errorContent, Toast.LENGTH_SHORT).show()
        }
    }

}
