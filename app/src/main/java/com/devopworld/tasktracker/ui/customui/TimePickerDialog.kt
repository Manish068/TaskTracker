package com.devopworld.tasktracker.ui.customui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.widget.picker.ItemStyles
import com.compose.widget.picker.PickerContainer
import com.compose.widget.picker.TimePicker
import com.devopworld.tasktracker.R
import com.devopworld.tasktracker.ui.theme.fontTypography
import com.devopworld.tasktracker.ui.theme.mainBgColor
import com.devopworld.tasktracker.util.CommonFunction
import com.devopworld.tasktracker.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

private const val TAG = "TimePickerDialog"

@Composable
fun TimePickerDialog(
    viewModel: MainViewModel,
    fromStart: Boolean,
    fromEnd: Boolean,
    onDismiss: (Boolean) -> Unit,
    onDateTimeSave: (Long, String) -> Unit
) {
    var time by remember { mutableStateOf(LocalTime.now()) }
    val sdf = SimpleDateFormat("E,dd")

    val startTime by viewModel.taskStartTime

    // on below line we are creating a variable for
    // current date and time and calling a simple
    // date format in it.
    val currentDateAndTime = sdf.format(Date())

    val context = LocalContext.current


    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
                onDismiss(openDialog.value)
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Column() {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 2.dp),
                            text = currentDateAndTime,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = Color.DarkGray,
                                fontFamily = fontTypography,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp
                            )
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp, bottom = 10.dp),
                            text = "Select start time",
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                color = Color.Black,
                                fontFamily = fontTypography,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        )

                        PickerContainer(
                            modifier = Modifier.padding(top = 10.dp),
                            backgroundColor = MaterialTheme.colors.mainBgColor,
                            cornerRadius = 15.dp,
                            fadingEdgeLength = 50.dp
                        ) {
                            TimePicker(
                                modifier = Modifier.height(130.dp),
                                itemHeight = 40.dp,
                                is24TimeFormat = true,
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
                                onTimeChanged = { changeTime ->
                                    time = changeTime
                                },
                                currentTime = time,
                            )
                        }
                    }

                }
            },
            buttons = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            Log.d(
                                TAG,
                                "TimePickerDialog: ${CommonFunction.TodayDate()} ${
                                    time.toString().substring(0, 5)
                                } "
                            )

                            val dateTime =
                                "${CommonFunction.TodayDate()} ${time.toString().substring(0, 5)}"
                            if (fromEnd) {
                                if (startTime > CommonFunction.dateToTimestamp(dateTime)!!) {
                                    Toast.makeText(
                                        context,
                                        R.string.warning_for_time_selection,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    onDateTimeSave(
                                        CommonFunction.dateToTimestamp(dateTime)!!,
                                        time.toString().substring(0, 5)
                                    )
                                    openDialog.value = false
                                    onDismiss(openDialog.value)
                                }
                            } else {
                                onDateTimeSave(
                                    CommonFunction.dateToTimestamp(dateTime)!!,
                                    time.toString().substring(0, 5)
                                )
                                openDialog.value = false
                                onDismiss(openDialog.value)
                            }


                        },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 20.dp, bottom = 15.dp)
                    ) {
                        Text(text = "Save")
                    }
                }

            },
            shape = RoundedCornerShape(10.dp)
        )
    }
}


//@Preview(
//    showBackground = true,
//    device = Devices.NEXUS_6
//)
//@Composable
//fun previewTimePickerDialog() {
//    TimePickerDialog(,true,false,onDismiss = {}, onDateTimeSave = { time, date -> })
//}