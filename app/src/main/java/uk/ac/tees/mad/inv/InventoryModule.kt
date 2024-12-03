package uk.ac.tees.mad.inv

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object InventoryModule {

    @Provides
    fun ProvidesAuthentication() : FirebaseAuth = Firebase.auth

    @Provides
    fun ProvidesFirestore() : FirebaseFirestore = Firebase.firestore

    @Provides
    fun ProvidesStorage() : FirebaseStorage = Firebase.storage
}