package com.kuit.archiveatproject.presentation.share.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.core.util.ApiException
import com.kuit.archiveatproject.domain.repository.NewsletterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val newsletterRepository: NewsletterRepository
) : ViewModel() {
    companion object {
        private const val DUPLICATE_NEWSLETTER_STATUS_CODE = 40904
    }

    private val _uiState = MutableStateFlow(ShareUiState())
    val uiState: StateFlow<ShareUiState> = _uiState

    /** Activity에서 전달받은 공유 텍스트 → contentUrl 추출 */
    fun setContentUrlFromSharedText(sharedText: String) {
        val contentUrl = extractContentUrl(sharedText)
        _uiState.update {
            it.copy(contentUrl = contentUrl)
        }
    }

    fun updateMemo(memo: String) {
        _uiState.update { it.copy(memo = memo) }
    }

    fun consumeToastMessage() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    fun saveNewsletter() {
        val state = _uiState.value

        val memoToSend = state.memo.trim()

        if (state.contentUrl.isBlank()) {
            _uiState.update {
                it.copy(
                    errorMessage = "유효한 링크를 찾을 수 없습니다.",
                    toastMessage = "유효한 링크를 찾을 수 없습니다."
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                newsletterRepository.saveNewsletter(
                    contentUrl = state.contentUrl,
                    memo = memoToSend
                )
            }.onSuccess {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        errorMessage = null,
                        toastMessage = "저장이 완료됐어요."
                    )
                }
            }.onFailure { throwable ->
                val message = throwable.toShareErrorMessage()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = message,
                        toastMessage = message
                    )
                }
            }
        }
    }

    private fun Throwable.toShareErrorMessage(): String {
        if (this is ApiException) {
            if (code == DUPLICATE_NEWSLETTER_STATUS_CODE || message.containsDuplicateKeyword()) {
                return "이미 저장한 기사예요."
            }
            return if (message.isBlank()) "저장 중 오류가 발생했어요." else message
        }
        if (this is HttpException) {
            val (statusCode, serverMessage) = parseServerError()
            if (statusCode == DUPLICATE_NEWSLETTER_STATUS_CODE || serverMessage.containsDuplicateKeyword()) {
                return "이미 저장한 기사예요."
            }
            if (serverMessage.isNotBlank()) return serverMessage
            if (this.code() in 500..599) return "서버 오류가 발생했어요."
            return "저장 중 오류가 발생했어요."
        }
        if (this is IOException) return "네트워크 연결을 확인해주세요."
        return "저장 중 오류가 발생했어요."
    }

    private fun String.containsDuplicateKeyword(): Boolean {
        val lower = lowercase()
        return contains("이미") || "already" in lower || "duplicate" in lower
    }

    // HttpException일 때 에러 바디 JSON 파싱으로 statusCode/message 추출
    private fun HttpException.parseServerError(): Pair<Int?, String> {
        val rawBody = try {
            response()?.errorBody()?.string().orEmpty().trim()
        } catch (_: Exception) {
            ""
        }

        if (rawBody.isBlank()) {
            return code() to message().orEmpty().trim()
        }

        return try {
            val json = JSONObject(rawBody)
            val statusCode = json.optInt("statusCode").takeIf { it != 0 }
            val message = json.optString("message").orEmpty().trim()
            statusCode to message
        } catch (_: Exception) {
            code() to rawBody
        }
    }

    /** 공유 텍스트에서 contentUrl 추출 */
    private fun extractContentUrl(text: String): String {
        val regex = "(https?://[^\\s]+)".toRegex()
        return regex.find(text)?.value.orEmpty()
    }
}
