package com.dog.childgrowthmonitor.util

import android.content.Context
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*


// ********************* EDIT TEXT*********************
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }
    })
}

fun AppCompatEditText.validate(message: String, validator: (String) -> Boolean): Boolean{
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
    return this.error.isNullOrBlank()
}


// ********************* STRING *********************
fun String.validateIsNotEmpty(): Boolean = this.isNotEmpty()

fun String.capitalizeAllWords(locale: Locale = Locale.US): String =
    split(" ").joinToString(" ") { it.toLowerCase(locale).capitalize() }
    .trim()

fun String.inRange(min: Double, max: Double): Boolean {
    if(this.validateIsNotEmpty()){
        return this.toFloat() in min..max
    }
    return false
}



// ********************* CONTEXT *********************
fun Context.hideKeyboard(view: View){
    val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}



// ********************* APP COMPAT ACTIVITY *********************




// ********************* FRAGMENT *********************
fun Fragment.hideKeyboard(){
    view?.let {
        activity?.hideKeyboard(it)
    }
}

fun Fragment.setTitle(appCompatActivity: AppCompatActivity, title: String, subTitle: String = "") {
    appCompatActivity.toolbar.title = title
    appCompatActivity.toolbar.subtitle = subTitle
}

fun Fragment.setTitle(appCompatActivity: AppCompatActivity, title: Spanned, subTitle: String = "") {
    appCompatActivity.toolbar.title = title
    appCompatActivity.toolbar.subtitle = subTitle
}

fun Fragment.replaceFrameLayout(frameLayout: Int, fragment: Fragment){
    parentFragmentManager.inTransaction {
        detach(fragment)
        attach(fragment)
        replace(frameLayout, fragment)
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction){
    beginTransaction().func().commit()
}


// ********************* OBSERVABLES *********************
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>){
    observe(lifecycleOwner, Observer<T> {
        observer.onChanged(it)
        removeObservers(lifecycleOwner)
    })
}