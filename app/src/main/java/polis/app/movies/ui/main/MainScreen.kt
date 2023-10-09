package polis.app.movies.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    activity: MainActivity,
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    var prompt by rememberSaveable { mutableStateOf("") }
    val isLoading by mainViewModel.isLoading.collectAsState()
    val generatedBitmap by mainViewModel.generatedBitmap.collectAsState()

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
//        ) {
//            OutlinedTextField(
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(24.dp),
//                value = prompt,
//                onValueChange = { prompt = it },
//                placeholder = { Text(stringResource(R.string.what_do_you_want_to_create)) },
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = MaterialTheme.colorScheme.surface,
//                    unfocusedBorderColor = MaterialTheme.colorScheme.surface
//                )
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Text(
//                    text = "${prompt.length}/500",
////                    modifier = Modifier
////                        .padding(start = 16.dp, end = 4.dp),
//                    color = Color.Gray,
//                    fontSize = 14.sp
//                )
//                OutlinedIconButton(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .padding(8.dp),
//                    onClick = { /*TODO*/ },
//                    shape = RoundedCornerShape(16.dp),
//                    border = BorderStroke(
//                        color = Color.Gray,
//                        width = 1.dp
//                    )
//                ) {
//                    Icon(Icons.Default.Clear, "Clear", tint = Color.Gray)
//                }
//
//            }
//
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//        OutlinedIconButton(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp),
//            onClick = {
//
//                      mainViewModel.generateImage(prompt)
//
//            },
////            colors = IconButtonDefaults.outlinedIconButtonColors(
////                containerColor = MaterialTheme.colorScheme.primary
////            )
//        )
//        {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
////                Icon(
////                    Icons.TwoTone.Image,
////                    "Generate image",
////                    modifier = Modifier.size(40.dp),
////                    tint = Color.Gray
////                )
//
//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(text = "Generate".uppercase(), fontSize = 22.sp)
//                    Text(
//                        text = "Watch an advertisement",
//                        modifier = Modifier.padding(bottom = 8.dp),
//                        fontSize = 12.sp
//                    )
//                }
//            }
//
//
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedIconButton(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp),
//            onClick = {
//
////                mainViewModel.saveToGallery( generatedImage ?: url)
//                mainViewModel.saveImageToStorage(activity)
//
//            },
//        )
//        {
//            Text(text = "Download".uppercase(), fontSize = 22.sp)
//        }
//
//
//        Spacer(modifier = Modifier.height(16.dp))
//
////        if (generatedImage != null) {
//
//        val url = "https://media.istockphoto.com/id/1181605840/pl/zdj%C4%99cie/zach%C3%B3d-s%C5%82o%C5%84ca-w-latarni-morskiej-souter.jpg?s=1024x1024&w=is&k=20&c=w3-VgPJi74IvdlwoVRlFCLV7tTvq0jwa5VwnphO-35k="
//            val imageRequest = ImageRequest.Builder(LocalContext.current)
//                .data(generatedBitmap ?: url)
//                .crossfade(true)
//                .build()
//
//
//            AsyncImage(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                model = imageRequest,
//
//                contentDescription = "Generated image",
//                onSuccess = { success ->
//                    val drawable = success.result.drawable
////                    mainViewModel.saveImage(activity, drawable)
//                    mainViewModel.generatedImageDrawable.value = drawable
//                }
//            )
//
//
////        }
//
//    }


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
