package com.news.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.news.R
import com.news.client.PopularPhotosApi
import com.news.data.adapters.NewsRecyclerAdapter
import com.news.data.dto.News
import com.news.data.dto.Photos
import com.news.ui.news.NewsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_popular.error_no_internet
import kotlinx.android.synthetic.main.fragment_popular.indeterminateBar
import kotlinx.coroutines.*
import org.koin.android.ext.android.get

class PopularFragment : Fragment() {

    private var list: Photos? = null

    companion object

    var page: Int = 1
    val news: MutableList<News>? = arrayListOf()
    private var noEmptyList = false
    private val photos: PopularPhotosApi = get()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // homeViewModel =
    //        ViewModelProviders.of(this).get(NewsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new, container, false)



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fetchData()
        setList()

        //  val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
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

    private fun setList() {
        with(recyclerListPopular) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = NewsRecyclerAdapter(context.applicationContext, news!!)
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
            }, {
                setErrorNoInternet()
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            })
            .addTo(compositeDisposable)
        page++
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