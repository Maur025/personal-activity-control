package com.maur025.activitycontrol.jpa.module.config

import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaConfig {
    @Bean
    fun auditorAware(
        provider: ObjectProvider<AuditorAware<String>>
    ): AuditorAware<String> = provider.getIfAvailable { AuditorAwareImpl() }
}