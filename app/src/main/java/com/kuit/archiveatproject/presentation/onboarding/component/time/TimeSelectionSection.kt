package com.kuit.archiveatproject.presentation.onboarding.component.time

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.TimeSlot
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun TimeSelectionSection(
    title: String,
    timeSlots: List<TimeSlot>,
    selectedTimes: Set<TimeSlot>,
    disabledTimes: Set<TimeSlot>,
    onTimeClicked: (TimeSlot) -> Unit
) {
    Text(
        text = title,
        style = ArchiveatProjectTheme.typography.Caption_semibold,
        color = ArchiveatProjectTheme.colors.black
    )
    Spacer(Modifier.height(14.dp))

    TimeSlotGrid(
        timeSlots = timeSlots,
        selectedTimes = selectedTimes,
        disabledTimes = disabledTimes,
        onTimeClicked = onTimeClicked
    )
}