package com.dog.childgrowthmonitor.ui.add.child


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.databinding.FragmentAddChildBinding
import com.dog.childgrowthmonitor.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddChildFragment : Fragment() {

    lateinit var viewModel: AddChildViewModel
    lateinit var binding: FragmentAddChildBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_child,
            container,
            false
        )

        setTitle(
            activity as AppCompatActivity,
            styleAppName(resources)!!,
            resources.getString(R.string.adding_child)
        )

        val factory = AddChildViewModelFactory(context!!, resources)
        viewModel = ViewModelProvider(this, factory)[AddChildViewModel::class.java]


        viewModel.canNavigate.observe(viewLifecycleOwner, Observer {
            if(it == true){
                hideKeyboard()
                findNavController().navigate(
                    AddChildFragmentDirections.actionNavAddChildToNavVisit(viewModel.child)
                )
                viewModel.cancelNavigate()
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let{
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
                viewModel.cancelErrorMessage()
            }
        })

        binding.addChildFab.setOnClickListener {
            viewModel.saveChild(binding.root)
        }

        return binding.root
    }

}
