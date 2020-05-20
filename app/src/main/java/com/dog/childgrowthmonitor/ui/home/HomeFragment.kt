package com.dog.childgrowthmonitor.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.ui.search.SearchFragment
import com.dog.childgrowthmonitor.ui.search.SearchViewModel
import com.dog.childgrowthmonitor.util.*
import kotlinx.android.synthetic.main.home_fragment.view.*

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.home_fragment, container, false)

        val factory = GeneralViewModelFactory(context!!)
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        setTitle(
            activity as AppCompatActivity,
            styleAppName(resources)!!
        )

        SearchViewModel.idOnSearch.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.getChild(it)
            }
        })

        viewModel.child.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.canNavigate()
            }
        })

        viewModel.canNavigate.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(
                    HomeFragmentDirections.actionNavHomeToNavVisit(viewModel.child.value!!)
                )
                viewModel.cancelNavigation()
            }
        })




        val searchChild = SearchFragment()
        val args = Bundle()
        args.putString(SEARCH_IN, CHILD_ID)
        searchChild.arguments = args
        replaceFrameLayout(R.id.frame_search_child, searchChild)

        return root
    }
}
