package com.maur025.activitycontrol.common.module.exception

class ResourceNotFoundException : DefaultException {
    constructor(resource: String) : super(404, "$resource not found.")

    constructor() : this("Resource")
}