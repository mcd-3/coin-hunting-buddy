package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    surface = Gray200,
    background = Gray700,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    surface = Silver200,
    background = Silver200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

val Colors.topAppBar: Color
    get() = if(isLight) Silver200 else Gray700

val Colors.cardBackground: Color
    get() = if(isLight) Silver300 else Gray500

val Colors.deleteIcon: Color
    get() = Red

val Colors.labelColor: Color
    get() = if (isLight) Color.Black else Color.White


@Composable
fun CoinHuntingBuddyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}