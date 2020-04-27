package com.news.data.navigate

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.news.R

object Navigate {
    fun navigate(file: String, name: String, descriptions: String) {
        Navigation.createNavigateOnClickListener(
            R.id.contentFragment,
            Bundle().apply {
                putString("file", file)
                putString("name", name)
                putString("description", descriptions)
            })

    }

}