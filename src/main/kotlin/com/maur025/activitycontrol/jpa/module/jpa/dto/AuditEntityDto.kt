package com.maur025.activitycontrol.jpa.module.jpa.dto

import java.time.LocalDateTime

class AuditEntityDto(
    createdAt: LocalDateTime, createdBy: String, updatedAt: LocalDateTime, updatedBy: String
) {}