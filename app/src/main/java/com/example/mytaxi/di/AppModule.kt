package com.example.mytaxi.di

import com.example.mytaxi.data.room.AppDatabase
import com.example.mytaxi.data.room.LocationDao
import com.example.mytaxi.data.repository.LocationRepositoryImpl
import com.example.mytaxi.domain.useCase.GetLocationUseCase
import com.example.mytaxi.domain.repository.LocationRepository

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideLocationDao(database: AppDatabase): LocationDao {
        return database.locationDao()
    }

    @Provides
    @Singleton
    fun provideLocationRepository(locationDao: LocationDao): LocationRepository {
        return LocationRepositoryImpl(locationDao)
    }

    @Provides
    @Singleton
    fun provideGetLocationUseCase(locationRepository: LocationRepository): GetLocationUseCase {
        return GetLocationUseCase(locationRepository)
    }
}
