package com.kursatkumsuz.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.kursatkumsuz.domain.model.user.UserModel

@Composable
fun SettingsScreen(navController: NavHostController) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val userInfoState = viewModel.userInfoState.value
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Settings",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )

        // Profile Section
        Spacer(modifier = Modifier.height(10.dp))
        UserInfoSection(userInfoState)

        // Notification Section
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Notifications",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        NotificationSettingSection(viewModel)

        // Help Section
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Help",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        ContactSupport()
        Spacer(modifier = Modifier.height(10.dp))
        PrivacyPolicy()

        // Sign Out Section
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Sign Out",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LogoutSection(navController, viewModel)

        // Logout Section
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "App ver 1.0.0",
            color = Color.LightGray,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun UserInfoSection(user: com.kursatkumsuz.domain.model.user.UserModel) {
    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .background(Color(0x9A242F3D)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.pic_empty),
                contentDescription = "profile image",
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .padding(5.dp)
            )

            Text(
                text = user.name,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = user.email,
                color = Color.LightGray,
                fontSize = 14.sp,
            )
        }
    }
}


@Composable
fun NotificationSettingSection(viewModel: SettingsViewModel) {
    val notificationState = viewModel.notificationState.value
    val checkState = remember { mutableStateOf(notificationState) }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 20.dp)
            .background(Color(0x9A242F3D)),
    ) {
        Text(
            text = "Notifications",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )

        Switch(
            modifier = Modifier
                .align(
                    Alignment.CenterEnd
                ),
            checked = checkState.value, onCheckedChange = {
                checkState.value = it
                viewModel.saveNotificationPreference(it)
            })
    }
}

@Composable
fun LogoutSection(navController: NavHostController, viewModel: SettingsViewModel) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 20.dp)
            .background(Color(0x9A242F3D)),
    ) {
        Text(
            text = "Logout",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )
        IconButton(
            modifier = Modifier
                .align(
                    Alignment.CenterEnd
                ),
            onClick = {
                viewModel.signOut()
                navController.popBackStack()
                navController.navigate("signin_screen")
            }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                tint = Color.White,
                contentDescription = "logout icon",
            )
        }

    }
}


@Composable
fun ContactSupport() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 20.dp)
            .background(Color(0x9A242F3D)),
    ) {
        Text(
            text = "Contact Support",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )
        IconButton(
            modifier = Modifier
                .align(
                    Alignment.CenterEnd
                ),
            onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                tint = Color.White,
                contentDescription = "contact icon",
            )
        }

    }
}

@Composable
fun PrivacyPolicy() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 20.dp)
            .background(Color(0x9A242F3D)),
    ) {
        Text(
            text = "Privacy Policy",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )
        IconButton(
            modifier = Modifier
                .align(
                    Alignment.CenterEnd
                ),
            onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                tint = Color.White,
                contentDescription = "privacy icon",
            )
        }

    }
}