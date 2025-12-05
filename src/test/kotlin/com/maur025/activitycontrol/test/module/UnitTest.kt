package com.maur025.activitycontrol.test.module

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.mockito.MockitoAnnotations

@Tag("UnitTests")
abstract class UnitTest {
    var closeable: AutoCloseable? = null

    @BeforeEach
    fun openMocks() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @AfterEach
    fun releaseMocks() {
        closeable?.close()
    }
}