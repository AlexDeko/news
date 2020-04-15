package com.news.ui.news

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.R
import com.news.client.PhotosApi
import com.news.data.adapters.NewsRecyclerAdapter
import com.news.data.dto.News
import com.news.data.dto.Photos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_new.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.get

class NewsFragment() : Fragment() {

    private var list: Photos? = null
    companion object var page: Int = 1
    val news: MutableList<News>? = arrayListOf()
    private var noEmptyList = false
    private var isEndScrolling = false
    private val photos: PhotosApi = get()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
      //  homeViewModel =
      //          ViewModelProviders.of(this).get(NewsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new, container, false)
//        fetchData()
//        setList()
//
//        val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
//        swipeRefreshLayout.setOnRefreshListener {
//            page = 1
//            news?.clear()
//            fetchData()
//            notifyDataChangeAdapter()
//            swipeRefreshLayout.isRefreshing = false
//        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fetchData()
        setList()

        with(swipeRefresh) {
            setOnRefreshListener {
                page = 1
                news?.clear()
                fetchData()
                notifyDataChangeAdapter()
                isRefreshing = false
            }
        }
        super.onViewCreated(view, savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setList() {


            with(recyclerList) {
                layoutManager = GridLayoutManager(context,2)
                adapter = NewsRecyclerAdapter(context.applicationContext, news!!)

                val scrollListener: RecyclerView.OnScrollListener = object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                      //  val totalItemCount = layoutManager?.itemCount

                        if (scrollState == RecyclerView.SCROLL_STATE_IDLE) isEndScrolling = true

                    }
                }

               addOnScrollListener(scrollListener)
                
            }

    }


    private fun fetchData() {
        progressShow()

        photos.getPhotos(page).repeatUntil {
            false
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.list?.let { it1 -> news?.addAll(it1) }
                if (noEmptyList) notifyDataChangeAdapter()
                page++
            }, {
                setErrorNoInternet()
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            })
            .addTo(compositeDisposable)

        progressHide()
    }

    private fun notifyDataChangeAdapter() {
        recyclerList.adapter?.notifyDataSetChanged()
    }

    private fun progressShow() {
            indeterminateBar.show()
    }

    private fun progressHide(){
            indeterminateBar.hide()
    }

    private fun setErrorNoInternet() {
            indeterminateBar.hide()
            error_no_internet.setImageResource(R.drawable.ic_no_internet)

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
