package polis.app.movies.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import polis.app.movies.domain.MoviesRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val imageRepository: MoviesRepository
): ViewModel() {


    val isLoading = MutableStateFlow(false)
//    val generatedImageUrl = MutableStateFlow<String?>(null)
    val generatedImageDrawable = MutableStateFlow<Drawable?>(null)

    val generatedBitmap = MutableStateFlow<Bitmap?>(null)



    var stabilityAiToken = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("!!! Start fetching remote config")

            val config = FirebaseRemoteConfig.getInstance()
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(10)
                .build()
            config.setConfigSettingsAsync(configSettings).await()
//            config.setDefaultsAsync(R.xml.remote_config_defaults).await()
            config.fetchAndActivate().await()

            config.getString("stability_ai")?.let {
                stabilityAiToken = it
                Timber.d("!!! stability_ai_token: $it")
            }
            Timber.d("!!! End fetching remote config")
        }
    }

}