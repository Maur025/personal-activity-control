package com.maur025.activitycontrol.common.module.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging

val log = KotlinLogging.logger { }

fun <T> toString(objectMapper: ObjectMapper, objectGen: T?): String? {
    if (objectGen != null) {
        try {
            return objectMapper.writeValueAsString(objectGen)
        } catch (ex: JsonProcessingException) {
            log.error(ex) { "FAILED TO CONVERT TO STRING" }
        }
    }

    return null;
}