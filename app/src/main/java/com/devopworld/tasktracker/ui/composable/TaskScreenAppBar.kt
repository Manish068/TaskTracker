package com.devopworld.tasktracker.ui.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.devopworld.tasktracker.ui.theme.LightPrimaryColor
import com.devopworld.tasktracker.ui.theme.fontTypography
import com.devopworld.tasktracker.ui.theme.light50
import com.devopworld.tasktracker.ui.theme.mainBgColor
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TaskScreenAppBar"
@SuppressLint("SimpleDateFormat")
@Composable
fun TaskScreenAppBar( name: String) {
    val context = LocalContext.current
    Log.d(TAG, "TaskTrackerScreen: $name")
    val sdf = SimpleDateFormat("E,dd MMMM")

    // on below line we are creating a variable for
    // current date and time and calling a simple
    // date format in it.
    val currentDateAndTime = sdf.format(Date())
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.mainBgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = currentDateAndTime, style = TextStyle(
                    fontFamily = fontTypography,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light
                )
            )
            Text(
                text = name.capitalize(Locale.ROOT),
                style = TextStyle(
                    fontFamily = fontTypography,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape = CircleShape)
                        .background(LightPrimaryColor),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier.size(30.dp),
                        backgroundColor = Color.Transparent,
                        shape = CircleShape,
                        elevation = 10.dp
                    ) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                }
            }
        }


    }
}

@Preview(
    device =Devices.NEXUS_6
)
@Composable
fun previewTaskScreenAppBar(){
    TaskScreenAppBar(name = "Manish Pandit")
}