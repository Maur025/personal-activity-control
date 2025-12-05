package com.maur025.activitycontrol.postgresql.module.config

import org.hibernate.boot.model.FunctionContributions
import org.hibernate.dialect.PostgreSQLDialect
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.StandardBasicTypes

class PGDialect : PostgreSQLDialect() {
    override fun initializeFunctionRegistry(functionContributions: FunctionContributions?) {
        super.initializeFunctionRegistry(functionContributions)

        functionContributions?.functionRegistry?.let { registry ->
            registry.register(
                "string_agg", StandardSQLFunction(
                    "string_agg", StandardBasicTypes.STRING
                )
            )

            registry.register("sum", StandardSQLFunction("sum", StandardBasicTypes.STRING))
        }
    }
}