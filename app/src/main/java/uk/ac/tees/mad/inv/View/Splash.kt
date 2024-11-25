package uk.ac.tees.mad.inv.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.NavigationComponent
import uk.ac.tees.mad.inv.R

@Composable
fun Splash(navController: NavController, viewModel : InventoryViewModel) {
    LaunchedEffect(key1 = true) {
        delay(2500L)
        navController.navigate(NavigationComponent.LogInScreen.route)
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.designer), contentDescription = "app_logo",
            modifier = Modifier.size(230.dp))
    }
}