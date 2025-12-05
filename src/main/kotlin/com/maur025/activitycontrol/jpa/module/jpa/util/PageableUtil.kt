package com.maur025.activitycontrol.jpa.module.jpa.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

fun getSortDirection(descending: Boolean?): Sort.Direction =
    if (descending != null && descending) Sort.Direction.DESC else Sort.Direction.ASC

fun getSort(sortBy: String?, descending: Boolean?): Sort.Order? =
    if (sortBy != null) Sort.Order.by(sortBy).with(getSortDirection(descending)) else null

fun of(page: Int?, size: Int?, sortBy: String?, descending: Boolean?): Pageable {
    val orders = mutableListOf<Sort.Order?>()
    orders.add(getSort(sortBy, descending))

    val nonNullOrders = orders.filterNotNull()
    return of(page, size, nonNullOrders)
}

fun of(page: Int?, size: Int?, sortBy: Map<String, Boolean>): Pageable {
    val orders = sortBy.map { (field, descending) -> getSort(field, descending) }.filterNotNull()

    return of(page, size, orders)
}

fun of(page: Int?, size: Int?, orders: List<Sort.Order>): Pageable {
    val sort = Sort.by(orders)

    return if (page == null || size == null) PageRequest.of(
        0, Int.MAX_VALUE, sort
    ) else PageRequest.of(page, size, sort)
}