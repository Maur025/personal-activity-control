package com.maur025.activitycontrol.security.module.security

import com.nimbusds.jose.util.StandardCharset
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.server.resource.BearerTokenError
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component("reactiveServerAuthenticationEntryPoint")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
class ReactiveServerAuthenticationEntryPoint : ServerAuthenticationEntryPoint {

    override fun commence(
        exchange: ServerWebExchange, authException: AuthenticationException
    ): Mono<Void> = Mono.defer {
        val status: HttpStatus = this.getStatus(authException)
        val parameters: Map<String, String> = this.createParameters(authException)
        val wwwAuthenticate: String = computeWWWAuthenticateHeaderValue(parameters)

        val response: ServerHttpResponse = exchange.response
        response.headers.set("WWW-Authenticate", wwwAuthenticate)
        response.statusCode = status

        val bytes: ByteArray =
            "{\"code\":401,\"message\":\"UNAUTHORIZED\"}".toByteArray(StandardCharset.UTF_8)
        val buffer: DataBuffer = exchange.response.bufferFactory().wrap(bytes)
        response.writeWith { Flux.just(buffer) }

        response.setComplete()
    }

    private fun getStatus(authenticationException: AuthenticationException): HttpStatus {
        if (authenticationException is OAuth2AuthenticationException) {
            val error: OAuth2Error = authenticationException.error

            if (error is BearerTokenError) {
                return error.httpStatus
            }
        }

        return HttpStatus.UNAUTHORIZED
    }

    private fun createParameters(authenticationException: AuthenticationException): Map<String, String> {
        val parameters = linkedMapOf<String, String>()

        if (authenticationException is OAuth2AuthenticationException) {
            val error: OAuth2Error = authenticationException.error
            parameters["error"] = error.errorCode

            if (StringUtils.hasText(error.description)) {
                parameters["error_description"] = error.description
            }

            if (StringUtils.hasText(error.uri)) {
                parameters["error_uri"] = error.uri
            }

            if (error is BearerTokenError) {
                if (StringUtils.hasText(error.scope)) {
                    parameters["scope"] = error.scope
                }
            }
        }

        return parameters
    }

    private fun computeWWWAuthenticateHeaderValue(parameters: Map<String, String>): String {
        val wwwAuthenticate = StringBuilder()
        wwwAuthenticate.append("Bearer")

        if (!parameters.isEmpty()) {
            wwwAuthenticate.append(" ")

            for ((i, entry) in parameters.entries.withIndex()) {
                wwwAuthenticate.append(entry.key).append("=\"").append(entry.value).append("\"")

                if (i != parameters.size - 1) {
                    wwwAuthenticate.append(", ")
                }
            }
        }

        return wwwAuthenticate.toString()
    }
}