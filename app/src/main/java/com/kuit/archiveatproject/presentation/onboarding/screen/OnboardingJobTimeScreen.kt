package com.kuit.archiveatproject.presentation.onboarding.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.presentation.onboarding.component.JobSelectionComponent
import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingUiEvent
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingUiState
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingViewModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun OnboardingJobTimeScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    OnboardingJobTimeContent(
        uiState = uiState,
        onJobSelected = { job ->
            viewModel.onEvent(OnboardingUiEvent.OnEmploymentSelected(job))
        }
    )
}

@Composable
private fun OnboardingJobTimeContent(
    uiState: OnboardingUiState,
    onJobSelected: (JobUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {

        item {
            Text(
                text = "나의 리딩 패턴을\n알려주세요.",
                style = ArchiveatProjectTheme.typography.Heading_2_semibold,
                color = ArchiveatProjectTheme.colors.gray950
            )
            Spacer(Modifier.height(9.dp))
        }

        item {
            Text(
                text = "직업과 가용 시간에 맞춰 최적의 분량을 추천해드려요.",
                style = ArchiveatProjectTheme.typography.Body_2_medium,
                color = ArchiveatProjectTheme.colors.gray400
            )
            Spacer(Modifier.height(23.dp))
        }

        item {
            JobSelectionComponent(
                jobs = uiState.employmentOptions,
                selectedJob = uiState.selectedEmployment,
                onJobSelected = onJobSelected
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun OnboardingJobTimeScreenPreview() {

    val previewJobs = listOf(
        JobUiModel(
            type = "STUDENT",
            label = "대학생",
            iconRes = R.drawable.ic_job_student
        ),
        JobUiModel(
            type = "EMPLOYEE",
            label = "직장인",
            iconRes = R.drawable.ic_job_student
        ),
        JobUiModel(
            type = "FREELANCER",
            label = "프리랜서",
            iconRes = R.drawable.ic_job_student
        ),
        JobUiModel(
            type = "OTHER",
            label = "기타",
            iconRes = R.drawable.ic_job_student
        )
    )

    var selectedJob by remember { mutableStateOf<JobUiModel?>(null) }

    ArchiveatProjectTheme {
        OnboardingJobTimeContent(
            uiState = OnboardingUiState(
                employmentOptions = previewJobs,
                selectedEmployment = selectedJob
            ),
            onJobSelected = { job ->
                selectedJob = job
            }
        )
    }
}