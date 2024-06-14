package com.planner.data.util.category

import com.planner.data.util.OrderType

sealed class CategoryOrder(val orderType: OrderType) {
    class DateCreated(orderType: OrderType): CategoryOrder(orderType)

    class Title(orderType: OrderType): CategoryOrder(orderType)
}