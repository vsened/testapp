package com.vsened.testapp.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vsened.testapp.R

// Set of Material typography styles to start with
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

val calypso = FontFamily(
    Font(R.font.calypso, FontWeight.Normal)
)

val cruinn = FontFamily(
    Font(R.font.cruinn_light, FontWeight.Light),
    Font(R.font.cruinn_regular, FontWeight.Normal),
    Font(R.font.cruinn_black, FontWeight.Black),
    Font(R.font.cruinn_bold, FontWeight.Bold),
    Font(R.font.cruinn_medium, FontWeight.Medium),
    Font(R.font.cruinn_thin, FontWeight.Thin)
)