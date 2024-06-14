package com.planner.ui.tasks

import androidx.lifecycle.ViewModel
import com.planner.data.use_case.category.CategoryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

}