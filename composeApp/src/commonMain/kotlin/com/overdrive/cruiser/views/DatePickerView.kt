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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.mapbox.DatePickerModel
import com.overdrive.cruiser.utils.TimeRange
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(datePickerModel: DatePickerModel, onBack: () -> Unit, onSelected: (TimeRange) -> Unit) {
    val state = rememberDatePickerState(initialSelectedDateMillis = datePickerModel.selectedDate.value)
    val start = rememberTimePickerState(initialHour = datePickerModel.startHour.value,
        initialMinute = datePickerModel.startMinute.value)
    val end = rememberTimePickerState(initialHour = datePickerModel.endHour.value,
        initialMinute = datePickerModel.endMinute.value)

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

            SpotOnTimePicker(start, title = "Start Time")

            Spacer(modifier = Modifier.height(8.dp))

            SpotOnTimePicker(end, title = "End Time")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val selectedDateMillis = state.selectedDateMillis ?: Clock.System.now().toEpochMilliseconds()
                    val range = TimeRange(
                        combineDateAndTime(selectedDateMillis, start.hour, start.minute),
                        combineDateAndTime(selectedDateMillis, end.hour, end.minute)
                    )
                    datePickerModel.updateSelectedTimes(start.hour, start.minute, end.hour, end.minute)
                    datePickerModel.updateSelectedDate(selectedDateMillis)
                    onSelected(range)
                    onBack()
               },
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

fun combineDateAndTime(selectedDateMillis: Long, hour: Int, minute: Int = 0): LocalDateTime {
    // Convert the selectedDateMillis to a LocalDate
    // TODO: Why is it one day off
    val selectedDate = Instant.fromEpochMilliseconds(selectedDateMillis).plus(1.days)
        .toLocalDateTime(TimeZone.currentSystemDefault()).date

    // Combine LocalDate with the selected time to create a LocalDateTime
    return LocalDateTime(selectedDate.year, selectedDate.month, selectedDate.dayOfMonth, hour, minute)
}
