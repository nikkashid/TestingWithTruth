package com.nikhil.testingwithtruth.roughTest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.nikhil.testingwithtruth.R
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparatorTest {

    private lateinit var resourceComparator: ResourceComparator

    @Before
    fun setUp() {
        resourceComparator = ResourceComparator()
    }

    @After
    fun tearDown() {
        //will execute after the test completion
    }

    @Test
    fun success_ifStringsAreEqual() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result =
            resourceComparator.areResourceEqual(context, R.string.app_name, "TestingWithTruth")
        assertThat(result).isTrue()
    }

    @Test
    fun success_ifStringsAreNotEqual() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result =
            resourceComparator.areResourceEqual(context, R.string.app_name, "TestingString")
        assertThat(result).isFalse()
    }

}