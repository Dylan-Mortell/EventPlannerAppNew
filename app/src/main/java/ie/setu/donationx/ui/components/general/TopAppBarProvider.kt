package ie.setu.donationx.ui.components.general

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.donationx.navigation.AppDestination
import ie.setu.donationx.navigation.Donate
import ie.setu.donationx.ui.theme.DonationXTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProvider(
    currentScreen: AppDestination,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {})
{
    TopAppBar(
        title = {
            Text(
                text = currentScreen.label,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            else
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Button",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )

        },
        actions = { DropDownMenu() }
    )
}


@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    DonationXTheme {
        TopAppBarProvider(Donate,
            true)
    }
}