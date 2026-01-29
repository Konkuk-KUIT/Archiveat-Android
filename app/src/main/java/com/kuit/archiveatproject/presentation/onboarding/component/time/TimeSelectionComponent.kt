package com.kuit.archiveatproject.presentation.onboarding.component.time

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.presentation.onboarding.screen.toggle
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.ReadingMode
import com.kuit.archiveatproject.presentation.onboarding.viewmodel.TimeSlot
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun TimeSelectionComponent(
    timeSlots: List<TimeSlot>,
    lightSelected: Set<TimeSlot>,
    deepSelected: Set<TimeSlot>,
    onTimeClicked: (ReadingMode, TimeSlot) -> Unit,
    modifier: Modifier = Modifier
){
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(
                        color = ArchiveatProjectTheme.colors.gray200,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "2",
                    style = ArchiveatProjectTheme.typography.Caption_semibold,
                    color = ArchiveatProjectTheme.colors.gray700
                )
            }
            Spacer(Modifier.width(7.dp))
            Text(
                text = "가용 시간 (중복 선택)",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray950
            )
        }
        Spacer(Modifier.height(18.dp))

        TimeSelectionSection(
            title = "가볍게 읽기 (10분 미만)",
            timeSlots = timeSlots,
            selectedTimes = lightSelected,
            disabledTimes = deepSelected,
            onTimeClicked = { slot ->
                onTimeClicked(ReadingMode.LIGHT, slot)
            }
        )

        Spacer(Modifier.height(18.dp))

        TimeSelectionSection(
            title = "몰입해 읽기 (10분 이상)",
            timeSlots = timeSlots,
            selectedTimes = deepSelected,
            disabledTimes = lightSelected,
            onTimeClicked = { slot ->
                onTimeClicked(ReadingMode.DEEP, slot)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeSelectionComponentPreview() {
    ArchiveatProjectTheme {

        var lightSelected by remember {
            mutableStateOf(setOf(TimeSlot.MORNING))
        }

        var deepSelected by remember {
            mutableStateOf(setOf<TimeSlot>())
        }

        val timeSlots = listOf(
            TimeSlot.MORNING,
            TimeSlot.LUNCHTIME,
            TimeSlot.EVENING,
            TimeSlot.BEDTIME
        )

        TimeSelectionComponent(
            timeSlots = timeSlots,
            lightSelected = lightSelected,
            deepSelected = deepSelected,
            onTimeClicked = { mode, slot ->
                when (mode) {
                    ReadingMode.LIGHT -> {
                        if (slot !in deepSelected) {
                            lightSelected = lightSelected.toggle(slot)
                        }
                    }

                    ReadingMode.DEEP -> {
                        if (slot !in lightSelected) {
                            deepSelected = deepSelected.toggle(slot)
                        }
                    }
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}