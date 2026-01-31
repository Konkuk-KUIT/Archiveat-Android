package com.kuit.archiveatproject.ui.theme

import android.R.attr.data
import android.R.color.white
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
val Primary = Color(0xFF6220B8)
val Primary_1 = Color(0xFF934EEC)
val Primary_2 = Color(0xFFF7CEFA)
val Sub_1 = Color(0xFF3086FF)
val Sub_2 = Color(0xFFFD6D4C)
val Sub_3 = Color(0xFFFFB617)
val Inverse = Color(0xFF393945)
val DeepIndigo = Color(0xFF3A3B5D)

val DeepPurple = Color(0xFF39169C)
val Gray50 = Color(0xFFF2F3F6)
val Gray100 = Color(0xFFE2E3E9)
val Gray200 = Color(0xFFD5DAE3)
val Gray300 = Color(0xFFB6BED2)
val Gray400 = Color(0xFF8B91A1)
val Gray500 = Color(0xFF6B7084)
val Gray600 = Color(0xFF4E5368)
val Gray700 = Color(0xFF383E53)
val Gray800 = Color(0xFF262A3D)
val Gray900 = Color(0xFF1C1F2D)
val Gray950 = Color(0xFF191A28)

data class ArchiveatColors(
    val white: Color,
    val black: Color,
    val primary: Color,
    val primary_1: Color,
    val primary_2: Color,
    val sub_1: Color,
    val sub_2: Color,
    val sub_3: Color,
    val inverse: Color,
    val deepIndigo: Color,
    val deepPurple: Color,
    val gray50: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray700: Color,
    val gray800: Color,
    val gray900: Color,
    val gray950: Color,
)

val defaultArchiveatColors = ArchiveatColors(
    white = White,
    black = Black,
    primary = Primary,
    primary_1 = Primary_1,
    primary_2 = Primary_2,
    sub_1 = Sub_1,
    sub_2 = Sub_2,
    sub_3 = Sub_3,
    inverse = Inverse,
    deepIndigo = DeepIndigo,
    deepPurple = DeepPurple,
    gray50 = Gray50,
    gray100 = Gray100,
    gray200 = Gray200,
    gray300 = Gray300,
    gray400 = Gray400,
    gray500 = Gray500,
    gray600 = Gray600,
    gray700 = Gray700,
    gray800 = Gray800,
    gray900 = Gray900,
    gray950 = Gray950,
)

val LocalArchiveatColors = staticCompositionLocalOf { defaultArchiveatColors }

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)