package com.kuit.archiveatproject.data.util

import com.kuit.archiveatproject.core.util.ApiException
import com.kuit.archiveatproject.data.dto.response.BaseResponse

fun <T> BaseResponse<T>.requireData(): T {
    if (!isSuccess) throw ApiException(statusCode, message)
    return data ?: throw ApiException(statusCode, "data is null")
}

fun BaseResponse<Unit>.requireSuccess() {
    if (!isSuccess) throw ApiException(statusCode, message)
}