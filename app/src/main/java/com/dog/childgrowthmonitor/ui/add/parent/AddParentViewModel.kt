package com.dog.childgrowthmonitor.ui.add.parent

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.parents.Parent
import com.dog.childgrowthmonitor.data.parents.ParentChildrenCrossRef
import com.dog.childgrowthmonitor.data.parents.ParentRepository
import com.dog.childgrowthmonitor.util.capitalizeAllWords
import com.dog.childgrowthmonitor.util.validate
import com.dog.childgrowthmonitor.util.validateIsNotEmpty
import kotlinx.android.synthetic.main.add_basic_data.view.*
import kotlinx.android.synthetic.main.add_measured.view.*
import kotlinx.coroutines.*

class AddParentViewModel(
    private val parentRepository: ParentRepository,
    private val child: Child,
    private val resources: Resources,
    private val genderParent: Int //1 Male --- 2 Female
): ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val parent = Parent()

    private val _canNavigate = MutableLiveData<Boolean>()
    val canNavigate: LiveData<Boolean>
        get() = _canNavigate

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun save(view: View){
        if (
            view.txt_address.validate(
                view.resources.getString(
                    R.string.error_empty_field,
                    view.resources.getString(R.string.first_name)
                )
            ) { it.validateIsNotEmpty() }
            &&
            view.txt_last_name.validate(
                view.resources.getString(
                    R.string.error_empty_field,
                    view.resources.getString(R.string.last_name)
                )
            ) { it.validateIsNotEmpty() }
        ) {
            saveParent(view)
        }
    }

    private fun saveParent(view: View) {
        uiScope.launch {
            parent.firstName = view.txt_address.text.toString().capitalizeAllWords()
            parent.lastName = view.txt_last_name.text.toString().capitalizeAllWords()
            parent.height = view.height_person.text.toString().toIntOrNull() ?: 0
            parent.weight = view.weight_person.text.toString().toFloatOrNull() ?: 0F
            parent.gender = genderParent
            if(parent.idParent != 0L){
                update()
            } else{
                insert()
            }
        }
    }

    private suspend fun insert(){
        var result = -1L
        withContext(Dispatchers.IO){
            result = parentRepository.insert(parent)
            if(result > 0) {
                parent.idParent = result
                val parentChild = ParentChildrenCrossRef(
                    parent.idParent,
                    child.idChild
                )
                parentRepository.insert(parentChild)
            }
        }
        if(result > 0){
            _canNavigate.value = true
        }else{
            cancelNavigate()
            _errorMessage.value = "${resources.getString(R.string.error_number, 5001)} ${resources.getString(R.string.error_repeated_full_name)}"
        }
    }

    private suspend fun update(){
        var result = -1L
        withContext(Dispatchers.IO){
            result = parentRepository.update(parent).toLong()
        }
        if(result > 0){
            _canNavigate.value = true
        }else{
            cancelNavigate()
            _errorMessage.value = "${resources.getString(R.string.error_number, 5002)} ${resources.getString(R.string.error_updating_data)}"
        }
    }

    fun cancelNavigate(){
        _canNavigate.value = false
    }

    fun cancelErrorMessage(){
        _errorMessage.value = null
    }
}