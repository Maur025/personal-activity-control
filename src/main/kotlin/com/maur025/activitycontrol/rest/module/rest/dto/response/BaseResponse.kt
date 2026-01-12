package com.maur025.activitycontrol.rest.module.rest.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class BaseResponse(
    val code: Int? = null, val message: String? = null
) {
    val displayMessage: String?
        get() = if (message == null && code != null && !isError) "SUCCESS" else message

    private val isError: Boolean
        get() {
            val hundreds: Int = code?.let { it / 100 } ?: 5
            return hundreds == 4 || hundreds == 5
        }
}