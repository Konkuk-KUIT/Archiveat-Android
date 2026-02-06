package com.kuit.archiveatproject.domain.repository

import com.kuit.archiveatproject.domain.entity.Inbox

interface InboxRepository {
    suspend fun getInbox(): Inbox
}