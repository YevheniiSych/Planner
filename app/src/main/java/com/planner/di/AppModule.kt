package com.planner.di

import android.app.Application
import androidx.room.Room
import com.planner.data.repository.CategoryRepository
import com.planner.data.repository.TaskRepository
import com.planner.data.repository.createCategoryRepository
import com.planner.data.repository.createTaskRepository
import com.planner.data.room.PlannerDatabase
import com.planner.data.use_case.category.AddCategoryUseCase
import com.planner.data.use_case.category.CategoryUseCases
import com.planner.data.use_case.category.DeleteCategoryUseCase
import com.planner.data.use_case.category.GetCategoriesUseCase
import com.planner.data.use_case.category.UpdateCategoryUseCase
import com.planner.data.use_case.task.AddTaskUseCase
import com.planner.data.use_case.task.DeleteTaskUseCase
import com.planner.data.use_case.task.GetSingleTaskUseCase
import com.planner.data.use_case.task.GetTasksUseCase
import com.planner.data.use_case.task.TaskUseCases
import com.planner.data.use_case.task.UpdateTaskUseCase
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
        return  Room.databaseBuilder(app, PlannerDatabase::class.java, PlannerDatabase.DATABASE_NAME)
//            .createFromAsset("database/planner.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: PlannerDatabase): CategoryRepository {
        return createCategoryRepository(db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            addCategory = AddCategoryUseCase(repository),
            deleteCategory = DeleteCategoryUseCase(repository),
            getCategories = GetCategoriesUseCase(repository),
            updateCategoryUseCase = UpdateCategoryUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: PlannerDatabase): TaskRepository {
        return createTaskRepository(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCases {
        return TaskUseCases(
            addTaskUseCase = AddTaskUseCase(repository),
            deleteTaskUseCase = DeleteTaskUseCase(repository),
            updateTaskUseCase = UpdateTaskUseCase(repository),
            getTasksUseCase = GetTasksUseCase(repository),
            getSingleTaskUseCase = GetSingleTaskUseCase(repository)
        )
    }
}