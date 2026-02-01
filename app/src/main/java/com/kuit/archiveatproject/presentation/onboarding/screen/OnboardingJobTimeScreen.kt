package com.kuit.archiveatproject.presentation.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.presentation.onboarding.component.OnboardingNextButton
import com.kuit.archiveatproject.presentation.onboarding.component.job.JobSelectionComponent
import com.kuit.archiveatproject.presentation.onboarding.component.time.TimeSelectionComponent
import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingUiEvent
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingUiState
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.OnboardingViewModel
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.ReadingMode
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.TimeSlot
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
        },
        onTimeSlotClicked = { mode, slot ->
            viewModel.onEvent(
                OnboardingUiEvent.OnTimeSlotToggled(
                    mode = mode,
                    timeSlot = slot
                )
            )
        },
        onNextClicked = {
            viewModel.onEvent(OnboardingUiEvent.OnNextStep)
        }
    )

}

@Composable
private fun OnboardingJobTimeContent(
    uiState: OnboardingUiState,
    onJobSelected: (JobUiModel) -> Unit,
    onTimeSlotClicked: (ReadingMode, TimeSlot) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.height(74.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
        ) {

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

            // ===== Step 1: 직업 선택 =====
            item {
                JobSelectionComponent(
                    jobs = uiState.employmentOptions,
                    selectedEmploymentType = uiState.selectedEmploymentType,
                    onJobSelected = onJobSelected
                )
            }

            // ===== Step 2: 가용 시간 (조건부 노출) =====
            if (uiState.selectedEmploymentType != null) {
                item {
                    Spacer(Modifier.height(23.dp))

                    TimeSelectionComponent(
                        timeSlots = uiState.availabilityOptions,
                        employmentType = uiState.selectedEmploymentType,
                        lightSelected = uiState.lightReadingTimes,
                        deepSelected = uiState.deepReadingTimes,
                        onTimeClicked = { mode, slot ->
                            onTimeSlotClicked(mode, slot)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        OnboardingNextButton(
            text = "다음",
            enabled = uiState.isNextEnabled,
            onClick = onNextClicked,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(Modifier.height(34.dp))
    }

}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun OnboardingJobTimeScreenPreview() {

    val previewJobs = listOf(
        JobUiModel("STUDENT", "대학생", R.drawable.ic_job_student),
        JobUiModel("EMPLOYEE", "직장인", R.drawable.ic_job_student),
        JobUiModel("FREELANCER", "프리랜서", R.drawable.ic_job_student),
        JobUiModel("OTHER", "기타", R.drawable.ic_job_student)
    )

    val previewTimeSlots = listOf(
        TimeSlot.MORNING,
        TimeSlot.LUNCHTIME,
        TimeSlot.EVENING,
        TimeSlot.BEDTIME
    )

    // ===== Preview 전용 상태 =====
    var selectedEmploymentType by remember { mutableStateOf<String?>(null) }
    var lightReadingTimes by remember { mutableStateOf(setOf<TimeSlot>()) }
    var deepReadingTimes by remember { mutableStateOf(setOf<TimeSlot>()) }

    ArchiveatProjectTheme {
        OnboardingJobTimeContent(
            uiState = OnboardingUiState(
                employmentOptions = previewJobs,
                availabilityOptions = previewTimeSlots,
                selectedEmploymentType = selectedEmploymentType,
                lightReadingTimes = lightReadingTimes,
                deepReadingTimes = deepReadingTimes
            ),
            onJobSelected = { job ->
                selectedEmploymentType = job.type
            },
            onTimeSlotClicked = { mode, slot ->
                when (mode) {
                    ReadingMode.LIGHT -> {
                        if (slot !in deepReadingTimes) {
                            lightReadingTimes =
                                lightReadingTimes.toggle(slot)
                        }
                    }

                    ReadingMode.DEEP -> {
                        if (slot !in lightReadingTimes) {
                            deepReadingTimes =
                                deepReadingTimes.toggle(slot)
                        }
                    }
                }
            },
            onNextClicked = {
                // Preview에서는 실제 이동 없음
                // 필요하면 Log.d("Preview", "Next clicked")
            }
        )
    }
}

fun <T> Set<T>.toggle(item: T): Set<T> =
    if (contains(item)) this - item else this + item