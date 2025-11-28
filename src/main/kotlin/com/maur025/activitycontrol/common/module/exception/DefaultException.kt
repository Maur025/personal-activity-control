package com.maur025.activitycontrol.common.module.exception

open class DefaultException : RuntimeException, IException {
    private val code: Int

    constructor(code: Int, message: String?, cause: Throwable?) : super(message, cause) {
        this.code = code
    }

    constructor(code: Int, message: String) : this(code, message, null)

    constructor() : this(500, "INTERNAL_SERVER_ERROR")

    override fun getCode(): Int = this.code
}