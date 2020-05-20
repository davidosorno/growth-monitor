package com.dog.childgrowthmonitor.util

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.dog.childgrowthmonitor.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun checkBetweenDates(selectedDate: Calendar): Pair<Int, Int>{
    val today = Calendar.getInstance()
    val diff = today.timeInMillis - selectedDate.timeInMillis
    if(diff < 0){
        return Pair(0, 0)
    }
    val days = TimeUnit.MILLISECONDS.toDays(diff)
    val years = (days/360).toInt()
    val months = ((days - (360*years)) / 30).toInt()
    return Pair(years, months)
}

fun isValidDate(date: Date?): Boolean{
    date?.let {
        val today = Calendar.getInstance().time
        if (
            setFormatDate(date) == setFormatDate(today) ||
            date.after(today)
        ) {
            return false
        }
    }
    return true
}

//Solo para Debug
fun ToastMessage(message: String){
    Toast.makeText(GLOBAL_CONTEXT, message, Toast.LENGTH_SHORT).show()
}

fun styleAppName (resources: Resources): Spanned? {
    val text: String = resources.getString(R.string.app_name_style)
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
    return null
}

fun showSnackBarErrorMessage(message: String, activity: Activity){
    Snackbar.make(
        activity.findViewById(android.R.id.content) as View,
        message,
        Snackbar.LENGTH_LONG
    ).show()
}

fun showToastErrorMessage(message: String, activity: Activity){
    Toast.makeText(
        activity.applicationContext,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun setFormatDate(date: Date): String{
    val dateFormat = "MM/dd/yyyy" //TODO cambiarlo por el formato de localizacion
    val sdf = SimpleDateFormat(dateFormat, Locale.US)
    return sdf.format(date)
}

fun setAgeFormat(age: Pair<Int, Int>): String {
    return if(age == Pair(0, 0)) "" else GLOBAL_CONTEXT!!.resources.getString(
                                            R.string.show_age,
                                            age.first,
                                            age.second,
                                            ((age.first * 12) + age.second)
                                        )
}