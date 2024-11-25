package uk.ac.tees.mad.inv

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val auth : FirebaseAuth,
    private val firestore : FirebaseFirestore
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
}