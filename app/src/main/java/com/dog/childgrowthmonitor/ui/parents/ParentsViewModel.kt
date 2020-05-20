package com.dog.childgrowthmonitor.ui.parents

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildParents
import com.dog.childgrowthmonitor.data.child.ChildRepository
import com.dog.childgrowthmonitor.data.parents.Parent
import com.dog.childgrowthmonitor.data.parents.ParentChildrenCrossRef
import com.dog.childgrowthmonitor.data.parents.ParentRepository
import kotlinx.coroutines.*

class ParentsViewModel(
    private val childRepository: ChildRepository,
    private val parentRepository: ParentRepository,
    private val child: Child,
    private val resources: Resources
): ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val parents = MediatorLiveData<ChildParents>()

    private val _canNavigate = MutableLiveData<Boolean>()
    val canNavigate: LiveData<Boolean>
        get() = _canNavigate

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        getChildParents()
    }

    fun setParent(idParent: Long) {
        uiScope.launch {
            val parent = getParent(idParent)
            setParent(parent)
        }
    }

    fun setParent(parent: Parent){
        parents.value?.let {
            for (checkParent in it.parents) {
                if (checkParent.gender == parent.gender) {
                    parentRepository.removeParent(child.idChild, checkParent.idParent)
                    break
                }
            }
        }
        updateParent(parent)
    }

    private suspend fun getParent(idParent: Long): Parent{
        return withContext(Dispatchers.IO){
            parentRepository.getParent(idParent)!!
        }
    }

    private fun getChildParents() {
        parents.addSource(childRepository.getParents(child.idChild), parents::setValue)
    }

    fun cancelErrorMessage(){
        _errorMessage.value = null
    }

    private fun updateParent(parent: Parent) {
        uiScope.launch {
            withContext(Dispatchers.IO){
                val newParent = ParentChildrenCrossRef(
                    child.idChild,
                    parent.idParent
                )
                parentRepository.updateParent(newParent)
            }
            getChildParents()
        }
    }

    private fun showErrorMessage() {
        _errorMessage.value = resources.getString(R.string.error_zero_parents)
    }

    fun startNavigation() {
        if(parents.value!!.parents.isNotEmpty()){
            _canNavigate.value = true
        }else{
            showErrorMessage()
        }
    }

    fun cancelNavigation() {
        _canNavigate.value = false
    }
}