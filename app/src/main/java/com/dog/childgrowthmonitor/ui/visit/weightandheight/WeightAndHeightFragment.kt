package com.dog.childgrowthmonitor.ui.visit.weightandheight

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.util.*
import kotlinx.android.synthetic.main.add_measured.view.*
import kotlinx.android.synthetic.main.fragment_show_percentiles.view.*
import kotlinx.android.synthetic.main.fragment_weight_and_height.view.*
import kotlinx.android.synthetic.main.percentile_line.view.*
import java.util.*

class WeightAndHeightFragment : Fragment() {

    private lateinit var root: View
    private lateinit var viewModel: WeightAndHeightViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_weight_and_height, container, false)
        setHasOptionsMenu(true)


        val child = arguments?.getParcelable<Child>(CHILD_ID)

        val factory = WeightAndHeightFactory(
            context!!,
            child!!
        )
        viewModel = ViewModelProvider(this, factory)[WeightAndHeightViewModel::class.java]

        setTitle(
            activity as AppCompatActivity,
            styleAppName(resources)!!,
            child.fullName
        )

        root.weight_person.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus){
                viewModel.verifyWeight(view)
            }
        }

        root.height_person.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus){
                viewModel.verifyHeight(view)
            }
        }

        val topToBottom = AnimationUtils.loadAnimation(context, R.anim.toptobottom)
        val scaleToBig = AnimationUtils.loadAnimation(context, R.anim.scaletobig)
        val leftToRight = AnimationUtils.loadAnimation(context, R.anim.lefttoright)

        root.percentile_wfa.optional_value.text = resources.getString(R.string.weight_for_age)
        root.percentile_wfa.real_score_wfa.text = "47.8"
        root.percentile_wfa.z_score_wfa_left.text = "-0.06"

        root.percentile_hfa.optional_value.text = resources.getString(R.string.height_for_age)
        root.percentile_hfa.real_score_wfa.text = "49.0"
        root.percentile_hfa.z_score_wfa_left.text = "-0.03"

        root.percentile_bmifa.optional_value.text = resources.getString(R.string.BMI_for_age)
        root.percentile_bmifa.real_score_wfa.text = "46.2"
        root.percentile_bmifa.z_score_wfa_left.text = "-0.10"

        root.weight_person.afterTextChanged {
            viewModel.setWeight(root.weight_person.text.toString())
        }

        root.height_person.afterTextChanged {
            viewModel.setHeight(root.height_person.text.toString())
        }

        viewModel.BMI.observe(viewLifecycleOwner, Observer {
            it?.let {
                root.bmi.text = resources.getString(R.string.bmi, it)
                root.bmi.startAnimation(scaleToBig)
//                placingPercentiles()
            } ?: run {
                root.bmi.text = ""
            }
        })

        placingPercentiles()

        val adapter = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_dropdown_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        root.spin_previous_visits.adapter = adapter

        viewModel.previousVisits.observe(viewLifecycleOwner, Observer {
            if(it.visits.isNotEmpty()){
                for(visit in it.visits){
                    adapter.add(visit.dateVisit.toString())
                }
                root.spin_previous_visits.isEnabled = true
            } else {
                adapter.add(Calendar.getInstance().time.toString())
//                TODO LA PRIMERA VEZ TIENE QUE ESTAR BLOQUEADO Y SE DEBE DESBLOQUEAR CUANDO GUARDE LA INFORMACION PARA PODER VER LOS RESULTADOS DE OTRAS VISITAS
//                root.spin_previous_visits.isEnabled = false
            }
        })
        return root
    }

    private fun placingPercentiles() {
        var objectAnimator = ObjectAnimator.ofFloat(root.percentile_wfa.img_percentile_position, "translationX", 0f, 215f)
        objectAnimator.duration = 2000
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()

        objectAnimator = ObjectAnimator.ofFloat(root.percentile_hfa.img_percentile_position, "translationX", 0f, 225f)
        objectAnimator.duration = 2000
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()

        objectAnimator = ObjectAnimator.ofFloat(root.percentile_bmifa.img_percentile_position, "translationX", 0f, 210f)
        objectAnimator.duration = 2000
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_parent -> {
                ToastMessage("ESTE ES EL MENSAJE")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}