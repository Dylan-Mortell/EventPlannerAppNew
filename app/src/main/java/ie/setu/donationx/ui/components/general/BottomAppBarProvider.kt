package ie.setu.donationx.ui.components.general

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ie.setu.donationx.navigation.AppDestination
import ie.setu.donationx.navigation.bottomAppBarDestinations
import ie.setu.donationx.ui.theme.DonationXTheme

@Composable
fun BottomAppBarProvider(
    navController: NavHostController,
    currentScreen: AppDestination
) {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomAppBarDestinations.forEachIndexed { index, navigationItem ->
                val selected = navigationItem == currentScreen

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                ) {
                    Icon(
                        imageVector = navigationItem.icon,
                        contentDescription = navigationItem.label,
                        tint = if (selected) MaterialTheme.colorScheme.secondary else White
                    )
                    Text(
                        text = navigationItem.label,
                        color = if (selected) MaterialTheme.colorScheme.secondary else White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BottomAppBarScreenPreview() {
    DonationXTheme {
        BottomAppBarProvider(
            rememberNavController(),
            bottomAppBarDestinations.get(1)
        )
    }
}
