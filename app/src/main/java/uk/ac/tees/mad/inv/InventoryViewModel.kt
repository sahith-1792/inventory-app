package uk.ac.tees.mad.inv

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.inv.Model.InventoryItemOnline
import uk.ac.tees.mad.inv.data.InventoryDatabase
import uk.ac.tees.mad.inv.data.InventoryItem
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val firestore : FirebaseFirestore,
    private val storage : FirebaseStorage,
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val isSignedIn = mutableStateOf(false)
    private val _inventoryItems = MutableStateFlow<List<InventoryItem>>(emptyList())
    val inventoryItems: StateFlow<List<InventoryItem>> = _inventoryItems

    init {
        if (auth.currentUser!= null){
            isSignedIn.value = true
        }
        retrieveAndStore()
    }

    fun signUp(context : Context, name : String, email : String, password : String){
        isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            firestore.collection("users").document(it.user!!.uid).set(hashMapOf(
                "name" to name,
                "email" to email,
                "password" to password
            )).addOnSuccessListener {
                isSignedIn.value = true
                isLoading.value = false
                Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                isLoading.value = false
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d("TAG", "signUp: ${it.localizedMessage}")
            }
        }.addOnFailureListener {
            isLoading.value = false
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun logIn(context: Context,email: String, password: String){
        isLoading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            isSignedIn.value = true
            isLoading.value = false
            Toast.makeText(context, "Log In Successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            isLoading.value = false
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun retrieveAndStore(){
        isLoading.value = true
        firestore.collection("Item").get().addOnSuccessListener {
            val list = it.toObjects(InventoryItemOnline::class.java)
            Log.d("Item", "item fetched from firestore successfully")
            list.forEach { Log.d("Firestore", "Item: ${it.name}, ImageURL: ${it.imageUrl}") }
            val newList = list.map {
                InventoryItem(
                    documentId = it.documentId,
                    name = it.name,
                    category = it.category,
                    imageUrl = it.imageUrl,
                    quantity = it.quantity,
                    price = it.price,
                    expiry = it.expiry
                )
            }
            storeToDatabase(newList)
            isLoading.value = false
        }.addOnFailureListener {
            isLoading.value = false
            Log.d("Item", it.localizedMessage)
        }
    }

    fun deleteItem(context : Context,itemId : String){
        isLoading.value = true
        firestore.collection("Item").document(itemId).delete().addOnSuccessListener {
            isLoading.value = false
            Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT)
        }.addOnFailureListener {
            isLoading.value = false
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT)
        }
    }

    private fun storeToDatabase(items: List<InventoryItem>) {
        viewModelScope.launch {
            try {
                inventoryRepository.deleteAll()
                inventoryRepository.insertItem(items)
                getFromDatabase()
            } catch (e: Exception) {
                Log.e("Database", "Error storing items: ${e.localizedMessage}")
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun getFromDatabase() {
        viewModelScope.launch {
            inventoryRepository.getAll().collect { entities ->
                _inventoryItems.value = entities
                Log.d("DB", entities.toString())
                Log.d("DB", _inventoryItems.value.toString())
                Log.d("DB", inventoryItems.value.toString())
            }
        }
    }

    fun uploadItem(context : Context, image : Uri, name : String, category : String, quantity : String, price : String, expiryDate : String){
        isLoading.value = true
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${image.lastPathSegment}")
        val uploadTask = imageRef.putFile(image)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                firestore.collection("Item").add(hashMapOf(
                    "imageUrl" to it.toString(),
                    "name" to name,
                    "category" to category,
                    "quantity" to quantity,
                    "price" to price,
                    "expiry" to expiryDate)
                ).addOnSuccessListener {
                    val documentId = it.id
                    firestore.collection("Item").document(documentId).update(
                        "document", documentId
                    )
                    isLoading.value = false
                    Toast.makeText(context, "Item Uploaded", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    isLoading.value = false
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }.addOnFailureListener {
            isLoading.value = false
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}