package com.nikhil.testingwithtruth.localDB

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nikhil.testingwithtruth.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    private lateinit var shoppingItemDatabase: ShoppingItemDatabase

    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setup() {
        shoppingItemDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        shoppingDao = shoppingItemDatabase.shoppingDao()
    }

    @After
    fun Teardown() {
        shoppingItemDatabase.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItemTable = ShoppingItemTable("Zoro", 1, 65000f, "dummyURL", id = 1)
        shoppingDao.insertShoppingItem(shoppingItemTable)

        val allShoppingItems = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItemTable)
    }

}