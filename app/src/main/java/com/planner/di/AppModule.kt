package com.planner.di

import android.app.Application
import androidx.room.Room
import com.planner.data.repository.CategoryRepository
import com.planner.data.repository.CategoryRepositoryImpl
import com.planner.data.room.PlannerDatabase
import com.planner.data.use_case.category.AddCategoryUseCase
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.use_case.category.DeleteCategoryUseCase
import com.planner.data.use_case.category.GetCategoriesUseCase
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

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            addCategory = AddCategoryUseCase(repository),
            deleteCategory = DeleteCategoryUseCase(repository),
            getCategories = GetCategoriesUseCase(repository)
        )
    }
}