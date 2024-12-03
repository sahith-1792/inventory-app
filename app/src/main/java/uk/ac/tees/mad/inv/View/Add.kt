package uk.ac.tees.mad.inv.View

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.R
import java.io.File

@Composable
fun Add(navController: NavHostController, viewModel: InventoryViewModel) {
    val isLoading = viewModel.isLoading
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var imageUri2 by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var itemName by remember {
        mutableStateOf("")
    }
    var category by remember {
        mutableStateOf("")
    }
    var quantity by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    var expiryDate by remember {
        mutableStateOf("")
    }

    val takeImageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { onSuccess ->
            if (onSuccess) {
                Log.d("TAG", "Add: $imageUri")
                imageUri2 = imageUri.toString()
            } else {
                Log.d("TAG", "Add: Failed")
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
            Image(
            painter = rememberImagePainter(data = imageUri2),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(8.dp)
        )
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
            }, modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF00483D))) {
                Text(text = "Click Image")
            }
            OutlinedTextField(value = itemName, onValueChange ={itemName = it}, modifier = Modifier.align(Alignment.CenterHorizontally), label = { Text(
                text = "Item Name"
            )}
            )
            OutlinedTextField(value = category, onValueChange ={category = it}, modifier = Modifier.align(Alignment.CenterHorizontally),label = { Text(
                text = "Category"
            )}
            )
            OutlinedTextField(value = quantity, onValueChange ={quantity = it}, modifier = Modifier.align(Alignment.CenterHorizontally),label = { Text(
                text = "Quantity"
            )}
            )
            OutlinedTextField(value = price, onValueChange ={price = it}, modifier = Modifier.align(Alignment.CenterHorizontally),label = { Text(
                text = "Price"
            )}
            )
            OutlinedTextField(value = expiryDate, onValueChange ={expiryDate = it}, modifier = Modifier.align(Alignment.CenterHorizontally),
                label = { Text(
                    text = "Expiry Date"
                )}
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                if (imageUri !=null){
                viewModel.uploadItem(
                context,
                imageUri!!,
                itemName,
                category,
                quantity,
                price,
                expiryDate
            ) }
                             },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF00483D))) {
                if (isLoading.value) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Save")
                }
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