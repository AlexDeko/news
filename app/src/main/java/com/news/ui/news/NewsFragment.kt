package com.news.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.news.R
import com.news.client.Api
import com.news.data.adapters.NewsRecyclerAdapter
import com.news.data.dto.News
import io.ktor.client.request.get
import kotlinx.android.synthetic.main.fragment_new.*
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.cancel

class NewsFragment() : Fragment(), CoroutineScope by MainScope() {

    private lateinit var homeViewModel: NewsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(NewsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new, container, false)

        navigate()

        val indeterminateBar: ContentLoadingProgressBar = root.findViewById(R.id.indeterminateBar)
        with(indeterminateBar) {
            hide()
        }
        val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {

            swipeRefreshLayout.isRefreshing = false
        }



        return root
    }

    private fun navigate() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.action_newsFragment_to_contentFragment)
    }

    private fun setList(list: MutableList<News>) = launch {
        withContext(Dispatchers.Main) {
            with(recyclerList) {
                layoutManager = GridLayoutManager(context,2)

                adapter = NewsRecyclerAdapter(context.applicationContext, list)
            }
            Api.client.close()
        }
    }

    private fun fetchData() = launch {
        try {
            setList(list = Api.client.get<List<News>>(Api.url).toMutableList())
            indeterminateBar.visibility = View.GONE
        } catch (e: Exception) {
            error_no_internet.setImageResource(R.drawable.no_connect)
        }
    }


    @InternalCoroutinesApi
    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

}
