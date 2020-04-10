package com.news.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.news.R

class PopularFragment : Fragment() {

    private lateinit var popularViewModel: PopularViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        popularViewModel =
                ViewModelProviders.of(this).get(PopularViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_popular, container, false)
        navigate()
        val indeterminateBar: ContentLoadingProgressBar = root.findViewById(R.id.indeterminateBar)
        with(indeterminateBar) {
            hide()
        }

        val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.swipeRefresh)
        swipeRefreshLayout.setOnRefreshListener {

            swipeRefreshLayout.isRefreshing = false
        }
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    private fun navigate() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.action_popularFragment_to_contentFragment)
    }
}
