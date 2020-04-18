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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_new.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.get

class NewsFragment() : Fragment() {

   // private var list: Photos? = null
    private var page: Int = 1
    private var countItems = 10
    val news: MutableList<News>? = arrayListOf()
    private var noEmptyList = false
    private var isEndScrolling = false
    private var updateData = false
    private val photos: PhotosApi = get()
    private val compositeDisposable = CompositeDisposable()



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_new, container, false)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
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
                layoutManager = GridLayoutManager(requireContext(),2)
                adapter = NewsRecyclerAdapter(context.applicationContext, news!!)

                //adapter.it = page * countItems

                val scrollListener = object : RecyclerView.OnScrollListener() {

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

//                        if (!recyclerView.canScrollVertically(1)) {
//                            fetchData()
//                        }

                        val manage: GridLayoutManager =
                            recyclerView.layoutManager as GridLayoutManager
                        manage.findLastVisibleItemPosition()

                        if (manage.findLastVisibleItemPosition() >= manage.itemCount - 1) {
                            page++

                            fetchData()
                        }
                    }
                }
               addOnScrollListener(scrollListener)
            }
        noEmptyList = true
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
                error_no_internet.visibility = View.INVISIBLE

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
