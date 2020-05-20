package com.dog.childgrowthmonitor.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SearchViewModelFactory(
    private val context: Context,
    private val searchIn: String,
    private val filterBy: SearchFilter?
): ViewModelProvider.Factory {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(
                context,
                searchIn,
                filterBy
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}