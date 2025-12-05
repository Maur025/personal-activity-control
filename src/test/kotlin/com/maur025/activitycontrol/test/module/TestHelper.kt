package com.maur025.activitycontrol.test.module

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

object TestHelper {
    val data: ConcurrentMap<String, Any> = ConcurrentHashMap()

    fun setValue(key: String, value: Any): Any? = data.put(key, value)

    fun getValue(key: String): Any? = data[key]
}