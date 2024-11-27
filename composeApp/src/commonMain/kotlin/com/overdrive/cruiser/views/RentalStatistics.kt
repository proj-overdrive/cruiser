package com.overdrive.cruiser.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.clock_unboxed
import cruiser.composeapp.generated.resources.visa_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

const val MINUTES_IN_DAY = 1440

@Composable
fun RentalStatisticsView(onBackClick: () -> Unit, onResetUserScreen: () -> Unit) {
    val monthDensityMap = mapOf(
        1 to 0.0f, 2 to 0.6f, 3 to 0.4f, 4 to 0.8f, 5 to 0.9f, 6 to 0.0f, 7 to 0.2f,
        8 to 0.6f, 9 to 0.0f, 10 to 0.1f, 11 to 0.3f, 12 to 0.7f, 13 to 0.9f, 14 to 0.2f,
        15 to 0.3f, 16 to 1.0f, 17 to 0.5f, 18 to 0.6f, 19 to 0.0f, 20 to 0.4f, 21 to 0.9f,
        22 to 0.8f, 23 to 0.3f, 24 to 0.9f, 25 to 0.5f, 26 to 0.3f, 27 to 0.3f, 28 to 0.2f,
        29 to 0.0f, 30 to 0.0f
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF2F2F2))
        .verticalScroll(rememberScrollState())
    ) {
        SpotOnTopBar("Rental Statistics", onBackClick = { onBackClick(); onResetUserScreen() })

        Column(modifier = Modifier.padding(16.dp)) {
            SpotOnMonthActivity(monthDensityMap)

            Spacer(modifier = Modifier.size(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                SpotOnDaysRented(monthDensityMap, modifier = Modifier.weight(0.45f))

                Spacer(modifier = Modifier.size(16.dp))

                SpotOnIncome(modifier = Modifier.weight(0.45f))
            }

            Spacer(modifier = Modifier.size(16.dp))

            SpotOnPaymentMethod()
        }
    }
}

@Composable
fun SpotOnMonthActivity(monthDensityMap: Map<Int, Float>) {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column {
            Text("Monthly Activity", fontSize = 20.sp,
                modifier = Modifier.padding(8.dp, 8.dp, 0.dp, 16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("<", fontWeight = FontWeight.Bold)
                Text("November 2024", fontWeight = FontWeight.Bold)
                Text(">", fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                daysOfWeek.forEach { day ->
                    Box(
                        modifier = Modifier.background(Color.Transparent)
                            .size(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(day, fontWeight = FontWeight.Bold)
                    }
                }
            }

            val startDayOffset = 5
            val totalCells = monthDensityMap.size + startDayOffset

            for (i in 0 until totalCells step 7) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (j in 0..6) {
                        val dayIndex = i + j
                        var color = Color.Transparent
                        var textBox = @Composable { Text("") }

                        if (dayIndex in startDayOffset..<totalCells) {
                            val day = dayIndex - startDayOffset + 1
                            val fontColor = if ((monthDensityMap[day] ?: 0.0f) > 0.5f) Color.White else Color.Black
                            color = Color(0xF74600).copy(alpha = monthDensityMap[day] ?: 0.0f)
                            textBox = { Text(day.toString(), color = fontColor) }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f/7)
                                .aspectRatio(1f)
                                .background(color, shape = CircleShape)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            textBox()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpotOnDaysRented(monthDensityMap: Map<Int, Float>, modifier: Modifier = Modifier) {
    val minutesRented = monthDensityMap.values.sum() * MINUTES_IN_DAY
    val daysRented = (minutesRented / MINUTES_IN_DAY).toInt()
    val hoursRented = (minutesRented / 60).toInt()
    val minutesLeft = (minutesRented % 60).toInt()

    Box(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
            .height(100.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.clock_unboxed),
                    contentDescription = "clock",
                    modifier = Modifier.size(30.dp).padding(end = 4.dp)
                )
                Text("$daysRented Days", fontSize = 20.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$hoursRented Hours, $minutesLeft Minutes", fontSize = 11.sp)
            }

            for (i in 0..2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (j in 0..9) {
                        Box(
                            modifier = Modifier
                                .weight(1f/10)
                                .aspectRatio(1f)
                                .background(if (i * 10 + j < daysRented) Color(0xFFF74600)
                                    else Color.LightGray,
                                    shape = CircleShape)
                                .clip(CircleShape)
                        ) {
                            Text("")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SpotOnIncome(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
            .height(100.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text("Income", fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Week")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text("$167", color = Color(0xFF3DBA53))
                }

                Canvas(
                    modifier = Modifier
                        .height(50.dp)
                ) {
                    val startX = size.width / 2
                    val startY = 0f
                    val endX = size.width / 2
                    val endY = size.height
                    drawLine(
                        color = Color.Black,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 4f
                    )
                }

                Column(modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Month")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text("$824", color = Color(0xFF3DBA53))
                }
            }
        }
    }
}

@Composable
fun SpotOnPaymentMethod(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .padding(16.dp)
            .height(100.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.visa_logo),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.width(50.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text("Direct Debit", fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text("Main payments to card", fontSize = 14.sp, color = Color.Gray)
            Text("**** **** **** 1234")
        }
    }
}

