package ie.setu.donationx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Map


interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object Report : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "Report"
    override val route = "report"
}

object Donate : AppDestination {
    override val icon = Icons.Filled.AddCircle
    override val label = "Donate"
    override val route = "donate"
}

object About : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "About"
    override val route = "about"
}

object Details : AppDestination {
    override val icon = Icons.Filled.Details
    override val label = "Details"
    const val idArg = "id"
    override val route = "details/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.IntType }
    )
}

object Login : AppDestination {
    override val icon = Icons.Filled.Person
    override val label = "Login"
    override val route = "login"
}


object Splash {
    const val route = "splash"
}

object Signup : AppDestination {
    override val icon = Icons.Default.Person
    override val label = "Sign Up"
    override val route = "signup"
}

object Budget : AppDestination {
    override val icon = Icons.Filled.AttachMoney
    override val label = "Budget"
    override val route = "budget"
}

object EventPlanner : AppDestination {
    override val icon = Icons.Filled.Event
    override val label = "EventPlanner"
    override val route = "EventPlanner"
}

object Map : AppDestination {
    override val icon = Icons.Default.Map
    override val label = "Map"
    override val route = "map"
}

object Invitation : AppDestination {
    override val icon = Icons.Filled.Event
    override val label = "Invitation"
    override val route = "invitation"
}




val bottomAppBarDestinations = listOf(Donate, Report, About, Budget, EventPlanner, Map, Invitation)
val allDestinations = listOf(Donate, Report, About, Budget, EventPlanner, Map, Invitation)
