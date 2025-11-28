package com.maur025.activitycontrol.jpa.module.jpa.service

import com.maur025.activitycontrol.jpa.module.jpa.entity.Identifiable
import org.jetbrains.annotations.NotNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import java.util.*

@Validated
interface BaseService<TAB : Identifiable<ID>, ID : Any> {
    fun save(entity: TAB): TAB

    fun saveAll(entities: List<TAB>): List<TAB>

    fun existsById(@NotNull id: ID): Boolean

    fun findById(@NotNull id: ID): Optional<TAB>

    fun findByIdThrow(@NotNull id: ID): TAB

    fun findAll(): List<TAB>

    fun findAllById(ids: Iterable<ID>): List<TAB>

    fun findAll(pageable: Pageable): Page<TAB>

    fun count(): Long

    fun deleteById(@NotNull id: ID)

    fun delete(@NotNull entity: TAB)

    fun deleteAllById(ids: List<ID>)

    fun deleteAll(entities: List<TAB>)
}