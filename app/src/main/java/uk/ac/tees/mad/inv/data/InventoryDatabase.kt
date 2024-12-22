package uk.ac.tees.mad.inv.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [InventoryItem::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao() : InventoryDao
}