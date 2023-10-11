package polis.app.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import polis.app.movies.ui.details.MovieDetailsScreen
import polis.app.movies.ui.main.MainScreen
import polis.app.movies.ui.main.MainViewModel

@Composable
fun MainNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    NavHost(navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }

        composable(Screen.MovieDetails.route) {
            MovieDetailsScreen(
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }

    }
}