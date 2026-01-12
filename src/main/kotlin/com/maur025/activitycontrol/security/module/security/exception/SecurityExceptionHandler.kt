package com.maur025.activitycontrol.security.module.security.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.maur025.activitycontrol.common.module.util.toString
import com.maur025.activitycontrol.common.module.validation.getOrDefault
import com.maur025.activitycontrol.rest.module.rest.dto.response.ExceptionResponse
import com.maur025.activitycontrol.rest.module.rest.dto.response.error.FieldErrorResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.*
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

private val log = KotlinLogging.logger { }

@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class SecurityExceptionHandler(private val objectMapper: ObjectMapper) :
    ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [AuthenticationException::class])
    fun handleBaseException(ex: java.lang.Exception, request: WebRequest): ResponseEntity<Any>? {
        val headers = HttpHeaders()

        if (ex is AuthenticationException) {
            return this.handleAuth(
                ex,
                headers,
                if (ex is InsufficientAuthenticationException) HttpStatus.UNAUTHORIZED else HttpStatus.FORBIDDEN,
                request
            )
        }

        return handleExceptionInternal(ex, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, request)
    }

    fun handleAuth(
        ex: AuthenticationException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? = this.handleExceptionInternal(
        ex, null, headers, getOrDefault(
            status, HttpStatus.FORBIDDEN
        ), request
    )

    protected override fun handleExceptionInternal(
        ex: java.lang.Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        if (request is ServletWebRequest) {
            val response: HttpServletResponse? = request.response

            if (response != null && response.isCommitted) {
                if (this.logger.isWarnEnabled) {
                    this.logger.warn("Response already committed. Ignoring: $ex")
                }

                return null;
            }
        }

        val detail = when (ex) {
            is ErrorResponse -> {
                headers.contentType = MediaType.APPLICATION_JSON
                ex.updateAndGetBody(messageSource, LocaleContextHolder.getLocale()).detail
            }

            else -> ex.message
        }

        val responseBody = ExceptionResponse<FieldErrorResponse>(
            code = statusCode.value(), message = getMessage(statusCode), detail = detail
        )

        log.error(ex) {
            toString(
                objectMapper, responseBody
            )
        }

        return this.createResponseEntity(responseBody, headers, statusCode, request)
    }

    private fun getMessage(statusCode: HttpStatusCode): String =
        when (val httpStatus: HttpStatus = HttpStatus.valueOf(statusCode.value())) {
            HttpStatus.BAD_REQUEST -> "REQUEST_VALIDATION_FAILED"
            HttpStatus.NOT_FOUND -> "RESOURCE_NOT_FOUND"
            HttpStatus.CONFLICT -> "RESOURCE_EXISTS"
            HttpStatus.UNAUTHORIZED -> "NOT_AUTHORIZED"
            HttpStatus.FORBIDDEN -> "ACCESS_DENIED"
            else -> httpStatus.name
        }
}