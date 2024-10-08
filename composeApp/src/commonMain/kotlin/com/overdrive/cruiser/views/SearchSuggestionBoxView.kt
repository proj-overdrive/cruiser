package com.overdrive.cruiser.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.endpoints.SearchBoxFetcher
import com.overdrive.cruiser.models.Coordinate
import com.overdrive.cruiser.models.MapViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchSuggestionBoxView(modifier: Modifier, mapViewModel: MapViewModel) {
    val query by mapViewModel.query.collectAsState()
    val suggestions by mapViewModel.suggestions.collectAsState()

    val scope = rememberCoroutineScope()
    val suggestionGenerator = remember { SearchBoxFetcher() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                mapViewModel.updateQuery(it)
                if (it.trim().isNotEmpty()) {
                    scope.launch {
                        mapViewModel.updateSuggestions(
                            suggestionGenerator.fetch(it)
                        )
                    }
                }
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Gray,
                focusedBorderColor = Color.Gray,
                focusedLabelColor = Color.DarkGray,
                textColor = Color.DarkGray,
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
            ),
            shape = RoundedCornerShape(30.dp),
            interactionSource = remember { MutableInteractionSource() },
        )

        // Display search suggestions as a dropdown below the search bar
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
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
                                    mapViewModel.updateCurrentLocation(
                                        Coordinate(
                                            feature.geometry.coordinates[0],
                                            feature.geometry.coordinates[1]
                                        )
                                    )
                                }
                            }
                            focusManager.clearFocus()
                            mapViewModel.updateQuery(suggestion.name)
                            mapViewModel.updateSuggestions(emptyList())
                        },
                    color = Color.DarkGray,
                )
            }
        }
    }
}