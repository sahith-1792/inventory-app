package uk.ac.tees.mad.inv.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.NavigationComponent
import uk.ac.tees.mad.inv.data.InventoryItem
import uk.ac.tees.mad.inv.ui.theme.Roboto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(item: InventoryItem, viewModel: InventoryViewModel, navController: NavHostController) {
    Scaffold(
        topBar = {TopAppBar(title = {
            Row(Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            navController.navigate(NavigationComponent.HomeScreen.route) {
                                popUpTo(0)
                            }
                        })
                Text(text = "Item Detail", modifier = Modifier.padding(start = 30.dp))
            }
        })}
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Detail")
            AsyncImage(model = item.imageUrl, contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .height(400.dp)
                .clip(
                    RoundedCornerShape(20.dp)
                ),
                contentScale = ContentScale.FillBounds)

            Text(
                text = item.name,
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )
        }
    }
}