package com.maur025.activitycontrol.rest.module.rest.dto.response.error

class FieldErrorResponse(val propertyPath: String? = null, val message: String? = null) :
    ErrorResponse() {}