package com.kuit.archiveatproject.presentation.onboarding.component.job

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuit.archiveatproject.R
import com.kuit.archiveatproject.ui.theme.ArchiveatProjectTheme

@Composable
fun JobSelectionItem(
    title: String,
    icon: Painter,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val borderColor =
        if (isSelected) ArchiveatProjectTheme.colors.primary
        else ArchiveatProjectTheme.colors.gray200

    val backgroundColor =
        if (isSelected) Color(0xFFDCD9FA)
        else ArchiveatProjectTheme.colors.white

    val textColor =
        if (isSelected) ArchiveatProjectTheme.colors.primary
        else ArchiveatProjectTheme.colors.gray600

    val iconTint =
        if (isSelected) ArchiveatProjectTheme.colors.primary
        else ArchiveatProjectTheme.colors.gray600

    Box(
        modifier = modifier
            .width(75.dp)
            .height(65.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                tint = iconTint
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = title,
                style = ArchiveatProjectTheme.typography.Caption_medium,
                color = textColor
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun JobSelectionItemSelectedPreview() {
    JobSelectionItem(
        title = "대학생",
        icon = painterResource(id = R.drawable.ic_job_student),
        isSelected = true,
        onClick = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun JobSelectionItemUnselectedPreview() {
    JobSelectionItem(
        title = "직장인",
        icon = painterResource(id = R.drawable.ic_job_student),
        isSelected = false,
        onClick = {}
    )
}