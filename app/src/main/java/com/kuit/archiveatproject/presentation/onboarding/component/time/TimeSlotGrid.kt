package com.kuit.archiveatproject.presentation.onboarding.component.time

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.TimeSlot

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TimeSlotGrid(
    timeSlots: List<TimeSlot>,
    selectedTimes: Set<TimeSlot>,
    disabledTimes: Set<TimeSlot>,
    employmentType: String,
    onTimeClicked: (TimeSlot) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        timeSlots.forEach { slot ->

            val isSelected = slot in selectedTimes
            val isDisabled =
                slot in disabledTimes ||

                        (selectedTimes.size >= 2 && slot !in selectedTimes)

            TimeSelectionItem(
                text = slot.toDisplayText(employmentType),
                isSelected = isSelected,
                isDisabled = isDisabled,
                onClick = { onTimeClicked(slot) },
                modifier = Modifier.fillMaxWidth(0.48f)
            )
        }
    }
}

fun TimeSlot.toDisplayText(employmentType: String): String {
    return when (employmentType) {
        "STUDENT" -> when (this) {
            TimeSlot.MORNING -> "등굣길"
            TimeSlot.LUNCHTIME -> "공강, 점심"
            TimeSlot.EVENING -> "하굣길"
            TimeSlot.BEDTIME -> "자기 전"
        }

        "EMPLOYEE" -> when (this) {
            TimeSlot.MORNING -> "출근길"
            TimeSlot.LUNCHTIME -> "점심시간"
            TimeSlot.EVENING -> "퇴근길"
            TimeSlot.BEDTIME -> "자기 전"
        }

        else -> when (this) {
            TimeSlot.MORNING -> "오전"
            TimeSlot.LUNCHTIME -> "오후"
            TimeSlot.EVENING -> "저녁"
            TimeSlot.BEDTIME -> "자기 전"
        }
    }
}