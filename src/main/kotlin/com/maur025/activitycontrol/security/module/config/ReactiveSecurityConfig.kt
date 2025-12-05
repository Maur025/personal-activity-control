package com.maur025.activitycontrol.security.module.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.web.server.ServerAuthenticationEntryPoint

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
class ReactiveSecurityConfig(
    @Qualifier("reactiveServerAuthenticationEntryPoint") private val reactiveServerAuthenticationEntryPoint: ServerAuthenticationEntryPoint
) {

}