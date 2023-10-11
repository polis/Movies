package polis.app.movies.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import polis.app.movies.BuildConfig
import polis.app.movies.data.model.MovieInfo
import polis.app.movies.ui.main.MainViewModel
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val selectedMovie by mainViewModel.selectedMovie.collectAsState()
    Scaffold(
        topBar = {

            MovieDetailsScreenActionBar(
                navController,
                mainViewModel
            )
        },
        content = {
            MovieDetailsScreenContent(selectedMovie)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreenActionBar(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val favoriteMovies by mainViewModel.favoriteMovies.collectAsState()
    val selectedMovie by mainViewModel.selectedMovie.collectAsState()

    TopAppBar(
//        backgroundColor = MaterialTheme.colors.background,
//        elevation = 0.dp,
        title = {
            Text(text = selectedMovie?.title ?: "", style = MaterialTheme.typography.titleLarge)
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
                mainViewModel.clearSelectedMovie()
            }) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        },
        actions = {
            IconButton(onClick = {
                mainViewModel.addToFavorite(selectedMovie?.id ?: 0)
            }) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 5.dp, end = 5.dp)
                        .clickable {
                            mainViewModel.addToFavorite(selectedMovie?.id ?: 0)
                        },
                    imageVector = if (favoriteMovies.any { it == selectedMovie?.id }) {
                        Icons.Filled.Star
                    } else {
                        Icons.Outlined.Star
                    },
                    contentDescription = "Star",
                    tint = if (favoriteMovies.any { it == selectedMovie?.id }) {
                        Color.Yellow
                    } else {
                        Color.Gray
                    }
                )
            }
        }
    )
}

@Composable
fun MovieDetailsScreenContent(
    selectedMovie: MovieInfo?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val posterImg = "https://image.tmdb.org/t/p/original" + selectedMovie?.poster_path

        Timber.d("!!! path: ${"https://image.tmdb.org/t/p/original" + selectedMovie?.poster_path}")

        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(posterImg)
            .setHeader("Autorization", "Bearer ${BuildConfig.API_KEY}")
            .crossfade(true)
            .build()
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequest,
            contentDescription = "Generated image"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(buildAnnotatedString {
                append("Released: ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(selectedMovie?.release_date ?: "")
                }
            })
            Text(buildAnnotatedString {
                append("Rating: ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${selectedMovie?.vote_average ?: ""}")
                }
            })
//            Text(text = selectedMovie?.release_date ?: "")
//            Text(text = "Rating: ${selectedMovie?.vote_average ?: ""}")
        }


        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            text = selectedMovie?.overview ?: ""
        )

    }

}