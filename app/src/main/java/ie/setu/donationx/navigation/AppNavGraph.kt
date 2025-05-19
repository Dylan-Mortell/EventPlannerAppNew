package ie.setu.donationx.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.donationx.ui.screens.about.AboutScreen
import ie.setu.donationx.ui.screens.budget.BudgetScreen
import ie.setu.donationx.ui.screens.details.DetailsScreen
import ie.setu.donationx.ui.screens.donate.DonateScreen
import ie.setu.donationx.ui.screens.eventplanner.EventPlannerScreen
import ie.setu.donationx.ui.screens.invitation.InvitationScreen
import ie.setu.donationx.ui.screens.login.LoginScreen
import ie.setu.donationx.ui.screens.report.ReportScreen
import ie.setu.donationx.ui.screens.signup.SignupScreen
import ie.setu.donationx.ui.screens.splash.SplashScreen
import ie.setu.donationx.ui.screens.map.MapScreen


@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Splash.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Splash.route) {
            SplashScreen {
                navController.navigate(Login.route) {
                    popUpTo(Splash.route) { inclusive = true }
                }
            }
        }

        composable(Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Report.route) {
                        popUpTo(Login.route) { inclusive = true }
                    }
                },
                onSignupClick = {
                    navController.navigate(Signup.route)
                }
            )
        }

        composable(Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Login.route) {
                        popUpTo(Signup.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Login.route) {
                        popUpTo(Signup.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Donate.route) {
            DonateScreen(modifier = modifier)
        }

        composable(Report.route) {
            ReportScreen(
                modifier = modifier,
                onClickDonationDetails = { donationId ->
                    navController.navigateToDonationDetails(donationId)
                }
            )
        }

        composable(About.route) {
            AboutScreen(modifier = modifier)
        }

        composable(Budget.route) {
            BudgetScreen(modifier = modifier)
        }

        composable(EventPlanner.route) {
            EventPlannerScreen(modifier = modifier)
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt(Details.idArg)
            if (id != null) {
                DetailsScreen()
            }
        }

        composable(Map.route) {
            MapScreen(modifier = modifier)
        }

        composable(Invitation.route) {
            InvitationScreen()
        }

    }

}

private fun NavHostController.navigateToDonationDetails(donationId: String) {
    this.navigate("details/$donationId")
}
