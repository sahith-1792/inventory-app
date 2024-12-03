package uk.ac.tees.mad.inv.View

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.R
import java.io.File

@Composable
fun Add(navController: NavHostController, viewModel: InventoryViewModel) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current

    val takeImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { onSuccess ->
            if (onSuccess) {
            } else {

            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                imageUri = createImageUri(context)
                takeImageLauncher.launch(imageUri)
            } else {

            }
        }

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            // Display the captured image if available
            if (imageUri != null) {
                AsyncImage(model = imageUri, contentDescription = null, modifier = Modifier.fillMaxWidth().height(300.dp).padding(8.dp))
            } else {
                Image(
                    painter = painterResource(id = R.drawable.designer),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(300.dp).padding(8.dp)
                )
            }
            Button(onClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.CAMERA
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    imageUri = createImageUri(context)
                    takeImageLauncher.launch(imageUri)
                } else {
                    cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
            }) {
                Text(text = "Click Image")
            }
        }
    }
}

fun createImageUri(context: Context): Uri? {
    val imageFile = File(
        context.getExternalFilesDir(null),
        "image_${System.currentTimeMillis()}.jpg"
    )
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}