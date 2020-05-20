package com.dog.childgrowthmonitor.ui.add.parent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dog.childgrowthmonitor.R
import com.dog.childgrowthmonitor.data.child.Child
import com.dog.childgrowthmonitor.databinding.ActivityAddParentBinding
import com.dog.childgrowthmonitor.util.*
import kotlinx.android.synthetic.main.add_measured.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.lang.Exception

class AddParentActivity : AppCompatActivity() {

    lateinit var viewModel: AddParentViewModel
    lateinit var root: ActivityAddParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        root = DataBindingUtil.setContentView(this, R.layout.activity_add_parent)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val child = intent.getParcelableExtra<Child>(CHILD_ID)
        val typeParent= intent.getStringExtra(FILTER_PARENT_BY)!!

        title = styleAppName(resources) ?: getString(R.string.app_name)
        toolbar.subtitle = resources.getString(R.string.adding_parent, typeParent.capitalizeAllWords())

        val factory = AddParentViewModelFactory(
            applicationContext,
            child!!,
            resources,
            when(typeParent){
                    "FATHER"-> 1
                    "MOTHER" -> 2
                    else -> throw Exception()
                }
        )
        viewModel = ViewModelProvider(this, factory)[AddParentViewModel::class.java]

        measured.visibility = GONE
        oedema.visibility = GONE
        group_measured.visibility = GONE
        group_oedema.visibility = GONE

        viewModel.canNavigate.observe(this, Observer {
            if(it == true){
                val resultIntent = Intent()
                resultIntent.putExtra(PARENT_ID, viewModel.parent)
                setResult(Activity.RESULT_OK, resultIntent)
                viewModel.cancelNavigate()
                finish()
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            it?.let{
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
                viewModel.cancelErrorMessage()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finishAndRemoveTask()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_parent -> {
                viewModel.save(root.root)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
