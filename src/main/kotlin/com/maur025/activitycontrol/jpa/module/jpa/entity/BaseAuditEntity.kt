package com.maur025.activitycontrol.jpa.module.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Version
import org.hibernate.annotations.OptimisticLock
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseAuditEntity : BaseEntity() {
    @OptimisticLock(excluded = true)
    @CreatedDate
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default now()")
    var createdAt: LocalDateTime? = null

    @OptimisticLock(excluded = true)
    @CreatedBy
    @Column(name = "created_by")
    var createdBy: String? = null

    @OptimisticLock(excluded = true)
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp default now()")
    var updatedAt: LocalDateTime? = null

    @OptimisticLock(excluded = true)
    @LastModifiedBy
    @Column(name = "updated_by")
    var updatedBy: String? = null

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 0")
    var version: Long? = 0L
}