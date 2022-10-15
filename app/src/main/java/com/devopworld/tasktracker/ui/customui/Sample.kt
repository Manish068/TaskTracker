package com.compose.widget.sample

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.widget.picker.ItemStyles
import com.compose.widget.picker.PickerContainer
import com.compose.widget.picker.TimePicker
import com.devopworld.tasktracker.ui.theme.mainBgColor
import java.time.LocalTime

private const val TAG = "Sample"
@Composable
fun BottomTimePicker(
    currentTime: LocalTime? = null,
    is24TimeFormat: Boolean
) {

    var time by remember { mutableStateOf(currentTime ?: LocalTime.now()) }

        PickerContainer(
            modifier = Modifier.padding(18.dp),
            backgroundColor = MaterialTheme.colors.mainBgColor,
            cornerRadius = 15.dp, fadingEdgeLength = 60.dp
        ) {
            TimePicker(
                modifier = Modifier.height(130.dp),
                itemHeight = 40.dp,
                is24TimeFormat = is24TimeFormat,
                itemStyles = ItemStyles(
                    defaultTextStyle = TextStyle(
                        Color.DarkGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    selectedTextStyle = TextStyle(
                        Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                ),
                onTimeChanged = {
                    Log.d(TAG, "BottomTimePicker: $it")
                    time = it
                },
                currentTime = time
            )
        }
}

@Preview(
    device = Devices.NEXUS_6
)
@Composable
fun previewPicker(){
    BottomTimePicker(is24TimeFormat = true)
}
