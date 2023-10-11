package polis.app.movies.ui.main

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import polis.app.movies.BuildConfig
import polis.app.movies.data.model.MovieInfo
import polis.app.movies.ui.navigation.Screen
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    Column {
        MovieSearchBar(mainViewModel)
        MovieContent(navController, mainViewModel)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieContent(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    val isLoading by mainViewModel.isLoading.collectAsState()

//    val movies by mainViewModel.movies.collectAsState()
    val pagedMovies = mainViewModel.pagedMovies.collectAsLazyPagingItems()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
    ) {

        LazyColumn() {
            items(pagedMovies.itemCount) { index ->
                val item = pagedMovies[index]
                item?.let {
                    Row(
                        Modifier.animateItemPlacement(
                            tween(durationMillis = 250)
                        )
                    ) {
                        MovieRow(
                            mainViewModel,
                            navController,
                            it
                        )
                    }
                }
            }
//            items(movies.results) { movie ->
//                movie?.let {
//                    Row(
//                        Modifier.animateItemPlacement(
//                            tween(durationMillis = 250)
//                        )
//                    ) {
//                        MovieRow(
//                            activity,
//                            mainViewModel,
//                            navController,
//                            it
//                        )
//                    }
//                }
//
//            }
        }


        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

    }
}

@Composable
fun MovieRow(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    movieInfo: MovieInfo
) {

    val favoriteMovies by mainViewModel.favoriteMovies.collectAsState()

    Card(
        modifier = Modifier
//            .height(100.dp)
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, top = 3.dp, bottom = 2.dp)
            .clickable {

                mainViewModel.selectMovie(movieInfo)
                navController.navigate(Screen.MovieDetails.route)

            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            val posterImg = "https://image.tmdb.org/t/p/w500" + movieInfo.poster_path

            Timber.d("!!! path: ${"https://image.tmdb.org/t/p/w500" + movieInfo.poster_path}")

            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(posterImg)
                .setHeader("Autorization", "Bearer ${BuildConfig.API_KEY}")
                .crossfade(true)
                .build()
            AsyncImage(
                modifier = Modifier.height(100.dp),
                model = imageRequest,

                contentDescription = "Generated image"
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    modifier = Modifier

                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth(), text = movieInfo.title, fontSize = 22.sp
                )
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth(), text = movieInfo.release_date, fontSize = 16.sp
                )
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.2f)
                    .clickable { },
                contentAlignment = TopEnd
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 5.dp, end = 5.dp)
                        .clickable {
                            mainViewModel.addToFavorite(movieInfo.id)
                        },
                    imageVector = if (favoriteMovies.any { it == movieInfo.id }) {
                        Icons.Filled.Star
                    } else {
                        Icons.Outlined.Star
                    },
                    contentDescription = "Star",
                    tint = if (favoriteMovies.any { it == movieInfo.id }) {
                        Color.Yellow
                    } else {
                        Color.Gray
                    }
                )

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchBar(mainViewModel: MainViewModel) {
    val searchQuery by mainViewModel.searchQuery.collectAsState()
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { value ->
                    mainViewModel.search(value)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                            mainViewModel.search("")
//                            mainViewModel.enableSearch(false)
                }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
    )


    BackHandler {
        mainViewModel.search("")
    }
}