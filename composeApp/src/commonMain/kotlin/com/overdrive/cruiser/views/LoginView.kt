package com.overdrive.cruiser.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.overdrive.cruiser.auth.GoogleUser
import cruiser.composeapp.generated.resources.Res
import cruiser.composeapp.generated.resources.apple_logo
import cruiser.composeapp.generated.resources.background_image
import cruiser.composeapp.generated.resources.facebook_logo
import cruiser.composeapp.generated.resources.google_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginView(onAuthenticated: () -> Unit) {
    var signedInUser: GoogleUser? by remember { mutableStateOf(null) }

    GoogleButtonUiContainer(onGoogleSignInResult = { googleUser ->
        val idToken = googleUser?.idToken
        // TODO: Use the idToken to authenticate the user
        signedInUser = googleUser

        if (idToken != null) {
            onAuthenticated()
        }
    }) {
        val onGoogleClick = { this.onClick() }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.background_image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(140.dp))

                Text(
                    text = "Connect",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Start),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Color.Black.copy(alpha = 0.5f))
                        .padding(24.dp),
                ) {
                    Button(
                        onClick = { onGoogleClick() },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                            .background(color = Color.White),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.google_logo),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(50.dp),
                            )
                            Text(
                                text = "Continue With Google",
                                color = contentColorFor(Color.Black),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onGoogleClick() },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                            .background(color = Color.White),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.apple_logo),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.align(Alignment.CenterStart)
                                    .padding(start = 11.dp)
                            )

                            Text(
                                text = "Continue With Apple",
                                color = contentColorFor(Color.Black),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onGoogleClick() },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(12.dp))
                            .background(color = Color.White),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.facebook_logo),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.align(Alignment.CenterStart)
                                    .padding(start = 10.dp)
                            )

                            Text(
                                text = "Continue With Facebook",
                                color = contentColorFor(Color.Black),
                            )
                        }
                    }
                }

            }
        }
    }
}