package com.dog.childgrowthmonitor.ui.add.child.basicdata

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.util.*
import androidx.lifecycle.Observer
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.databinding.FragmentChildBasicDataBinding
import kotlinx.android.synthetic.main.show_parents_name.view.*
import java.util.*


class BasicDataFragment : Fragment() {
    
    lateinit var binding: FragmentChildBasicDataBinding
    lateinit var viewModel: BasicDataViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
//        root = inflater.inflate(R.layout.fragment_child_basic_data, container, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_child_basic_data,
            container,
            false
        )

        val child = arguments?.getParcelable<Child>(CHILD_ID)

        val factory = BasicDataFactory(
                context!!,
                child!!
            )
        viewModel = ViewModelProvider(this, factory)[BasicDataViewModel::class.java]

        var wasBorn: Calendar = Calendar.getInstance()

        binding.btnDateOfBirth.setOnClickListener {
            val dateBirth = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                wasBorn.set(year, month, dayOfMonth)

                if(isValidDate(wasBorn.time)){
                    val age = checkBetweenDates(wasBorn) //age is a Pair of [years], [months]
                    binding.txtAge.text = setAgeFormat(age)
                    binding.txtDateOfBirth.text = setFormatDate(wasBorn.time)
                }else{
                    showSnackBarErrorMessage(resources.getString(R.string.date_of_birth_not_valid), activity!!)
                    wasBorn = Calendar.getInstance()
                    binding.txtAge.text = ""
                    binding.txtDateOfBirth.text = ""
                }
            }
            DatePickerDialog(
                context!!,
                dateBirth,
                wasBorn.get(Calendar.YEAR),
                wasBorn.get(Calendar.MONTH),
                wasBorn.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.visitFloatingActionButton.setOnClickListener {
            viewModel.whereNavigate = NEW_VISIT
            saveData(wasBorn)
        }
        binding.parentsName.btn_change_parents.setOnClickListener {
            viewModel.whereNavigate = ADD_PARENTS
            saveData(wasBorn)
        }

        setTitle(
            activity as AppCompatActivity,
            styleAppName(resources)!!,
            child.fullName
        )
        binding.child = child
        wasBorn.time = child.birth


        viewModel.canNavigate.observe(viewLifecycleOwner, Observer {
            if(it){
                when(viewModel.whereNavigate){
                    NEW_VISIT -> {
                        findNavController().navigate(
                            BasicDataFragmentDirections.actionNavVisitToNavWeightAndHeightFragment(
                                child
                            )
                        )
                    }
                    ADD_PARENTS -> {
                        findNavController().navigate(
                            BasicDataFragmentDirections.actionNavVisitToParents(
                                child
                            )
                        )
                    }
                }

                viewModel.cancelNavigate()
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                showSnackBarErrorMessage(it, activity!!)
                viewModel.cancelNavigate()
                viewModel.cancelErrorMessage()
            }
        })


        viewModel.parents.observe(viewLifecycleOwner, Observer {
            if(it.parents.isNotEmpty()){
                binding.parentsName.visibility = VISIBLE
                for(parent in it.parents){
                    when(parent.gender){
                        MALE -> {
                            binding.parentsName.textViewFather.visibility = VISIBLE
                            binding.parentsName.txtFather.text = parent.fullName
                        }
                        FEMALE -> {
                            binding.parentsName.textViewMother.visibility = VISIBLE
                            binding.parentsName.txtMother.text = parent.fullName
                        }
                    }
                }
            }else {
                binding.parentsName.visibility = INVISIBLE
            }
        })

        return binding.root
    }

    private fun saveData(wasBorn: Calendar) {
        viewModel.saveData(
            wasBorn.time,
            when(true){
                binding.radioButtonMale.isChecked -> 1
                binding.radioButtonFemale.isChecked -> 2
                else -> 0
            }
        )
    }
}