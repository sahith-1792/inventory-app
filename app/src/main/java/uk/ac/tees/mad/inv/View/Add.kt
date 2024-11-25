package uk.ac.tees.mad.inv.View

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import uk.ac.tees.mad.inv.InventoryViewModel
import java.io.File

@Composable
fun Add(navController: NavHostController, viewModel: InventoryViewModel) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val takeImageLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
        onSuccess ->
        if (onSuccess){
            imageUri?.let {

            }
        }
    }

    val imageFile = File(
        context.getExternalFilesDir(null),
        "image_${System.currentTimeMillis()}.jpg"
    )
    val photoUri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
    Scaffold {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}