package com.news.ui.popular

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.R
import com.news.client.PopularPhotosApi
import com.news.data.adapters.NewsRecyclerAdapter
import com.news.data.dto.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_popular.error_no_internet
import kotlinx.android.synthetic.main.fragment_popular.indeterminateBar
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.get

class PopularFragment : Fragment() {

    private var page: Int = 1
    val news: MutableList<News> = arrayListOf()
    private var noEmptyList = false
    private val photos: PopularPhotosApi = get()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_popular, container, false)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fetchData()
        setList()

        with(swipeRefresh) {
            setOnRefreshListener {
                page = 1
                news.clear()
                fetchData()
                notifyDataChangeAdapter()
                isRefreshing = false
            }
        }
        super.onViewCreated(view, savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setList() {
        with(recyclerListPopular) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = NewsRecyclerAdapter( news)

            val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)



                    val layoutManager: GridLayoutManager =
                        recyclerView.layoutManager as GridLayoutManager
                    layoutManager.findLastVisibleItemPosition()

                    if (layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 1) {
                        page++

                        fetchData()
                    }
                }
            }
            addOnScrollListener(scrollListener)
        }
    }

    fun navigate(file: String, name: String, descriptions: String){

        findNavController()
            .navigate(R.id.action_popularFragment_to_contentFragment, Bundle().apply {
                putString("file", file)
                putString("name", name)
                putString("description", descriptions)
            })
    }


    private fun fetchData() {
        progressShow()

        photos.getPhotos(page).repeatUntil {
            false
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.news?.let { it1 -> news.addAll(it1) }
                if (noEmptyList) notifyDataChangeAdapter()

                error_no_internet.visibility = View.INVISIBLE
            }, {
                setErrorNoInternet()
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            })
            //.addTo(compositeDisposable)

        progressHide()
    }

    private fun notifyDataChangeAdapter() {
        recyclerListPopular.adapter?.notifyDataSetChanged()
    }

    private fun progressShow() {
        indeterminateBar.show()
    }

    private fun progressHide() {
        indeterminateBar.hide()
    }

    private fun setErrorNoInternet() {
        if (indeterminateBar.isVisible) indeterminateBar.hide()
        error_no_internet.visibility = View.VISIBLE

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    @InternalCoroutinesApi
    override fun onDestroy() {
        super.onDestroy()
    }
}