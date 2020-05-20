package com.dog.childgrowthmonitor.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.data.child.ChildRepository
import java.lang.RuntimeException

class GeneralViewModelFactory(
    private val context: Context
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("Unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try{
            return modelClass.getConstructor(ChildRepository::class.java)
                .newInstance(ChildRepository.getInstance(context))
        }catch (e: InstantiationException){
            throw RuntimeException("1 Cannot create an isntance of $modelClass", e)
        }catch (e: IllegalAccessException){
            throw RuntimeException("2 Cannot create an isntance of $modelClass", e)
        }
//        return when (modelClass.simpleName) {
//            "HomeViewModel" -> {
//                HomeViewModel(context) as T
//            }
//            "ParentsViewModel" -> {
//                ParentsViewModel(context) as T
//            }
//            else -> throw IllegalArgumentException("Unknown View Model Class")
//        }

    }
}