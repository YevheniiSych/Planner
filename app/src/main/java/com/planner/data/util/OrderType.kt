package com.planner.data.util

sealed class OrderType {
    data object ASC: OrderType()
    data object DESC: OrderType()

}