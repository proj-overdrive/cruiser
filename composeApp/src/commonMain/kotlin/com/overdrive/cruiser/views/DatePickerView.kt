package com.overdrive.cruiser.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(state: DatePickerState, onBack: () -> Unit) {
    Column (Modifier.fillMaxSize().background(color = Color(0xFFF5F5F5))) {
        SpotOnTopBar("Filter Spots") { onBack() }

        Column(modifier = Modifier.padding(16.dp)) {
            DatePicker(
                state = state,
                colors = DatePickerDefaults.colors(
                    todayContentColor = Color.Black,
                    todayDateBorderColor = Color(0xFFF9784B),
                    selectedDayContainerColor = Color(0xFFF9784B),
                ),
                showModeToggle = false,
                title = null,
                headline = null
            )

            SpotOnTimePicker(title = "Start Time")

            Spacer(modifier = Modifier.height(8.dp))

            SpotOnTimePicker(title = "End Time")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onBack() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF9784B)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text("Submit", color = Color.White)
            }
        }
    }
}
