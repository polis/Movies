package polis.app.movies.ui.navigation

sealed class Screen(val route: String){


    object Main : Screen("main")
    object MovieDetails : Screen("movie_details")

}

