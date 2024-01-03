package com.acioli.areas.di

import android.app.Application
import androidx.room.Dao
import androidx.room.Room
import com.acioli.areas.areas_service.data.local.entity.AreasDatabase
import com.acioli.areas.areas_service.data.remote.Api
import com.acioli.areas.areas_service.data.repository.AreasRepositoryImpl
import com.acioli.areas.areas_service.domain.repository.AreasRepository
import com.acioli.areas.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideApi(): Api {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: Api, db: AreasDatabase): AreasRepository {
        return AreasRepositoryImpl(api = api, dao = db.dao)
    }

    @Provides
    @Singleton
    fun provideAreasDatabase(app: Application): AreasDatabase {
        return Room.databaseBuilder(
            app,AreasDatabase::class.java, AreasDatabase.DATABASE_NAME
        ).build()
    }

}