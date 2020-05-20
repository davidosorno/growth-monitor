package com.dog.childgrowthmonitor.ui.parents


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.data.parents.Parent
import com.dog.childgrowthmonitor.ui.add.parent.AddParentActivity
import com.dog.childgrowthmonitor.ui.search.SearchFilter
import com.dog.childgrowthmonitor.ui.search.SearchFragment
import com.dog.childgrowthmonitor.ui.search.SearchViewModel
import com.dog.childgrowthmonitor.util.*
import kotlinx.android.synthetic.main.fragment_parents.view.*

/**
 * A sim ple [Fragment] subclass.
 */
class ParentsFragment : Fragment() {

    private lateinit var viewModel: ParentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_parents, container, false)

        val child = arguments?.getParcelable<Child>(CHILD_ID)

        val factory = ParentsViewModelFactory(
            context!!,
            child!!
        )
        viewModel = ViewModelProvider(this, factory)[ParentsViewModel::class.java]
        setTitle(
            activity as AppCompatActivity,
            styleAppName(resources)!!,
            child.fullName
        )

        val searchFather = SearchFragment()
        val fatherArgs = Bundle()
        val searchMother = SearchFragment()
        val motherArgs = Bundle()

        val parentIntent = Intent(this.context, AddParentActivity::class.java)
        parentIntent.putExtra(CHILD_ID, child)

        root.fab_add_father.setOnClickListener {
            parentIntent.putExtra(FILTER_PARENT_BY, SearchFilter.FATHER.toString()) // 1 Father
            startActivityForResult(parentIntent, 1)
        }

        root.fab_add_mother.setOnClickListener {
            parentIntent.putExtra(FILTER_PARENT_BY, SearchFilter.MOTHER.toString()) // 2 Mother
            startActivityForResult(parentIntent, 1)
        }

        SearchViewModel.idOnSearch.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.setParent(it)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let{
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
                viewModel.cancelErrorMessage()
            }
        })

        root.parents_floating_action_button.setOnClickListener {
            viewModel.startNavigation()
        }

        viewModel.parents.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.parents.let { parents ->
                    for(parent in parents){
                        when(parent.gender){
                            1 -> fatherArgs.putLong(OPTIONAL_ID, parent.idParent)
                            2 -> motherArgs.putLong(OPTIONAL_ID, parent.idParent)
                        }
                    }
                }
                fatherArgs.putString(SEARCH_IN, PARENT_ID)
                fatherArgs.putString(FILTER_PARENT_BY, SearchFilter.FATHER.toString())
                searchFather.arguments = fatherArgs
                replaceFrameLayout(R.id.search_father, searchFather)

                motherArgs.putString(SEARCH_IN, PARENT_ID)
                motherArgs.putString(FILTER_PARENT_BY, SearchFilter.MOTHER.toString())
                searchMother.arguments = motherArgs
                replaceFrameLayout(R.id.search_mother, searchMother)
            }
        })

        viewModel.canNavigate.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(
                    ParentsFragmentDirections.actionParentsToNavWeightAndHeightFragment(viewModel.parents.value!!.child)
                )
                viewModel.cancelNavigation()
            }
        })

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                val parent = data?.getParcelableExtra<Parent>(PARENT_ID)
                viewModel.setParent(parent!!)
            }
        }
    }
}