package com.kuit.archiveatproject.ui.theme

import android.R.attr.data
import android.R.attr.fontFamily
import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.kuit.archiveatproject.R

// Set of Material typography styles to start with
val ArchiveatFontBold = FontFamily(Font(R.font.pretendard_bold))
val ArchiveatFontSemiBold = FontFamily(Font(R.font.pretendard_semibold))
val ArchiveatFontMedium = FontFamily(Font(R.font.pretendard_medium))
val ArchiveatFontRegular = FontFamily(Font(R.font.pretendard_regular))
val ArchiveatLogoFontRegular = FontFamily(Font(R.font.tilt_warp_regular))

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
    val Body_2_medium: TextStyle,
    val Body_2_semibold: TextStyle,
    val Caption_semibold: TextStyle,
    val Caption_medium: TextStyle,
    val Caption_medium_sec: TextStyle,
    val Etc_regular: TextStyle,
    val Logo_regular: TextStyle,
)

val defaultArchiveatTypography = ArchiveatTypography(
    Heading_1_bold = TextStyle(
        fontFamily = ArchiveatFontBold,
        fontSize = 22.sp,
        lineHeight = 1.4.em, // 행간 140%
        letterSpacing = (-0.002).em,
    ),
    Heading_1_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 22.sp,
        lineHeight = 1.4.em, // 행간 140%
        letterSpacing = (-0.002).em,
    ),
    Heading_2_bold = TextStyle(
        fontFamily = ArchiveatFontBold,
        fontSize = 20.sp,
        lineHeight = 1.4.em, // 행간 140%
        letterSpacing = (-0.002).em,
    ),
    Heading_2_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 20.sp,
        lineHeight = 1.4.em, // 행간 140%
        letterSpacing = (-0.002).em,
    ),
    Subhead_1_bold = TextStyle(
        fontFamily = ArchiveatFontBold,
        fontSize = 18.sp,
        lineHeight = 1.4.em, // 행간 140%
        letterSpacing = (-0.002).em,
    ),
    Subhead_1_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 18.sp,
        lineHeight = 1.45.em, // 행간 145%
        letterSpacing = (-0.002).em,
    ),
    Subhead_1_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 18.sp,
        lineHeight = 1.45.em, // 행간 145%
        letterSpacing = (-0.002).em,
    ),
    Subhead_2_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 16.sp,
        lineHeight = 1.45.em, // 행간 145%
        letterSpacing = (-0.002).em,
    ),
    Subhead_2_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 16.sp,
        lineHeight = 1.45.em, // 행간 145%
        letterSpacing = (-0.002).em,
    ),
    Body_1_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 14.sp,
        lineHeight = 1.45.em, // 행간 145%
        letterSpacing = (-0.002).em,
    ),
    Body_1_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 14.sp,
        lineHeight = 1.45.em, // 행간 145%
        letterSpacing = (-0.002).em,
    ),
    Body_1_regular = TextStyle(
        fontFamily = ArchiveatFontRegular,
        fontSize = 14.sp,
        lineHeight = 1.45.em, // 행간 145%
        letterSpacing = (-0.002).em,
    ),
    Body_2_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 14.sp,
        lineHeight = 1.40.em, // 행간 140%
        letterSpacing = 0.em,
    ),
    Body_2_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 14.sp,
        lineHeight = 1.40.em, // 행간 140%
        letterSpacing = 0.em,
    ),
    Caption_semibold = TextStyle(
        fontFamily = ArchiveatFontSemiBold,
        fontSize = 12.sp,
        lineHeight = 1.4.em, // 행간 140%
        letterSpacing = (-0.002).em,
    ),
    Caption_medium = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 12.sp,
        lineHeight = 1.4.em, // 행간 140%
        letterSpacing = (-0.002).em,
    ),
    Caption_medium_sec = TextStyle(
        fontFamily = ArchiveatFontMedium,
        fontSize = 12.sp,
        lineHeight = 1.4.em, // 행간 140%
        letterSpacing = 0.em,
    ),
    Etc_regular = TextStyle(
        fontFamily = ArchiveatFontRegular,
        fontSize = 12.sp,
        lineHeight = 1.4.em,
        letterSpacing = 0.sp,
    ),
    Logo_regular = TextStyle(
        fontFamily = ArchiveatLogoFontRegular,
        fontSize = 22.sp,
        lineHeight = 1.4.em,
        letterSpacing = 0.sp,
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