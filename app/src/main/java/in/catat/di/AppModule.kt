package `in`.catat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.co.catatin.core.commons.DiffCallback
import javax.inject.Singleton

/**
 * Created by pertadima on 13,October,2020
 */

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDiffUtilCallback() = DiffCallback()
}