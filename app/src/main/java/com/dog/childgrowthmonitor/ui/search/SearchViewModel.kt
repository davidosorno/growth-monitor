package com.dog.childgrowthmonitor.ui.search

import android.content.Context
import androidx.lifecycle.*
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildRepository
import com.dog.childgrowthmonitor.data.parents.Parent
import com.dog.childgrowthmonitor.data.parents.ParentRepository
import com.dog.childgrowthmonitor.util.CHILD_ID
import com.dog.childgrowthmonitor.util.PARENT_ID

class SearchViewModel(
    private val context: Context,
    private val searchIn: String,
    private val filterBy: SearchFilter?
): ViewModel() {

    companion object {
        private val _idOnSearch: MutableLiveData<Long> = MutableLiveData<Long>()
        val idOnSearch: LiveData<Long>
            get() = _idOnSearch
    }

    val tmpList = MediatorLiveData<List<Any>>()
    val listSuggestions = MediatorLiveData<List<String>>()
    val listId = MediatorLiveData<List<Long>>()

    init {
        getData()
    }

    private fun getData(){
        when(searchIn){
            CHILD_ID -> {
                val childRepository = ChildRepository.getInstance(context)
                childRepository?.let {
                    tmpList.addSource(childRepository.getChildren(), tmpList::setValue)
                }
            }
            PARENT_ID -> {
                val parentRepository = ParentRepository.getInstance(context)
                parentRepository?.let {
                    tmpList.addSource(parentRepository.getAllParentsFilterBy(filterBy!!), tmpList::setValue)
                }
            }
        }
    }

    @Suppress("unchecked_cast")
    fun setData(list: List<Any>){
        val newListSuggestions = mutableListOf<String>()
        val newListId = mutableListOf<Long>()
        when(searchIn){
            CHILD_ID -> {
                list as List<Child>
                for(i in list.indices){
                    newListSuggestions.add("${list[i].firstName} ${list[i].lastName}")
                    newListId.add(list[i].idChild)
                }
            }
            PARENT_ID -> {
                list as List<Parent>
                for(i in list.indices){
                    newListSuggestions.add("${list[i].firstName} ${list[i].lastName}")
                    newListId.add(list[i].idParent)
                }
            }
        }
        listSuggestions.value = newListSuggestions
        listId.value = newListId
    }

    fun setId(idFromSearch: Long){
        _idOnSearch.value = idFromSearch
    }

    fun cancelCode(){
        _idOnSearch.value = null
    }
}