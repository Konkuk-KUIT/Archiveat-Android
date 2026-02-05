package com.kuit.archiveatproject.core.util

class ApiException(
    val code: Int,
    override val message: String
): RuntimeException(message)