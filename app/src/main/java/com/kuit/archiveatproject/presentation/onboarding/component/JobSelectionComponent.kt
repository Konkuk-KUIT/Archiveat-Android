package com.kuit.archiveatproject.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.presentation.onboarding.model.JobUiModel
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun JobSelectionComponent(
    jobs: List<JobUiModel>,
    selectedEmploymentType: String?,
    onJobSelected: (JobUiModel) -> Unit,
    modifier: Modifier = Modifier
){
    Column(

    ) {
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
                    text = "1",
                    style = ArchiveatProjectTheme.typography.Caption_semibold,
                    color = ArchiveatProjectTheme.colors.gray700
                )
            }
            Spacer(modifier.width(7.dp))
            Text(
                text = "현재 하시는 일",
                style = ArchiveatProjectTheme.typography.Subhead_2_semibold,
                color = ArchiveatProjectTheme.colors.gray950
            )
        }
        Spacer(modifier.height(14.dp))
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            jobs.forEach { job ->
                JobSelectionItem(
                    title = job.label,
                    icon = painterResource(id = R.drawable.ic_job_student),
                    isSelected = job.type == selectedEmploymentType,
                    onClick = { onJobSelected(job) }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun JobSelectionComponentPreview() {

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

    ArchiveatProjectTheme {
        JobSelectionComponent(
            jobs = previewJobs,
            selectedEmploymentType = previewJobs[0].type, // "대학생" 선택 상태
            onJobSelected = {}
        )
    }
}