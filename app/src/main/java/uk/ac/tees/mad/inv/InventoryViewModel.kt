package uk.ac.tees.mad.inv

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val firestore : FirebaseFirestore,
    private val storage : FirebaseStorage
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val isSignedIn = mutableStateOf(false)

    init {
        if (auth.currentUser!= null){
            isSignedIn.value = true
        }
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

    fun uploadItem(context : Context, image : Uri, name : String, category : String, quantity : String, price : String, expiryDate : String){
        isLoading.value = true
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${image.lastPathSegment}")
        val uploadTask = imageRef.putFile(image)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                firestore.collection("Item").add(hashMapOf(
                    "image" to it.toString(),
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