package uk.ac.tees.mad.inv.View

import android.app.Activity
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import uk.ac.tees.mad.inv.BiometricAuth
import uk.ac.tees.mad.inv.BiometricAuthStatus
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.NavigationComponent
import uk.ac.tees.mad.inv.R
import uk.ac.tees.mad.inv.ui.theme.Roboto

@Composable
fun FingerPrintAuth(
    navController: NavHostController,
    viewModel: InventoryViewModel,
    biometricAuth: BiometricAuth
) {
    val activity = LocalContext.current as FragmentActivity
    var message by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(140.dp))
        Text(
            text = "Inventory App", fontFamily = Roboto, fontWeight = FontWeight.SemiBold,
            fontSize = 35.sp, color = Color(0xFF00483D)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Please enter your fingerprint to continue",color = Color(0xFF00483D),
            fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(80.dp))
        Image(painter = painterResource(id = R.drawable.fingerprint), contentDescription = null )
        Spacer(modifier = Modifier.height(80.dp))
        Text(text = "Fingerprint Scan",color = Color(0xFF00483D), fontSize = 18.sp,
            fontStyle = FontStyle.Italic)
        Button(onClick = {
            biometricAuth.promptBiometricAuth(
                title = "Login",
                description = "Scan your fingerprint to continue",
                negativeButtonText = "Cancel",
                fragmentActivity = activity,
                onSuccess = {
                    message = "Success"
                    navController.navigate(NavigationComponent.LogInScreen.route){
                        popUpTo(0)
                    }
                },
                onFailed = {
                    message = "Failed"
                },
                onError = { _, error ->
                    message = error
                }
            )
        }) {
            Text(text = "Scan")
        }
        Text(text = message)
    }
}
