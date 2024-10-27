package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.overdrive.cruiser.models.UserViewModel
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.clock
import cruiser.composeapp.generated.resources.email
import cruiser.composeapp.generated.resources.location
import cruiser.composeapp.generated.resources.notification
import cruiser.composeapp.generated.resources.payment
import cruiser.composeapp.generated.resources.phone
import cruiser.composeapp.generated.resources.profile_picture
import cruiser.composeapp.generated.resources.report
import cruiser.composeapp.generated.resources.security
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun UserView(userViewModel: UserViewModel, onLogOut: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(color = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF5F5F5))
            .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(36.dp))
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                imageVector = vectorResource(Res.drawable.profile_picture),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(36.dp))
            Column {
                Text("Preferences")
                ProfileButton(
                    "Notifications",
                    Res.drawable.notification,
                    onClick = { /*TODO*/ })
                ProfileButton(
                    "Personal Information",
                    Res.drawable.email,
                    onClick = { /*TODO*/ })
                ProfileButton(
                    "Login & Security",
                    Res.drawable.phone,
                    onClick = { /*TODO*/ })
                ProfileButton(
                    "Rental History",
                    Res.drawable.clock,
                    onClick = { /*TODO*/ })

                Spacer(modifier = Modifier.height(16.dp))

                Text("Billing")
                ProfileButton(
                    "Payment Methods",
                    Res.drawable.payment,
                    onClick = { /*TODO*/ })
                ProfileButton(
                    "Billing Address",
                    Res.drawable.location,
                    onClick = { /*TODO*/ })

                Spacer(modifier = Modifier.height(16.dp))

                Text("Other")
                ProfileButton(
                    "Report a Problem",
                    Res.drawable.report,
                    onClick = { /*TODO*/ })
                ProfileButton(
                    "Security Policy",
                    Res.drawable.security,
                    onClick = { /*TODO*/ })
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onLogOut() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF9784B)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(2.dp)
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text(
                    text = "Log Out",
                    modifier = Modifier.padding(0.dp, 4.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ProfileButton(text: String, icon: DrawableResource, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(2.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                imageVector = vectorResource(icon),
                contentDescription = null
            )
            Text(text, modifier = Modifier.padding(4.dp, 0.dp))
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.AutoMirrored.Outlined.ArrowForward,
                modifier = Modifier.size(24.dp).padding(4.dp),
                contentDescription = null
            )
        }
    }
}