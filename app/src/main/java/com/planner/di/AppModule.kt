package com.planner.di

import android.app.Application
import androidx.room.Room
import com.planner.data.repository.CategoryRepository
import com.planner.data.repository.CategoryRepositoryImpl
import com.planner.data.room.PlannerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlannerDatabase(app: Application): PlannerDatabase {
        return  Room.databaseBuilder(
            app,
            PlannerDatabase::class.java,
            PlannerDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: PlannerDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao)
    }
}