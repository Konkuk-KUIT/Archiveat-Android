import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuit.archiveatproject.domain.repository.ExploreTopicNewslettersRepository
import com.kuit.archiveatproject.presentation.explore.viewmodel.ExploreTopicDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreTopicDetailViewModel @Inject constructor(
    private val repository: ExploreTopicNewslettersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val topicId: Long =
        savedStateHandle["topicId"] ?: 0L

    private val _uiState = MutableStateFlow(ExploreTopicDetailUiState())
    val uiState: StateFlow<ExploreTopicDetailUiState> = _uiState.asStateFlow()

    init {
        fetchNewsletters()
    }

    fun fetchNewsletters(page: Int = 0) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                repository.getTopicUserNewsletters(
                    topicId = topicId,
                    page = page,
                    size = 20
                )
            }.onSuccess { result ->
                _uiState.update {
                    it.copy(
                        newsletters = result.newsletters,
                        hasNext = result.hasNext,
                        isLoading = false
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message
                    )
                }
            }
        }
    }
}