package com.dog.childgrowthmonitor.ui.add.child.basicdata

import android.content.res.Resources
import androidx.lifecycle.*
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.child.ChildParents
import com.dog.childgrowthmonitor.data.child.ChildRepository
import com.dog.childgrowthmonitor.util.ADD_PARENTS
import com.dog.childgrowthmonitor.util.NEW_VISIT
import com.dog.childgrowthmonitor.util.isValidDate
import kotlinx.coroutines.*
import java.util.*

class BasicDataViewModel(
    private val childRepository: ChildRepository,
    private val child: Child,
    private val resources: Resources
):ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val parents = MediatorLiveData<ChildParents>()

    private val _canNavigate = MutableLiveData<Boolean>()
    val canNavigate: LiveData<Boolean>
        get() = _canNavigate
    var whereNavigate: String = NEW_VISIT

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        parents.addSource(childRepository.getParents(child.idChild), parents::setValue)
    }

    fun saveData(wasBorn: Date, gender: Int){
        if(!isValidDate(wasBorn)){
            _errorMessage.value = "${resources.getString(R.string.error_number, 1002)} ${resources.getString(R.string.date_birth)}: ${resources.getString(R.string.date_of_birth_not_valid)}"
            return
        }

        child.gender = when(gender) {
                1, 2 -> gender
                else -> {
                    _errorMessage.value = "${resources.getString(R.string.error_number,1001)} ${resources.getString(R.string.gender)}: ${resources.getString(R.string.error_select_option)}"
                    return
                }
            }
        child.birth = wasBorn
        updateChild()


        if(parents.value!!.parents.isEmpty()){
            whereNavigate = ADD_PARENTS
        }

//        val visit = Visit(
//            idVisit = idVisit.value ?: 0L,
//            child_id = child.idChild
//        )
//
//        idVisit.value?.let {
//            updateVisit(visit)
//        } ?: run {
//            insert(visit)
//        }
    }

//    private fun insert(visit: Visit){
//        uiScope.launch {
//            var result = -1L
//            withContext(Dispatchers.IO){
//                result = visitRepository.insert(visit)
//            }
//
//            if(result > 0){
//                idVisit.value = result
//                _canNavigate.value = true
//            }else{
//                cancelNavigate()
//                _errorMessage.value = "${resources.getString(R.string.error_number, 1003)} ${resources.getString(R.string.error_saving_data)}"
//            }
//        }
//    }

//    private fun updateVisit(visit: Visit) {
//        uiScope.launch {
//            var result = -1L
//            withContext(Dispatchers.IO){
//                result = visitRepository.update(visit).toLong()
//            }
//
//            if(result > 0){
//                _canNavigate.value = true
//            }else{
//                cancelNavigate()
//                _errorMessage.value = "${resources.getString(R.string.error_number, 1004)} ${resources.getString(R.string.error_updating_data)}"
//            }
//        }
//    }

    private fun updateChild(){
        uiScope.launch {
            var result = -1
            withContext(Dispatchers.IO){
                result = childRepository.update(child)
            }
            if(result > 0) {
                _canNavigate.value = true
            }else{
                _errorMessage.value = "${resources.getString(R.string.error_number, 1005)} ${ resources.getString(R.string.error_child_information)}"
            }
        }
    }

    fun cancelNavigate() {
        _canNavigate.value = false
    }

    fun cancelErrorMessage(){
        _errorMessage.value = null
    }
}