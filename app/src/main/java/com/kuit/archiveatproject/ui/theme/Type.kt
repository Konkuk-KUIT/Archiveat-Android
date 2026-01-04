package com.kuit.archiveatproject.ui.theme

import android.R.attr.fontFamily
import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kuit.archiveatproject.R

// Set of Material typography styles to start with
val ArchiveatFontBold = FontFamily(Font(R.font.pretendard_bold))
val ArchiveatFontSemiBold = FontFamily(Font(R.font.pretendard_semibold))
val ArchiveatFontMedium = FontFamily(Font(R.font.pretendard_medium))
val ArchiveatFontRegular = FontFamily(Font(R.font.pretendard_regular))

data class ArchiveatTypography(
    val Heading_1_bold: TextStyle,
    val Heading_1_semibold: TextStyle,
    val Heading_2_bold: TextStyle,
    val Heading_2_semibold: TextStyle,
    val Subhead_1_bold: TextStyle,
    val Subhead_1_semibold: TextStyle,
    val Subhead_1_medium: TextStyle,
    val Subhead_2_semibold: TextStyle,
    val Subhead_2_medium: TextStyle,
    val Body_1_semibold: TextStyle,
    val Body_1_medium: TextStyle,
    val Body_1_regular: TextStyle,
    val Caption_semibold: TextStyle,
    val Caption_medium: TextStyle
)

val defaultArchiveatTypography = ArchiveatTypography(
    Heading_1_bold = TextStyle(
        fontFamily = ArchiveatFontBold,
        fontSize = 22.sp,
        lineHeight = 22.sp
    ),
    Heading_1_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 22.sp,
        lineHeight = 22.sp
    ),
    Heading_2_bold = TextStyle(
        fontFamily = ArchiveatFontBold,
        fontSize = 20.sp,
        lineHeight = 20.sp
    ),
    Heading_2_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp
    ),
    Subhead_1_bold = TextStyle(
        fontFamily = ArchiveatFontBold,
        fontSize = 18.sp,
        lineHeight = 25.2.sp // 행간 140%
    ),
    Subhead_1_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 18.sp,
        lineHeight = 18.sp
    ),
    Subhead_1_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 18.sp,
        lineHeight = 18.sp
    ),
    Subhead_2_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp
    ),
    Subhead_2_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 16.sp,
        lineHeight = 16.sp
    ),
    Body_1_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp
    ),
    Body_1_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 14.sp,
        lineHeight = 19.6.sp // 행간 140%
    ),
    Body_1_regular = TextStyle(
        fontFamily = ArchiveatFontRegular,
        fontSize = 14.sp,
        lineHeight = 14.sp
    ),
    Caption_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 12.sp,
        lineHeight = 12.sp
    ),
    Caption_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 12.sp,
        lineHeight = 12.sp
    ),
)

val LocalArchiveatTypography = staticCompositionLocalOf { defaultArchiveatTypography }

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)