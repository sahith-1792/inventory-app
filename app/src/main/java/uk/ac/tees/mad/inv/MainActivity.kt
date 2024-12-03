package uk.ac.tees.mad.inv

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.inv.View.Add
import uk.ac.tees.mad.inv.View.FingerPrintAuth
import uk.ac.tees.mad.inv.View.Home
import uk.ac.tees.mad.inv.View.LogIn
import uk.ac.tees.mad.inv.View.SignUp
import uk.ac.tees.mad.inv.View.Splash
import uk.ac.tees.mad.inv.ui.theme.InventoryAppTheme

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biometricAuth = BiometricAuth(this)
        enableEdgeToEdge()
        setContent {
            InventoryAppTheme {
                NavigationInApp(biometricAuth)
            }
        }
    }
}


sealed class NavigationComponent(val route:String){
    object SplashScreen:NavigationComponent("splash_screen")
    object LogInScreen : NavigationComponent("log_in_screen")
    object SignUpScreen : NavigationComponent("sign_up_screen")
    object FingerPrintScreen : NavigationComponent("finger_print_screen")
    object HomeScreen : NavigationComponent("home_screen")
    object AddScreen : NavigationComponent("add_screen")
}


@Composable
fun NavigationInApp(biometricAuth: BiometricAuth) {
    val navController = rememberNavController()
    val viewModel : InventoryViewModel = viewModel()
    Surface {
        NavHost(
            navController = navController,
            startDestination = NavigationComponent.SplashScreen.route
        ) {
            composable(route = NavigationComponent.SplashScreen.route) {
                Splash(navController = navController, viewModel = viewModel)
            }
            composable(route = NavigationComponent.LogInScreen.route){
                LogIn(navController = navController, viewModel = viewModel)
            }
            composable(route = NavigationComponent.SignUpScreen.route){
                SignUp(navController = navController, viewModel = viewModel)
            }
            composable(route = NavigationComponent.FingerPrintScreen.route){
                FingerPrintAuth(navController = navController, viewModel = viewModel, biometricAuth)
            }
            composable(route = NavigationComponent.HomeScreen.route){
                Home(navController = navController, viewModel = viewModel)
            }
            composable(route = NavigationComponent.AddScreen.route){
                Add(navController = navController, viewModel = viewModel)
            }
        }
    }
}
