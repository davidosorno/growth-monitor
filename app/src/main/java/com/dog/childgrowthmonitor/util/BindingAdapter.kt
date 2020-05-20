package com.dog.childgrowthmonitor.util

import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.dog.childgrowthmonitor.data.child.Child
import java.util.*

@BindingAdapter("checkGender")
fun RadioButton.setChecked(child: Child?){
    child?.let {
        isChecked = when {
            child.gender == MALE && text == "Male" -> true //text is radioButton property
            child.gender == FEMALE && text == "Female" -> true
            else -> false

        }
    }
}

@BindingAdapter("setDateOfBirth")
fun AppCompatTextView.dateOfBirth(child: Child?){
    child?.let {
        text = setFormatDate(child.birth)
    }
}

@BindingAdapter("setAge")
fun AppCompatTextView.setAge(child: Child?){
    child?.let {
        val dateTmp = Calendar.getInstance()
        dateTmp.time = child.birth
        val age = checkBetweenDates(dateTmp) //age is a Pair of [years], [months]
        text = setAgeFormat(age)
    }
}