package com.dog.childgrowthmonitor.ui.visit.weightandheight

import android.content.res.Resources
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.visit.ChildPreviousVisits
import com.dog.childgrowthmonitor.data.visit.VisitRepository
import com.dog.childgrowthmonitor.util.*
import kotlinx.android.synthetic.main.add_measured.view.*
import kotlin.math.pow

class WeightAndHeightViewModel(
    private val visitRepository: VisitRepository,
    private val resources: Resources,
    private val child: Child
): ViewModel(){

    val BMI = MutableLiveData<Double>()
    private var weight: Double? = null
    private var height: Double? = null

    val previousVisits = MediatorLiveData<ChildPreviousVisits>()

    init {
        getVisits()
    }

    private fun getVisits() {
        previousVisits.addSource(visitRepository.getChildVisits(child.idChild), previousVisits::setValue)
    }

    private fun setBMI() {
        weight?.let {w ->
            height?.let {h ->
                if(h > w) {
                    BMI.value = w / (h.pow(2.0) / 10000)
                    return
                }
            }
        }
        BMI.value = null
    }

    fun setWeight(w: String) {
        weight = w.toDoubleOrNull()
        setBMI()
    }

    fun setHeight(h: String) {
        height = h.toDoubleOrNull()
        setBMI()
    }

    fun verifyWeight(view: View) {
        verifyData(view.weight_person,
            R.string.error_weight_out_of_limits,
            MIN_WEIGHT,
            MAX_WEIGHT
        )
    }

    fun verifyHeight(view: View) {
        verifyData(view.height_person,
            R.string.error_height_out_of_limits,
            MIN_HEIGHT,
            MAX_HEIGHT
        )
    }

    private fun verifyData(control: AppCompatEditText, errorId: Int, min: Double, max: Double){
        control.validate(
            resources.getString(errorId)
        ) { it.inRange(
            min,
            max
        ) }
    }
}