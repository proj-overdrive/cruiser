package com.overdrive.cruiser.models.mapbox

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock

class DatePickerModel {
    private val _startHour = MutableStateFlow(0)
    val startHour: StateFlow<Int> = _startHour

    private val _startMinute = MutableStateFlow(0)
    val startMinute: StateFlow<Int> = _startMinute

    private val _endHour = MutableStateFlow(0)
    val endHour: StateFlow<Int> = _endHour

    private val _endMinute = MutableStateFlow(0)
    val endMinute: StateFlow<Int> = _endMinute

    private val _selectedDate = MutableStateFlow(Clock.System.now().toEpochMilliseconds())
    val selectedDate: StateFlow<Long> = _selectedDate

    fun updateSelectedTimes(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int) {
        _startHour.value = startHour
        _startMinute.value = startMinute
        _endHour.value = endHour
        _endMinute.value = endMinute
    }

    fun updateSelectedDate(date: Long) {
        _selectedDate.value = date
    }
}