package com.planner.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.planner.data.room.category.Category
import com.planner.ui.theme.SelectedCategoryItemColor
import com.planner.ui.theme.UnselectedCategoryItemColor

@Preview
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel()
) {
    CategoriesList(taskCategories = viewModel.state.value.categories)
}

@Composable
fun CategoriesList(taskCategories: List<Category>) {
    var selectedCategoryIndex by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),

        ) {
            itemsIndexed(taskCategories) { index, category ->

                val isSelected = selectedCategoryIndex == index

                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(
                            color = if (isSelected) SelectedCategoryItemColor
                            else UnselectedCategoryItemColor
                        )
                        .padding(horizontal = 15.dp, vertical = 2.dp)
                        .clickable {
                            selectedCategoryIndex = index
                        },
                ) {
                    Text(
                        text = category.title
                    )
                }

            }
        }
    }
}