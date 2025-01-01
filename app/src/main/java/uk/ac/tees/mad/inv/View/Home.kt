package uk.ac.tees.mad.inv.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.NavigationComponent
import uk.ac.tees.mad.inv.data.InventoryItem
import uk.ac.tees.mad.inv.ui.theme.Roboto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, viewModel: InventoryViewModel) {
    val inventoryItem = viewModel.inventoryItems.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Inventory App", fontSize = 30.sp, fontFamily = Roboto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle, contentDescription = "Profile",
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .size(50.dp)
                            .clickable {
                                navController.navigate(NavigationComponent.ProfileScreen.route)
                            }
                    )
                }
            })
        },
        floatingActionButton = {
            Icon(imageVector = Icons.Rounded.Add,
                contentDescription = "Add Items",
                tint = Color.White,
                modifier = Modifier
                    .padding(12.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00483D))
                    .clickable { navController.navigate(NavigationComponent.AddScreen.route) })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text(
                text = "Total Items : ${inventoryItem.value.size}",
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 12.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()) {

            }
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(inventoryItem.value){ item ->
                    ItemCard(item){
                        navController.navigate(NavigationComponent.DetailScreen.createRoute(item))
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(item : InventoryItem, navigateToDetail:() -> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(4.dp)
        .clickable { navigateToDetail() }) {
        AsyncImage(model = item.imageUrl, contentDescription = null, modifier = Modifier.size(80.dp).align(Alignment.CenterVertically),
            contentScale = ContentScale.FillBounds)
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = item.name, fontSize = 20.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
            Text(text = item.category, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
            Text(text = "Available Stock ${item.quantity}",  fontFamily = Roboto, color = Color(0xFF50C7B4))
        }
        Spacer(modifier = Modifier.weight(1f))

    }
}