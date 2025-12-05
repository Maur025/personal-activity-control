package com.maur025.activitycontrol.security.module.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.server.resource.BearerTokenError
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component("reactiveServerAuthenticationEntryPoint")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
class ReactiveServerAuthenticationEntryPoint : ServerAuthenticationEntryPoint {

    override fun commence(
        exchange: ServerWebExchange, authException: AuthenticationException
    ): Mono = Mono.defer {
        val status = this.getStatus(authException)
        val parameters = create

        return
    }

    private fun getStatus(authenticationException: AuthenticationException): HttpStatus {
        if (authenticationException is OAuth2AuthenticationException) {
            val error = authenticationException.error

            if (error is BearerTokenError) {
                return error.httpStatus
            }
        }

        return HttpStatus.UNAUTHORIZED
    }

    private fun createParameters(authenticationException: AuthenticationException): Map<String, String> {
        val parameters = Map
    }
}