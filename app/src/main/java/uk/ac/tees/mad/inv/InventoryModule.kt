package uk.ac.tees.mad.inv

import android.content.Context
import androidx.room.Room
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.inv.data.InventoryDao
import uk.ac.tees.mad.inv.data.InventoryDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object InventoryModule {

    @Provides
    fun ProvidesAuthentication() : FirebaseAuth = Firebase.auth

    @Provides
    fun ProvidesFirestore() : FirebaseFirestore = Firebase.firestore

    @Provides
    fun ProvidesStorage() : FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): InventoryDatabase {
        return Room.databaseBuilder(
            appContext,
            InventoryDatabase::class.java,
            "my_database"
        ).build()
    }

    @Provides
    fun provideMyDao(db: InventoryDatabase): InventoryDao {
        return db.inventoryDao()
    }
}