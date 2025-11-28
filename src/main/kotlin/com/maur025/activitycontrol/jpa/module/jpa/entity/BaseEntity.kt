package com.maur025.activitycontrol.jpa.module.jpa.entity

import jakarta.persistence.*
import org.hibernate.annotations.OptimisticLock
import java.util.*

@MappedSuperclass
abstract class BaseEntity : Identifiable<UUID> {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, columnDefinition = "uuid DEFAULT uuid_generate_v4()")
    var id: UUID? = null

    @OptimisticLock(excluded = true)
    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    var deleted: Boolean = false

    @PrePersist
    private fun beforePersist() {
        if (id == null) {
            id = UUID.randomUUID()
        }
    }
}