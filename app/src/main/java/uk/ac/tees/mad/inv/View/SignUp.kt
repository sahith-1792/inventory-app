package uk.ac.tees.mad.inv.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.NavigationComponent
import uk.ac.tees.mad.inv.R
import uk.ac.tees.mad.inv.ui.theme.Roboto

@Composable
fun SignUp(navController: NavHostController, viewModel: InventoryViewModel) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isSignedIn = viewModel.isSignedIn

    if (isSignedIn.value){
        navController.navigate(NavigationComponent.HomeScreen.route){
            popUpTo(0)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.designer), contentDescription = null,
                modifier = Modifier
                    .padding(top = 60.dp)
                    .size(130.dp)
            )
            Text(
                text = "Sign Up to Inventory App",
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            Text(
                text = "Name",
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = name, onValueChange = { name = it }, modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                text = "Email",
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = email, onValueChange = { email = it }, modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                text = "Password",
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = password, onValueChange = { password = it }, modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Button(
                onClick = { viewModel.signUp(context, name, email, password) }, modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(8.dp), shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF00483D))
            ) {
                Text(text = "Sign Up", fontFamily = Roboto)
            }
            Text(text = "Already an user? Sign In", modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navController.navigate(NavigationComponent.SignUpScreen.route){
                        popUpTo(0)
                    }
                },fontFamily = Roboto,
                fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Inventory App",
                color = Color(0xFF00483D),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 70.dp)
            )
        }
}
}