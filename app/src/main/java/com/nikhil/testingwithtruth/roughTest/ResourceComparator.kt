package com.nikhil.testingwithtruth.roughTest

import android.content.Context

class ResourceComparator {

    fun areResourceEqual(context: Context, resId: Int, stringToCheck: String): Boolean {
        return context.getString(resId) == stringToCheck
    }

}