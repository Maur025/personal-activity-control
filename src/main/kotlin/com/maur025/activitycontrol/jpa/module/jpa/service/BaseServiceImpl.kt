package com.maur025.activitycontrol.jpa.module.jpa.service

import com.maur025.activitycontrol.common.module.exception.ResourceNotFoundException
import com.maur025.activitycontrol.jpa.module.jpa.entity.Identifiable
import com.maur025.activitycontrol.jpa.module.jpa.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional(readOnly = true)
abstract class BaseServiceImpl<TAB : Identifiable<ID>, ID : Any> : BaseService<TAB, ID> {
    protected abstract fun resourceName(): String

    protected abstract fun repository(): BaseRepository<TAB, ID>

    @Transactional
    override fun save(entity: TAB): TAB = repository().save(entity)

    @Transactional
    override fun saveAll(entities: List<TAB>): List<TAB> = repository().saveAll(entities)

    override fun existsById(id: ID): Boolean = repository().existsById(id)

    override fun findById(id: ID): Optional<TAB> = repository().findById(id)

    override fun findByIdThrow(id: ID): TAB = findById(id).orElseThrow {
        ResourceNotFoundException("${resourceName()}[id=$id]")
    }

    override fun findAll(): List<TAB> = repository().findAll()

    override fun findAllById(ids: Iterable<ID>): List<TAB> = repository().findAllById(ids)

    override fun findAll(pageable: Pageable): Page<TAB> = repository().findAll(pageable)

    override fun count(): Long = repository().count()

    @Transactional
    override fun deleteById(id: ID) = repository().deleteById(id)

    @Transactional
    override fun delete(entity: TAB) = repository().delete(entity)

    @Transactional
    override fun deleteAllById(ids: List<ID>) = repository().deleteAllById(ids)

    @Transactional
    override fun deleteAll(entities: List<TAB>) = repository().deleteAll(entities)
}