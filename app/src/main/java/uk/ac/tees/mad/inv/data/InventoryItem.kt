package uk.ac.tees.mad.inv.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val documentId : String,
    val name : String,
    val category : String,
    val imageUrl : String,
    val quantity : String,
    val price : String,
    val expiry : String,
)
