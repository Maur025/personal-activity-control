package com.maur025.activitycontrol.jpa.module.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.hibernate.type.descriptor.WrapperOptions
import org.hibernate.type.descriptor.java.JavaType
import org.hibernate.type.format.FormatMapper
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper

class HibernateFormatMapper : FormatMapper {
    private val delegate: FormatMapper;

    init {
        delegate = JacksonJsonFormatMapper(createObjectMapper())
    }

    private fun createObjectMapper(): ObjectMapper = ObjectMapper().apply {
        registerModule(JavaTimeModule())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    }

    override fun <T : Any?> fromString(
        charSequence: CharSequence?, javaType: JavaType<T?>?, wrapperOptions: WrapperOptions?
    ): T? = delegate.fromString(charSequence, javaType, wrapperOptions)

    override fun <T : Any?> toString(t: T?, javaType: JavaType<T?>?, wrapperOptions: WrapperOptions?): String? =delegate.toString(t,javaType,wrapperOptions)
}