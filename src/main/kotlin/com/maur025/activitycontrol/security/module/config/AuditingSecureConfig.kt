package com.maur025.activitycontrol.security.module.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.AuditorAware

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class AuditingSecureConfig {

    @Bean
    @Primary
    fun auditorAware(): AuditorAware<String> = AuditorAwareSecureImpl()
}