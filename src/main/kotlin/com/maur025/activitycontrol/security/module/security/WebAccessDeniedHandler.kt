package com.maur025.activitycontrol.security.module.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component("webAccessDeniedHandler")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class WebAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val parameters = linkedMapOf<String, String>()

        if (request.userPrincipal is AbstractOAuth2TokenAuthenticationToken<*>) {
            parameters["error"] = "insufficient_scope"
            parameters["error_description"] =
                "The request requires higher privileges than provided by the access token."
            parameters["error_uri"] = "https://tools.ietf.org/html/rfc6750#section-3.1"
        }

        val wwwAuthenticate: String = computeWWWAuthenticateHeaderValue(parameters)
        response.addHeader("WWW-Authenticate", wwwAuthenticate)
        response.status = HttpStatus.FORBIDDEN.value()
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