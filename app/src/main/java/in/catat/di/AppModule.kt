package `in`.catat.di

import `in`.catat.data.local.AppDatabase
import `in`.catat.domain.app.datasource.AppLocalDataSource
import `in`.catat.util.tracking.TrackingUtil
import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.co.catatin.core.commons.DiffCallback
import javax.inject.Singleton

/**
 * Created by pertadima on 13,October,2020
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDiffUtilCallback() = DiffCallback()

    @Singleton
    @Provides
    fun provideTrackingUtil() = TrackingUtil()

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "catatin_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesLocalDataBase(appDatabase: AppDatabase) = AppLocalDataSource(appDatabase)
}