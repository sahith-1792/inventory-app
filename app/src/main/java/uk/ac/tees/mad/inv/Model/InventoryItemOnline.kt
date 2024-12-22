package uk.ac.tees.mad.inv.Model

data class InventoryItemOnline(
    val documentId: String = "",
    val name: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val quantity: String = "",
    val price: String = "",
    val expiry: String = "",
)
