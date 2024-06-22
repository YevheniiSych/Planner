package com.planner.data.use_case.category

data class CategoryUseCases(
    val addCategory: AddCategoryUseCase,
    val deleteCategory: DeleteCategoryUseCase,
    val getCategories: GetCategoriesUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase
)
