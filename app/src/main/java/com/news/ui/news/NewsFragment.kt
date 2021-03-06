package com.news.ui.news

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.R
import com.news.client.PhotosApi
import com.news.data.adapters.NewsRecyclerAdapter
import com.news.data.dto.News
import com.news.ui.BaseFragment
import com.news.utils.InternetСonnection
import com.news.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_new.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.get

class NewsFragment() : BaseFragment() {

    private var page: Int = 1
    val news: MutableList<News> = arrayListOf()
    private val photos: PhotosApi = get()
    private var isEndPage: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fetchData()
        setList()
        setSwipeRefresh()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            page = 1
            news.clear()
            fetchData()
            notifyDataChangeAdapter()
            swipeRefresh.isRefreshing = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setList() {
        with(recyclerList) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = NewsRecyclerAdapter(news) { news ->
                navigate(
                    news.image?.name.orEmpty(),
                    news.name.orEmpty(),
                    news.description.orEmpty()
                )
            }
            val scrollListener = object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager: GridLayoutManager =
                        recyclerView.layoutManager as GridLayoutManager
                    if (layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 1 &&
                        isEndPage
                    ) {
                        page++
                        fetchData()
                        isEndPage = false
                    }
                }
            }
            addOnScrollListener(scrollListener)
        }
    }

    private fun navigate(file: String, name: String, descriptions: String) {
        findNavController()
            .navigate(R.id.action_newsFragment_to_contentFragment, Bundle().apply {
                putString(Utils.file, file)
                putString(Utils.name, name)
                putString(Utils.description, descriptions)
            })
    }

    private fun changeProgressState(state: Boolean) {
        when (state) {
            true -> indeterminateBar.show()
            false -> indeterminateBar.hide()
        }
    }

    private fun fetchData() {
        changeProgressState(true)

        if (InternetСonnection.isConnect(requireContext())) {
            photos.getPhotos(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    changeProgressState(false)
                    isEndPage = true
                }
                .subscribe({ photos ->
                    photos.news?.let { news.addAll(it) }
                    notifyDataChangeAdapter()
                    error_no_internet.visibility = View.INVISIBLE
                }, {

                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
                })
                .addTo(compositeDisposable)
        } else setErrorNoInternet()

        changeProgressState(false)
    }

    private fun notifyDataChangeAdapter() {
        recyclerList.adapter?.notifyDataSetChanged()
    }

    private fun setErrorNoInternet() {
        indeterminateBar.hide()
        error_no_internet.visibility = View.VISIBLE
    }
}
