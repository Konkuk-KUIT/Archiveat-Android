package com.kuit.archiveatproject.ui.theme

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

data class ArchiveatColors(
    val white: Color,
    val black: Color,
    val primary: Color,
    val primary_1: Color,
    val primary_2: Color,
    val sub_1: Color,
    val sub_2: Color,
    val sub_3: Color,
    val inverse: Color
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
    inverse = Inverse
)

val LocalArchiveatColors = staticCompositionLocalOf { defaultArchiveatColors }

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)