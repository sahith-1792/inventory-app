package uk.ac.tees.mad.inv.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("SELECT * FROM InventoryItem")
    fun getAllItems() : Flow<List<InventoryItem>>

    @Insert
    suspend fun insertItem(item : List<InventoryItem>)

    @Query("DELETE FROM InventoryItem")
    suspend fun deleteAllItems()


}