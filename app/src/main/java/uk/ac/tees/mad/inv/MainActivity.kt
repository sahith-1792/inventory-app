package uk.ac.tees.mad.inv

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import uk.ac.tees.mad.inv.View.Add
import uk.ac.tees.mad.inv.View.Detail
import uk.ac.tees.mad.inv.View.Edit
import uk.ac.tees.mad.inv.View.FingerPrintAuth
import uk.ac.tees.mad.inv.View.Home
import uk.ac.tees.mad.inv.View.LogIn
import uk.ac.tees.mad.inv.View.Profile
import uk.ac.tees.mad.inv.View.SignUp
import uk.ac.tees.mad.inv.View.Splash
import uk.ac.tees.mad.inv.data.InventoryItem
import uk.ac.tees.mad.inv.ui.theme.InventoryAppTheme
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
    object DetailScreen : NavigationComponent("detail_screen/{item}"){
        fun createRoute(item: InventoryItem): String {
            val json = Gson().toJson(item)
            val encodedItem = URLEncoder.encode(json, StandardCharsets.UTF_8.toString()).replace("+", "%20")
            return "detail_screen/$encodedItem"
        }
    }
    object EditScreen : NavigationComponent("edit_screen/{item}"){
        fun createRoute(item: InventoryItem): String {
            val json = Gson().toJson(item)
            val encodedItem = URLEncoder.encode(json, StandardCharsets.UTF_8.toString()).replace("+", "%20")
            return "edit_screen/$encodedItem"
        }
    }
    object ProfileScreen : NavigationComponent("profile_screen")
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
            composable(
                route = NavigationComponent.DetailScreen.route,
                arguments = listOf(navArgument("item") { type = NavType.StringType })
            ) { backStackEntry ->
                val itemJson = backStackEntry.arguments?.getString("item")
                val item = Gson().fromJson(itemJson, InventoryItem::class.java)
                Detail(navController = navController, viewModel = viewModel, item = item)
            }
            composable(route = NavigationComponent.EditScreen.route, arguments = listOf(navArgument("item") { type = NavType.StringType }))
            { backStackEntry ->
                val itemJson = backStackEntry.arguments?.getString("item")
                val item = Gson().fromJson(itemJson, InventoryItem::class.java)
                Edit(navController = navController, viewModel = viewModel, item = item)
            }
            composable(route = NavigationComponent.ProfileScreen.route){
                Profile(navController = navController, viewModel = viewModel)
            }
        }
    }
}
