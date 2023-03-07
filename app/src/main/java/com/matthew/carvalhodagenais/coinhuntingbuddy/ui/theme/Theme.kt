package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Blue200,
    primaryVariant = Blue500,
    secondary = Blue100,
    surface = Gray200,
    background = Gray700,
)

private val LightColorPalette = lightColors(
    primary = Blue500,
    primaryVariant = Blue700,
    secondary = Blue100,
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

val Colors.addMinus: Color
    get() = if (isLight) Color(0xFF6F6F6F) else Color.White

val Colors.unselected: Color
    get() = if (isLight) Silver900 else Gray100

val Colors.secondaryText: Color
    get() = if (isLight) Gray500 else Silver900

val Colors.cardBorder: Color
    get() = if (isLight) Silver1000 else Gray150


@Composable
fun CoinHuntingBuddyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = if (darkTheme) Gray700 else Silver200
    )

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