package com.dog.childgrowthmonitor.ui.add.user


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.databinding.FragmentAddUserBinding
import com.dog.childgrowthmonitor.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddUserFragment : Fragment() {

    lateinit var viewModel: AddUserViewModel
    lateinit var binding: FragmentAddUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_user,
            container,
            false
        )

        val idUserParent = if(arguments?.getLong("idUserParent")!! > 0L)
                                    arguments?.getLong("idUserParent")
                                else
                                    null


//        setTitle(
//            activity as AppCompatActivity,
//            styleAppName(resources)!!,
//            resources.getString(R.string.add_user)
//        )

        val factory = AddUserFactory(context!!, resources)
        viewModel = ViewModelProvider(this, factory)[AddUserViewModel::class.java]


        viewModel.canNavigate.observe(viewLifecycleOwner, Observer {
            if(it == true){
                hideKeyboard()
//                viewModel.userParent.value?.let {
//                    findNavController().navigate(
//                        AddUserFragmentDirections.actionNavAddUserToNavWeightAndHeightFragment(viewModel.idUser.value!!)
//                    )
//                } ?: run {
//                    findNavController().navigate(
//                        AddUserFragmentDirections.actionUserFragmentToNavVisit(viewModel.idUser.value!!)
//                    )
//                }
//                if(viewModel.userParent.value?.parent == false) { //Adding a child
//                    findNavController().navigate(
//                        AddUserFragmentDirections.actionUserFragmentToNavVisit(viewModel.idUser.value!!)
//                    )
//                }else{ //Adding a parent
//                    findNavController().navigate(
//                        AddUserFragmentDirections.actionNavAddUserToNavWeightAndHeightFragment(viewModel.idUser.value!!)
//                    )
//                }
                viewModel.cancelNavigate()
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let{
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
                viewModel.cancelErrorMessage()
            }
        })

        binding.userFloatingActionButton.setOnClickListener {
            viewModel.saveUser(binding.root)
        }

        idUserParent?.let {
            viewModel.idUser.value = it
        }

        return binding.root
    }

}
