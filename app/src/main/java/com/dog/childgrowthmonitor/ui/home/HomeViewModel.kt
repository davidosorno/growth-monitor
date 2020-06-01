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
}