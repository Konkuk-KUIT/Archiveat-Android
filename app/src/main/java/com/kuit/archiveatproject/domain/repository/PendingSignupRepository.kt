package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.SignupDraft

interface PendingSignupRepository {
    fun save(draft: SignupDraft)
    fun get(): SignupDraft?
    fun clear()
}
