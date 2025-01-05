package uk.ac.tees.mad.inv.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uk.ac.tees.mad.inv.InventoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(viewModel : InventoryViewModel, navController : NavController) {
    val context = LocalContext.current
    val user = viewModel.userData
    val name = remember { mutableStateOf(user.value!!.name) }
    val email = remember { mutableStateOf(user.value!!.email) }
    Scaffold(topBar = { TopAppBar(title = {
        Row {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription =null,modifier = Modifier
                .size(40.dp)
                .clickable {
                    navController.popBackStack()
                } )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Edit Profile", modifier = Modifier.align(Alignment.CenterVertically))
        }
    }) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(text = "Name")
                OutlinedTextField(value = name.value, onValueChange = { name.value = it })
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column {
                Text(text = "Email")
                OutlinedTextField(value = email.value, onValueChange = { email.value = it })
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                viewModel.updateUser(
                    context = context,
                    name = name.value,
                    email = email.value
                )
            }, modifier = Modifier
                .width(250.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF00483D))
            ) {
                Text(text = "Save")
            }
        }
    }
}