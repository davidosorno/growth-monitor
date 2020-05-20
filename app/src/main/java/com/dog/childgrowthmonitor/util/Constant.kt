package com.dog.childgrowthmonitor.util

import android.content.Context
import android.view.View
import java.util.concurrent.Executors

const val DATABASE_NAME = "AnthroPlusDataBase-db"

const val CHILD_ID = "idChild"
const val PARENT_ID = "idParent"
const val SEARCH_IN = "searchIn"
const val FILTER_PARENT_BY = "filterParentBy" //Father or Mother
const val MALE = 1
const val FEMALE = 2
const val NEW_VISIT = "new_visit"
const val ADD_PARENTS = "add_parents"
const val VISIBLE = View.VISIBLE
const val INVISIBLE = View.INVISIBLE
const val GONE = View.GONE
const val OPTIONAL_ID = "optional_id"
const val MIN_WEIGHT = 0.9
const val MIN_HEIGHT = 38.0
const val MAX_WEIGHT = 275.0
const val MAX_HEIGHT = 230.0


var GLOBAL_CONTEXT: Context? = null // Lo utilizo solo para Debug

private val SINGLE_EXECUTOR = Executors.newSingleThreadExecutor()

fun executeThread(f: () -> Unit){
    SINGLE_EXECUTOR.execute(f)
}