package uk.ac.tees.mad.inv.View

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.data.InventoryItem

@Composable
fun Detail(item: InventoryItem, viewModel: InventoryViewModel, navController: NavHostController) {
    Text(text = item.name)
}