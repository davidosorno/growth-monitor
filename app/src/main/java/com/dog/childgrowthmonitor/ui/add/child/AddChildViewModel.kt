package com.dog.childgrowthmonitor.ui.add.child

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildRepository
import com.dog.childgrowthmonitor.util.capitalizeAllWords
import com.dog.childgrowthmonitor.util.validate
import com.dog.childgrowthmonitor.util.validateIsNotEmpty
import kotlinx.android.synthetic.main.add_basic_data.view.*
import kotlinx.coroutines.*

class AddChildViewModel(
    private val childRepository: ChildRepository,
    private val resources: Resources
): ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val child = Child()

    private val _canNavigate = MutableLiveData<Boolean>()
    val canNavigate: LiveData<Boolean>
        get() = _canNavigate

    private val _errorMessage= MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun saveChild(view: View){
        if (
            view.txt_first_name.validate(
                view.resources.getString(
                    R.string.error_empty_field,
                    view.resources.getString(R.string.first_name)
                ),
                { it.validateIsNotEmpty() }
            ) &&
            view.txt_last_name.validate(
                view.resources.getString(
                    R.string.error_empty_field,
                    view.resources.getString(R.string.last_name)
                ),
                { it.validateIsNotEmpty() }
            )
        ) {
            //If idChild has a value means the User is trying to modify the name or last name
            child.firstName = view.txt_first_name.text.toString().capitalizeAllWords() //Can be two names
            child.lastName = view.txt_last_name.text.toString().capitalizeAllWords() //Can be two last names
            if(child.idChild > 0){
                update()
            }else{
                insert()
            }
//            child.value?.let {
//                update(
//                    view.txt_first_name.text.toString().capitalizeAllWords(), //Can be two names
//                    view.txt_last_name.text.toString().capitalizeAllWords() //Can be two last names
//                )
//            } ?: run {
//                insert(
//                    view.txt_first_name.text.toString().capitalizeAllWords(), //Can be two names
//                    view.txt_last_name.text.toString().capitalizeAllWords() //Can be two last names
//                )
//            }
        }
    }

    fun cancelNavigate(){
        _canNavigate.value = false
    }

    fun cancelErrorMessage(){
        _errorMessage.value = null
    }

    private fun insert(){
        uiScope.launch {
            var result = -1L
            withContext(Dispatchers.IO){
                result = childRepository.insert(child)
            }

            if(result > 0){
                child.idChild = result
                _canNavigate.value = true
            }else{
                cancelNavigate()
                _errorMessage.value = "${resources.getString(R.string.error_number, 3001)} ${resources.getString(R.string.error_repeated_full_name)}"
            }
        }
    }

    private fun update(){
        uiScope.launch {
            var result = -1
            withContext(Dispatchers.IO){
                result = childRepository.update(child)
            }
            if(result > 0){
                _canNavigate.value = true
            }else{
                cancelNavigate()
                _errorMessage.value = "${resources.getString(R.string.error_number, 3002)} ${resources.getString(R.string.error_updating_data)}"
            }
        }
    }
}