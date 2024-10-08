package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.SettingsViewModel
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.star_filled
import cruiser.composeapp.generated.resources.star_outline
import org.jetbrains.compose.resources.vectorResource
import kotlin.math.roundToInt

@Composable
fun SettingsView(settingsViewModel: SettingsViewModel) {

    // Add Settings title

    // Add Settings icon

    // Add Settings text

    // Add Settings list

    // Add Settings buttons

    // Add Settings footer

    val roundedRating = settingsViewModel.rating.value.roundToInt()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Filled.Person, contentDescription = null)
        Text(text = "Settings")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingStarView(roundedRating)
        }
    }
}