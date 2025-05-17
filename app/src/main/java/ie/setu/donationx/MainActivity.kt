package ie.setu.donationx

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.donationx.navigation.NavHostProvider
import ie.setu.donationx.navigation.Report
import ie.setu.donationx.navigation.allDestinations
import ie.setu.donationx.ui.components.general.BottomAppBarProvider
import ie.setu.donationx.ui.components.general.TopAppBarProvider
import ie.setu.donationx.ui.theme.DonationXTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // State to toggle dark and light theme
            var darkTheme by remember { mutableStateOf(false) }  // Default to Light theme

            // Apply the selected theme
            DonationXTheme(darkTheme = darkTheme) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DonationXApp(
                        modifier = Modifier,
                        darkTheme = darkTheme,
                        onThemeToggle = { darkTheme = it }
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DonationXApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    darkTheme: Boolean,  // Current theme state
    onThemeToggle: (Boolean) -> Unit
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen = allDestinations.find { it.route == currentDestination?.route } ?: Report

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null
            ) { navController.navigateUp() }

            // Add the theme toggle in the top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Toggle Dark/Light Mode", style = MaterialTheme.typography.bodyMedium)
                Switch(
                    checked = darkTheme,
                    onCheckedChange = { onThemeToggle(it) }  // Change theme on toggle
                )
            }
        },
        content = { paddingValues ->
            // Provide content with navigation
            NavHostProvider(
                modifier = modifier,
                navController = navController,
                paddingValues = paddingValues
            )
        },
        bottomBar = {
            BottomAppBarProvider(navController, currentScreen = currentBottomScreen)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    DonationXTheme(darkTheme = false) {
        DonationXApp(modifier = Modifier, darkTheme = false, onThemeToggle = {})
    }
}
