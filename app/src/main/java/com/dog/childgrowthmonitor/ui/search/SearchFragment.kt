package com.dog.childgrowthmonitor.ui.search


import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.util.*
import kotlinx.android.synthetic.main.app_search.view.*
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.app_search, container, false)

        val searchIn = arguments?.getString(SEARCH_IN, "")
        val stringFilter: String? = arguments?.getString(FILTER_PARENT_BY, null)
        val filterBy: SearchFilter? = stringFilter?.let {
                                        SearchFilter.valueOf(
                                            stringFilter.toUpperCase(Locale.US))
                                        }
        val optionalId: Long = arguments?.getLong(OPTIONAL_ID, -1)!!

        val factory = SearchViewModelFactory(
            context!!,
            searchIn!!,
            filterBy
        )
        val viewModel = ViewModelProvider(this, factory)[SearchViewModel::class.java]

        ////        *********************** Search user by name or last name in the database (They already loaded in the ListView) ***********************
        val listOfChild = ArrayList<String>()
        val arrayAdapter = ArrayAdapter(context!!, android.R.layout.test_list_item, listOfChild)
        root.list_view_all.adapter = arrayAdapter

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_result_search)
        val cursorAdapter = SimpleCursorAdapter(context, R.layout.app_search_item, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)

        root.search.queryHint = getString(R.string.search)
        root.search.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1
        root.search.suggestionsAdapter = cursorAdapter


        // ******************* Adding Items to de adapter from database
        val listOfSuggestions = mutableListOf<String>()
        val listOfId = mutableListOf<Long>()

        viewModel.tmpList.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.setData(it.toList())
            }
        })

        viewModel.listSuggestions.observe(viewLifecycleOwner, Observer {
            it?.let {
                val itemsCount = it.size
                listOfSuggestions.clear()
                for(i in 0 until itemsCount){
                    listOfSuggestions.add(it[i])
                }
            }
        })

        viewModel.listId.observe(viewLifecycleOwner, Observer {
            it?.let {
                val itemsCount = it.size
                listOfId.clear()
                for(i in 0 until itemsCount){
                    listOfId.add(it[i])
                }
                if(optionalId > 0) {
                    root.search.setQuery(listOfSuggestions[listOfId.indexOf(optionalId)], false)
                }
            }
        })

        root.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                newText?.let {
                    listOfSuggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(newText, true)) {
                            cursor.addRow(arrayOf(index, suggestion))
                        }
                    }
                }
                cursorAdapter.changeCursor(cursor)
                return true
            }
        })

        root.search.setOnSuggestionListener(object : SearchView.OnSuggestionListener{
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                hideKeyboard()
                val cursor = root.search.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))!!
                root.search.setQuery(selection, false)
                arrayAdapter.add(selection) //Adding item to the list

                val id = listOfSuggestions.indexOf(selection)
                viewModel.setId(listOfId[id])
                root.search.setQuery(selection, false)
                viewModel.cancelCode()
                return true
            }
        })
        return root
    }
}