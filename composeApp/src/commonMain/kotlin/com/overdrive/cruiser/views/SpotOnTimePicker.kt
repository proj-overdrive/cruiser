package com.overdrive.cruiser.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.overdrive.cruiser.utils.SpotOnTimePickerDialog
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.saved_spots
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotOnTimePicker(title: String, modifier: Modifier = Modifier) {
    val timePickerState = rememberTimePickerState(initialHour = 0, initialMinute = 0)
    var showTimePicker by remember { mutableStateOf(false) }

    SpotOnField(modifier = modifier.fillMaxWidth()) {
        Row(modifier = modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Column(modifier = modifier.padding(bottom = 8.dp)) {
                Text(text = title, fontSize = 12.sp, color = Color.Gray)

                val selectedHour = timePickerState.hour
                val selectedMinute = timePickerState.minute

                val hourIn12Format = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                val amPm = if (selectedHour < 12) "AM" else "PM"

                Text(
                    "${hourIn12Format.toString().padStart(2, '0')}:" +
                            "${selectedMinute.toString().padStart(2, '0')} $amPm"
                )
            }

            Spacer(modifier = modifier.weight(1f))

            IconButton(onClick = { showTimePicker = true }, modifier.padding(0.dp, 16.dp)) {
                Icon(
                    imageVector = vectorResource(Res.drawable.saved_spots),
                    contentDescription = null
                )
            }
        }
    }

    if (showTimePicker) {
        SpotOnTimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) { Text("Cancel") }
            },
        )
        {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = Color(0xFFF0F0F0),
                    clockDialSelectedContentColor = Color.Black,
                    clockDialUnselectedContentColor = Color.Black,
                    selectorColor = Color(0xFFF9784B),
                    containerColor = Color.White,
                    periodSelectorSelectedContentColor = Color.Black,
                    periodSelectorUnselectedContentColor = Color.Black,
                    periodSelectorSelectedContainerColor = Color(0xFFF9784B),
                    periodSelectorUnselectedContainerColor = Color(0xFFF0F0F0),
                    timeSelectorSelectedContainerColor = Color(0xFFD3D3D3),
                    timeSelectorUnselectedContainerColor = Color(0xFFF0F0F0),
                    timeSelectorSelectedContentColor = Color.Black,
                    timeSelectorUnselectedContentColor = Color.Black,
                )
            )
        }
    }
}
