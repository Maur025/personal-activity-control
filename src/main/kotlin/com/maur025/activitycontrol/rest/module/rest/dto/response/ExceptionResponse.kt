package com.maur025.activitycontrol.rest.module.rest.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.maur025.activitycontrol.rest.module.rest.dto.response.error.ErrorResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
class ExceptionResponse<T : ErrorResponse>(
    code: Int? = null,
    message: String? = null,
    val detail: String? = null,
    val data: List<T>? = null
) : BaseResponse(code = code, message = message) {

}