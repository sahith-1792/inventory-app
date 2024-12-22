package uk.ac.tees.mad.inv

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.inv.data.InventoryDao
import uk.ac.tees.mad.inv.data.InventoryItem
import javax.inject.Inject

class InventoryRepository @Inject constructor(
    private val inventoryDao: InventoryDao
) {
    suspend fun insertItem(item: List<InventoryItem>) {
        inventoryDao.insertItem(item)
    }
    fun getAll(): Flow<List<InventoryItem>>{
        return inventoryDao.getAllItems()
    }
    suspend fun deleteAll(){
        inventoryDao.deleteAllItems()
    }
}