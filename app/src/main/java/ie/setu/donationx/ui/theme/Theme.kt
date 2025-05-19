package ie.setu.donationx.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6200EE),  // Dark Primary color
    secondary = Color(0xFF03DAC6), // Dark Secondary color
    tertiary = Color(0xFFBB86FC)  // Dark Tertiary color
)

// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1E88E5),  // Light Primary color
    secondary = Color(0xFF0288D1), // Light Secondary color
    tertiary = Color(0xFF03A9F4)  // Light Tertiary color
)


val startGradientColor = Color(0xFF1e88e5)
val endGradientColor = Color(0xFF005cb2)


@Composable
fun DonationXTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Apply the selected color
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )

    // Adjust the status bar color to match the theme
    val view = LocalView.current
    val context = LocalContext.current
    SideEffect {
        (context as? Activity)?.let {
            WindowCompat.getInsetsController(it.window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
}
