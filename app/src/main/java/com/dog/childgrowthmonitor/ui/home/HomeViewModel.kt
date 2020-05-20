package com.dog.childgrowthmonitor.ui.home

import androidx.lifecycle.*
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildRepository

class HomeViewModel(
    private val childRepository: ChildRepository
):ViewModel() {

    private val _canNavigate = MutableLiveData<Boolean>()
    val canNavigate: LiveData<Boolean>
        get() = _canNavigate

    val child = MediatorLiveData<Child>()

    fun getChild(idChild: Long){
        child.addSource(childRepository.getChild(idChild)!!, child::setValue)
    }

    fun canNavigate(){
        _canNavigate.value = true
    }

    fun cancelNavigation() {
        _canNavigate.value = false
    }

////    TODO MUTABLELIVEDATA is useful when we need to create an object in DB, otherwise we have to use MEDIATORLIVEDATA to get data from DB
//
//    private val _children = MediatorLiveData<List<Child>>()
//    val children: LiveData<List<Child>>
//        get() = _children
//
//    init {
//        _children.addSource(childRepository.getChildren(), _children::setValue)
//    }
//
//    fun getData(list: List<Child>){
//        val newListSuggestions = mutableListOf<String>()
//        val newListId = mutableListOf<Long>()
//        for(i in list.indices){
//            newListSuggestions.add("${list[i].firstName} ${list[i].lastName}")
//            newListId.add(list[i].idChild)
//        }
//        SearchViewModel.listSuggestions.value = newListSuggestions
//        SearchViewModel.listId.value = newListId
//    }
}