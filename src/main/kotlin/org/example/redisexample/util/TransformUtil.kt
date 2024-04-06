package org.example.redisexample.util

import kotlin.reflect.full.memberProperties

fun <T : Any> T.toMap(): Map<String, String> {
    return this::class.memberProperties.associateBy({ it.name }, { it.call(this)?.toString() ?: "" })
}