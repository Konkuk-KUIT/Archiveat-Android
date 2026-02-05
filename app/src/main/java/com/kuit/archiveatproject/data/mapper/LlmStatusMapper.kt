package com.kuit.archiveatproject.data.mapper

import com.kuit.archiveatproject.data.dto.response.explore.LlmStatusDto
import com.kuit.archiveatproject.domain.entity.LlmStatus

// 공용 파일 - 나중에 LlmStatus를 공용 엔티티, 디티오로 뺴면 좋을 듯
fun LlmStatusDto.toEntity(): LlmStatus = when (this) {
    LlmStatusDto.PENDING -> LlmStatus.PENDING
    LlmStatusDto.RUNNING -> LlmStatus.RUNNING
    LlmStatusDto.DONE -> LlmStatus.DONE
    LlmStatusDto.FAILED -> LlmStatus.FAILED
}