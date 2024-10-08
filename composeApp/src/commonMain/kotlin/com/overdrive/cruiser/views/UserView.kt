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
import com.overdrive.cruiser.models.UserViewModel
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.star_filled
import cruiser.composeapp.generated.resources.star_outline
import org.jetbrains.compose.resources.vectorResource
import kotlin.math.roundToInt

@Composable
fun UserView(userViewModel: UserViewModel) {
    val roundedRating = userViewModel.rating.value.roundToInt()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Filled.Person,
            contentDescription = null
        )
        Text("Hello, User")
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until roundedRating) {
                Image(
                    modifier = Modifier.size(20.dp),
                    imageVector = vectorResource(Res.drawable.star_filled),
                    contentDescription = null
                )
            }

            for (i in roundedRating until 5) {
                Image(
                    modifier = Modifier.size(20.dp),
                    imageVector = vectorResource(Res.drawable.star_outline),
                    contentDescription = null
                )
            }
        }
    }
}
