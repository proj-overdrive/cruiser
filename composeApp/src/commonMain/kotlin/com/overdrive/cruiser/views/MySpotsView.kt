package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.MySpotsViewModel
import kotlinx.coroutines.flow.onEmpty

@Composable
fun MySpotsView(mySpotsViewModel: MySpotsViewModel, onAddSpotClick: () -> Unit) {

    // add a button that will take you to the AddSpotView
    Column {
        TopAppBar(
            backgroundColor = Color.White,
            modifier = Modifier.shadow(8.dp),
            content = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "My Spots",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        )

        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                if (mySpotsViewModel.spots.value.isEmpty()) {
                    Text("No spots yet, add one!")
                } else {
                    mySpotsViewModel.spots.value.forEach { spot ->
                        // add a SpotView for each spot
                        Text(spot.id)
                    }
                }
            }
            Button(
                onClick = onAddSpotClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .size(64.dp)
            ) {
                Text("+")
            }
        }
    }
}