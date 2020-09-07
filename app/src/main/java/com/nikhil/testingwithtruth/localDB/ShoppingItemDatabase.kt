package com.nikhil.testingwithtruth.localDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItemTable::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}