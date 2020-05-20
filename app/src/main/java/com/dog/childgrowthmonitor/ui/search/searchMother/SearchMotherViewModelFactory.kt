package com.dog.childgrowthmonitor.ui.search.searchMother

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.ui.search.SearchFilter
import java.lang.IllegalArgumentException

class SearchMotherViewModelFactory(
    private val context: Context,
    private val searchIn: String,
    private val filterBy: SearchFilter?
): ViewModelProvider.Factory {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchMotherViewModel::class.java)){
            return SearchMotherViewModel(
                context,
                searchIn,
                filterBy
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}