package com.dog.childgrowthmonitor.ui.add.user

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.user.User
import com.dog.childgrowthmonitor.data.user.UserRepository
import com.dog.childgrowthmonitor.util.capitalizeAllWords
import com.dog.childgrowthmonitor.util.validate
import com.dog.childgrowthmonitor.util.validateIsNotEmpty
import kotlinx.android.synthetic.main.fragment_add_user.view.*
import kotlinx.coroutines.*

class AddUserViewModel(
    private val userRepository: UserRepository,
    private val resources: Resources
): ViewModel() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val idUser = MediatorLiveData<Long>()
    var user: User = User()

    private val _canNavigate = MutableLiveData<Boolean>()
    val canNavigate: LiveData<Boolean>
        get() = _canNavigate

    private val _errorMessage= MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun saveUser(view: View){
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
            //If the idUser has a value means the User is trying to modify the name or last name or the user is Creating a parent
            idUser.value?.let {
                updateUser(
                    view.txt_first_name.text.toString(),
                    view.txt_last_name.text.toString()
                )
            } ?: run {
                user.firstName = view.txt_first_name.text.toString().capitalizeAllWords() //Can be two names
                user.lastName = view.txt_last_name.text.toString().capitalizeAllWords() //Can be two last names
                insertUser()
            }
        }
    }

    fun cancelNavigate(){
        _canNavigate.value = false
    }

    fun cancelErrorMessage(){
        _errorMessage.value = null
    }

    private fun insertUser(){
        uiScope.launch {
            var result = -1L
            withContext(Dispatchers.IO){
                result = userRepository.insert(user)
            }

            if(result > 0){
                idUser.value = result
                _canNavigate.value = true
            }else{
                cancelNavigate()
                _errorMessage.value = "${resources.getString(R.string.error_number, 3001)} ${resources.getString(R.string.error_repeated_full_name)}"
            }
        }
    }

    private fun updateUser(firstName: String, lastName: String){
        uiScope.launch {
            var result = -1L
            withContext(Dispatchers.IO){
                user = userRepository.getUser(idUser.value!!)!!
                user.firstName = firstName.capitalizeAllWords() //Can be two names
                user.lastName = lastName.capitalizeAllWords() //Can be two last names
                result = userRepository.update(user).toLong()
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