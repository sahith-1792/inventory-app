package uk.ac.tees.mad.inv.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.ui.theme.Roboto

@Composable
fun Profile(navController: NavHostController, viewModel: InventoryViewModel) {
    val user = viewModel.userData.value
    Scaffold {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "My account", fontFamily = Roboto, fontSize = 23.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(15.dp))
                if (user!!.profileImage.isNotEmpty()) {
                    AsyncImage(model = user.profileImage, contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {

                            })
                } else {
                    Image(imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {

                            })
                }
                Spacer(modifier = Modifier.height(35.dp))
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)) {
                Text(
                    text = "Personal info", fontFamily = Roboto, fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(15.dp))
                ProfileView(info = "name", toDisplay = user!!.name, icon = Icons.Default.Person) {

                }
                ProfileView(info = "Email address", toDisplay = user!!.email, icon = Icons.Default.Email) {
                    
                }
            }
        }
    }
}

@Composable
fun ProfileView(info : String ,toDisplay : String,icon : ImageVector,onClick:() -> Unit){
    Row {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier
            .size(50.dp)
            .align(Alignment.CenterVertically))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = info, fontFamily = Roboto, fontSize = 14.sp)
            Text(text = toDisplay, fontFamily = Roboto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold,)
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null, modifier = Modifier.clickable { onClick() })
    }
}