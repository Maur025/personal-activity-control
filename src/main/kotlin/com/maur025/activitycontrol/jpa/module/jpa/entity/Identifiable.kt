package com.maur025.activitycontrol.jpa.module.jpa.entity

interface Identifiable<ID> {
    fun getId(): ID?

    fun isNew(): Boolean {
        return getId() == null
    }
}