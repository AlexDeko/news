package com.news.ui

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment() {
    val compositeDisposable = CompositeDisposable()

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}