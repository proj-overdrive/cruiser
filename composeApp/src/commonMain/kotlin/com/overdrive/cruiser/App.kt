package com.overdrive.cruiser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.overdrive.cruiser.endpoints.SpotFetcher
import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.views.NavigationBar
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    var spots by remember { mutableStateOf(emptyList<Spot>()) }

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            LaunchedEffect(true){
                scope.launch {
                    spots = try {
                        SpotFetcher().fetch()
                    } catch (e: Exception) {
                        println(e.message ?: "Error")
                        emptyList()
                    }
                }
            }
            NavigationBar(spots)
        }
    }
}
