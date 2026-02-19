package com.kuit.archiveatproject.data.repositoryimpl

import com.kuit.archiveatproject.domain.entity.SignupDraft
import com.kuit.archiveatproject.domain.repository.PendingSignupRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PendingSignupRepositoryImpl @Inject constructor() : PendingSignupRepository {
    private var draft: SignupDraft? = null

    override fun save(draft: SignupDraft) {
        this.draft = draft
    }

    override fun get(): SignupDraft? = draft

    override fun clear() {
        draft = null
    }
}
