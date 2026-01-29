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
            TimeSelectionItem(
                text = slot.toDisplayText(),
                isSelected = slot in selectedTimes,
                isDisabled = slot in disabledTimes,
                onClick = { onTimeClicked(slot) },
                modifier = Modifier.fillMaxWidth(0.48f)
            )
        }
    }
}

private fun TimeSlot.toDisplayText(): String =
    when (this) {
        TimeSlot.MORNING -> "등굣길 (아침)"
        TimeSlot.LUNCHTIME -> "공강/점심"
        TimeSlot.EVENING -> "하굣길 (저녁)"
        TimeSlot.BEDTIME -> "자기 전"
    }