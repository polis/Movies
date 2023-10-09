package polis.app.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun MainNavigation(
    activity: MainActivity,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {


    NavHost(navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {

            MainScreen(
                activity = activity,
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }

//        composable(Screen.Main.route) {
//            YouScreen(
//                activity = activity,
//                navController = navController,
//                mainActivityViewModel = mainActivityViewModel,
//                accountViewModel = accountViewModel
//            )
//            LaunchedEffect("showYouScreen") {
//                mainActivityViewModel.showBottomBar(true)
//            }
//        }

    }
}