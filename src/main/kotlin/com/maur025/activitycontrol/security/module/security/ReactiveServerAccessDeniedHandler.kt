package com.maur025.activitycontrol.security.module.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.security.Principal

@Component("reactiveServerAccessDeniedHandler")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
class ReactiveServerAccessDeniedHandler : ServerAccessDeniedHandler {
    var realName: String? = null

    override fun handle(
        exchange: ServerWebExchange, denied: AccessDeniedException
    ): Mono<Void> {
        val parameters = linkedMapOf<String, String>()

        this.realName?.let { parameters["realName"] = it }

        return exchange.getPrincipal<Principal>()
            .filter { it is AbstractOAuth2TokenAuthenticationToken<*> }
            .map { _ -> errorMessageParameters(parameters) }.switchIfEmpty(Mono.just(parameters))
            .flatMap { params -> respond(exchange, params) }
    }

    private fun errorMessageParameters(parameters: MutableMap<String, String>): Map<String, String> {
        parameters["error"] = "insufficient_scope"
        parameters["error_description"] =
            "The request requires higher privileges than provided by the access token."
        parameters["error_uri"] = "https://tools.ietf.org/html/rfc6750#section-3.1"

        return parameters
    }

    private fun respond(exchange: ServerWebExchange, parameters: Map<String, String>): Mono<Void> {
        val wwwAuthenticate: String = computeWWWAuthenticateHeaderValue(parameters)
        exchange.response.statusCode = HttpStatus.FORBIDDEN
        exchange.response.headers.set("WWW-Authenticate", wwwAuthenticate)

        return exchange.response.setComplete()
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