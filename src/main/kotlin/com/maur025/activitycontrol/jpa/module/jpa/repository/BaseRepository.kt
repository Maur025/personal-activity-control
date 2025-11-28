package com.maur025.activitycontrol.jpa.module.jpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<TAB : Any, ID : Any> : JpaRepository<TAB, ID>,
    JpaSpecificationExecutor<TAB> {}