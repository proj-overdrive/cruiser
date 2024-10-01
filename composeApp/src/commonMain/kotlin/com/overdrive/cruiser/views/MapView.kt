package com.overdrive.cruiser.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.endpoints.SearchBoxFetcher
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.Spot
import com.overdrive.cruiser.models.mapbox.Suggestion
import kotlinx.coroutines.launch

/**
 * A composable that displays a map with a search box and suggestions.
 *
 * @param spots The spots to display on the map.
 */
@Composable
fun MapView(spots: List<Spot>) {
    val scope = rememberCoroutineScope()
    val suggestionGenerator = remember { SearchBoxFetcher() }

    var query by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf(emptyList<Suggestion>()) }
    var currentLocation by remember { mutableStateOf(Coordinate.DEFAULT) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // SpotMapView is rendered first, at the bottom layer
        SpotMapView(
            modifier = Modifier.fillMaxSize(),
            location = currentLocation,
            spots = spots,
        )

        // Search box and suggestion list are overlaid on top of the map
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .background(Color.White.copy(alpha = 0.8f))
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    if (query.isNotEmpty()) {
                        scope.launch {
                            suggestions = suggestionGenerator.fetch(query)
                        }
                    }
                },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )

            // Display search suggestions as a dropdown below the search bar
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(suggestions) { suggestion ->
                    Text(
                        text = suggestion.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                scope.launch {
                                    suggestionGenerator.fetch(suggestion)?.let { feature ->
                                        currentLocation = Coordinate(
                                            feature.geometry.coordinates[0],
                                            feature.geometry.coordinates[1]
                                        )
                                    }
                                }
                                query = ""
                                suggestions = emptyList()
                            }
                    )
                }
            }
        }
    }
}