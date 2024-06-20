package `in`.catat.di

import `in`.catat.BuildConfig
import `in`.catat.domain.app.service.AppNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by pertadima on 26,September,2020
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.github.com/"

    @Singleton
    @Provides
    fun providesHttpLogInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLogInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) addInterceptor(httpLogInterceptor)
            }
            .build()
    }

    private inline fun <reified T> createRestApiAdapter(
        okHttpClient: OkHttpClient,
        url: String
    ): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(T::class.java)
    }

    @Singleton
    @Provides
    fun providesAppNetworkService(okHttpClient: OkHttpClient): AppNetworkService {
        return createRestApiAdapter(okHttpClient, BASE_URL)
    }
}