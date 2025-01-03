package uk.ac.tees.mad.inv.View

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import uk.ac.tees.mad.inv.InventoryViewModel
import uk.ac.tees.mad.inv.NavigationComponent
import uk.ac.tees.mad.inv.data.InventoryItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Edit(navController: NavHostController, viewModel: InventoryViewModel, item: InventoryItem) {
    val isLoading = viewModel.isLoading
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var imageUri2 by remember {
        mutableStateOf(item.imageUrl)
    }

    val context = LocalContext.current

    var itemName by remember {
        mutableStateOf(item.name)
    }
    var category by remember {
        mutableStateOf(item.category)
    }
    var quantity by remember {
        mutableStateOf(item.quantity)
    }
    var price by remember {
        mutableStateOf(item.price)
    }
    var expiryDate by remember {
        mutableStateOf(item.expiry)
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

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = null,
                        modifier = Modifier.size(30.dp).clickable {
                            navController.navigate(NavigationComponent.HomeScreen.route){
                                popUpTo(0)
                            }
                        })
                    Text(text = "Edit Item", modifier = Modifier.padding(start = 30.dp))
                }
            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Image(
                painter = rememberImagePainter(data = imageUri2),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(8.dp)
            )

            OutlinedTextField(value = itemName, onValueChange ={itemName = it}, modifier = Modifier.align(
                Alignment.CenterHorizontally), label = { Text(
                text = "Item Name"
            )
            }
            )
            OutlinedTextField(value = category, onValueChange ={category = it}, modifier = Modifier.align(
                Alignment.CenterHorizontally),label = { Text(
                text = "Category"
            )
            }
            )
            OutlinedTextField(value = quantity, onValueChange ={quantity = it}, modifier = Modifier.align(
                Alignment.CenterHorizontally),label = { Text(
                text = "Quantity"
            )
            }
            )
            OutlinedTextField(value = price, onValueChange ={price = it}, modifier = Modifier.align(
                Alignment.CenterHorizontally),label = { Text(
                text = "Price"
            )
            }
            )
            OutlinedTextField(value = expiryDate, onValueChange ={expiryDate = it}, modifier = Modifier.align(
                Alignment.CenterHorizontally),
                label = { Text(
                    text = "Expiry Date"
                )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                    viewModel.editItem(
                        context,
                        item.documentId,
                        itemName,
                        category,
                        quantity,
                        price,
                        expiryDate
                    )
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



//fun createImageUri(context: Context): Uri? {
//    val imageFile = File(
//        context.getExternalFilesDir(null),
//        "image_${System.currentTimeMillis()}.jpg"
//    )
//    return FileProvider.getUriForFile(
//        context,
//        "${context.packageName}.provider",
//        imageFile
//    )
//}