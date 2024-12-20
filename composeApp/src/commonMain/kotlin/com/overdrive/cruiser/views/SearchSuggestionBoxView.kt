package com.overdrive.cruiser.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.filter
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource

@Composable
fun SearchSuggestionBoxView(modifier: Modifier, mapViewModel: MapViewModel) {
    val query by mapViewModel.query.collectAsState()
    val suggestions by mapViewModel.suggestions.collectAsState()

    val scope = rememberCoroutineScope()
    val suggestionGenerator = remember { SearchBoxFetcher() }
    val focusManager = LocalFocusManager.current

    val backgroundColor by animateColorAsState(
        targetValue = if (suggestions.isEmpty()) Color.Transparent else Color.White
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        TextField(
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
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                cursorColor = Color.Gray,
                focusedLabelColor = Color.DarkGray,
                textColor = Color.DarkGray,
                unfocusedLabelColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(30.dp),
            interactionSource = remember { MutableInteractionSource() },
            trailingIcon = {
                IconButton(onClick = { mapViewModel.setShowFiltering(true) }) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.filter),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp).padding(end = 16.dp),
                        tint = Color.Gray
                    )
                }
            }
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