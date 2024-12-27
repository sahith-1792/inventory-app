package uk.ac.tees.mad.inv.View

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
fun Detail(item: InventoryItem, viewModel: InventoryViewModel, navController: NavHostController) {
    val context = LocalContext.current
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
                fontSize = 30.sp
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp)) {
                Text(text = "Item Description", fontSize = 22.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Item Price", fontSize = 18.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = " : ", fontSize = 18.sp, fontFamily = Roboto, modifier = Modifier.weight(1f))
                    Text(text = item.price, fontSize = 18.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Item Category", fontSize = 18.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = " : ", fontSize = 18.sp, fontFamily = Roboto, modifier = Modifier.weight(1f))
                    Text(text = item.category, fontSize = 18.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Item Quantity", fontSize = 18.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = " : ", fontSize = 18.sp, fontFamily = Roboto, modifier = Modifier.weight(1f))
                    Text(text = item.quantity, fontSize = 18.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Item Expiry", fontSize = 18.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = " : ", fontSize = 18.sp, fontFamily = Roboto, modifier = Modifier.weight(1f))
                    Text(text = item.expiry, fontSize = 18.sp, fontFamily = Roboto, fontWeight = FontWeight.SemiBold)
                }
                Row(Modifier.fillMaxWidth()) {
                    Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(Color(0xFF00483D))) {
                        Text(text = "Edit Item")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        Log.d("TAG", "Detail: ${item.documentId}")
                        viewModel.deleteItem(context,item.documentId)
                                     navController.popBackStack()
                                     }, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Red)) {
                        Text(text = "Delete Item")
                    }
                }
            }
        }
    }
}