package com.nikhil.testingwithtruth.localDB

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

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

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItemTable("Zoro", 1, 65000f, "dummyURL", id = 1)
        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = shoppingDao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPrice() = runBlockingTest {
        val shoppingItem1 = ShoppingItemTable("Zoro", 1, 65000f, "dummyURL", id = 1)
        val shoppingItem2 = ShoppingItemTable("Zoro", 2, 75000f, "dummyURL", id = 2)
        val shoppingItem3 = ShoppingItemTable("Zoro", 3, 5000f, "dummyURL", id = 3)

        shoppingDao.insertShoppingItem(shoppingItem1)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val totalAmount = shoppingDao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalAmount).isEqualTo(1 * 65000f + 2 * 75000f + 3 * 5000f)

    }

}