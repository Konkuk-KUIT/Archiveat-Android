package com.kuit.archiveatproject.presentation.newsletterdetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.core.component.tag.TagVariant
import com.kuit.archiveatproject.domain.entity.HomeCardType
import com.kuit.archiveatproject.domain.entity.HomeTabType
import com.kuit.archiveatproject.domain.entity.NewsletterSimple
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import com.kuit.archiveatproject.domain.repository.UserRepository
import com.kuit.archiveatproject.presentation.newsletterdetails.component.AiSectionUiModel
import com.kuit.archiveatproject.presentation.newsletterdetails.screen.NewsletterDetailsAiUiModel
import com.kuit.archiveatproject.presentation.newsletterdetails.screen.TagUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NewsletterSimpleViewModel @Inject constructor(
    private val newsletterRepository: NewsletterRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val argUserNewsletterId: Long =
        savedStateHandle.get<Long>("userNewsletterId") ?: -1L

    private val _uiState = MutableStateFlow(NewsletterSimpleUiState(isLoading = true))
    val uiState: StateFlow<NewsletterSimpleUiState> = _uiState

    init {
        if (argUserNewsletterId != -1L) {
            load(argUserNewsletterId)
        } else {
            _uiState.update { it.copy(isLoading = false, errorMessage = "userNewsletterId가 없습니다") }
        }
    }

    fun load(userNewsletterId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val nickname = runCatching { userRepository.getNickname() }.getOrDefault("나")
                val simple = newsletterRepository.getNewsletterSimple(userNewsletterId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        model = simple.toAiUiModel(userName = nickname),
                        contentUrl = simple.contentUrl,
                    )
                }
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "뉴스레터 조회에 실패했습니다."
                    )
                }
            }
        }
    }

    fun markRead() {
        val id = argUserNewsletterId
        if (id == -1L) return
        viewModelScope.launch {
            try {
                newsletterRepository.patchNewsletterRead(id)
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
            }
        }
    }
}

private fun NewsletterSimple.toAiUiModel(userName: String): NewsletterDetailsAiUiModel {
    val topicText = listOf(categoryName, topicName)
        .filter { it.isNotBlank() }
        .joinToString(" - ")
        .ifBlank { "" }

    val tags = buildList {
        if (label.isNotBlank()) {
            add(TagUiModel(text = label, variant = TagVariant.Tab(HomeTabType.fromLabel(label))))
        }
        add(TagUiModel(text = HomeCardType.AI_SUMMARY.label, variant = TagVariant.CardType(HomeCardType.AI_SUMMARY)))
    }

    return NewsletterDetailsAiUiModel(
        topicText = topicText,
        imageUrl = thumbnailUrl,
        tags = tags,
        contentTitle = title,
        userName = userName,
        aiSections = simpleSummary.map { AiSectionUiModel(it.title, it.content) },
        memo = memo
    )
}
