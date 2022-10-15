package com.devopworld.tasktracker.ui.customui

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devopworld.tasktracker.data.model.TaskData
import com.devopworld.tasktracker.util.CommonFunction
import com.devopworld.tasktracker.util.TASKSTATUS
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val TAG = "Timer"

@Composable
fun Timer(
    index: Int,
    taskData: TaskData,
    handleColor: Color,
    inActiveColor: Color,
    activeBarColor: Color,
    initialValue: Float = 1f,
    strokeWidth: Dp = 2.dp,
    onCompletedTask: () -> Unit
) {
    val firstTask = index == 0 && taskData.status == TASKSTATUS.INCOMPLETE.toString()
    Log.d(TAG, "Timer: $firstTask")
    if (firstTask) {

        var currentTime by remember {
            mutableStateOf(CommonFunction.getCurrentDateTimeInTimestamp())
        }

        val totalDifferenceEndToStart: Long = taskData.task_end_time - taskData.task_start_time
        val seconds = totalDifferenceEndToStart.toInt() / 1000
        val totalTime by remember {
            mutableStateOf(seconds * 1000L)
        }

        var value by remember {
            mutableStateOf(initialValue)
        }


        var isTimerRunning by remember {
            mutableStateOf(false)
        }

        var totalTimeTimerRun by remember {
            mutableStateOf(
                //agar current time start time se jada hai aur end time se kam hai toh
                if (currentTime > taskData.task_start_time && currentTime < taskData.task_end_time) {
                    //end time se current time ka difference nikalenge or adhe se start krenge from total time
                    val milliseconds: Long = taskData.task_end_time - currentTime
                    val seconds = (milliseconds.toInt() / 1000) * 1000L
                    seconds
                } else {
                    totalTime
                }
            )
        }
        //agar current samay end time se jada hua toh ye task complete ho chuka hai
        if (currentTime > taskData.task_end_time) {
            //task is completed
            onCompletedTask()
        } else if (currentTime > taskData.task_start_time && currentTime < taskData.task_end_time) {
            //timer start ring
            isTimerRunning = true
            //current time fetch ni krenge
        } else if (currentTime != taskData.task_start_time) { // agar current time aur start time same ni hai to current time fetch krte rahenge
            startTimer = true
            startUpdates { current ->
                currentTime = current
            }
        } else if (currentTime == taskData.task_start_time) { //agar current time aur start time same hai toh
            //timer start krdenge
            isTimerRunning = true
            if (startTimer) { // timer start hai to band krdenge
                startTimer = false
                stopUpdates()
            }
        }

        var size by remember {
            mutableStateOf(IntSize.Zero)
        }






        LaunchedEffect(key1 = totalTimeTimerRun, key2 = isTimerRunning) {
            Log.d(TAG, "Outside Launched Effect: $totalTimeTimerRun  $isTimerRunning")
            if (totalTimeTimerRun > 0 && isTimerRunning) {
                Log.d(TAG, "Inside Launched Effect: $totalTimeTimerRun  $isTimerRunning")
                delay(1000L)
                totalTimeTimerRun -= 1000L
                value = totalTimeTimerRun / totalTime.toFloat()
            }
        }

        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.onSizeChanged {
                    size = it
                }) {
                Canvas(modifier = Modifier.size(50.dp)) {
                    drawArc(
                        color = inActiveColor,
                        startAngle = -215f,
                        sweepAngle = 250f,
                        useCenter = false,
                        size = Size(size.width.toFloat(), size.height.toFloat()),
                        style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                    )

                    drawArc(
                        color = activeBarColor,
                        startAngle = -215f,
                        sweepAngle = 250f * value,
                        useCenter = false,
                        size = Size(size.width.toFloat(), size.height.toFloat()),
                        style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                    )

                    val center = Offset(size.width / 2f, size.height / 2f)
                    val beta = (250f * value + 145f) * (PI / 180f).toFloat()
                    val r = size.width / 2f
                    val a = cos(beta) * r
                    val b = sin(beta) * r
                    drawPoints(
                        listOf(Offset(center.x * a, center.y * b)),
                        pointMode = PointMode.Points,
                        color = handleColor,
                        strokeWidth = (strokeWidth * 3f).toPx(),
                        cap = StrokeCap.Round
                    )
                }


                val longtoSeconds = totalTimeTimerRun / 1000L
                val hours: Int = (longtoSeconds / 3600).toInt()
                val minutes: Int = ((longtoSeconds % 3600) / 60).toInt()
                val seconds: Int = ((longtoSeconds % 3600) % 60).toInt()

                Text(
                    text =
                    if (hours > 0) {
                        "${CommonFunction.fillText(hours)}:${CommonFunction.fillText(minutes)}"
                    } else {
                        "${CommonFunction.fillText(minutes)}:${CommonFunction.fillText(seconds)}"
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                if (isTimerRunning && totalTimeTimerRun > 0L)
                else if (!isTimerRunning && totalTimeTimerRun >= 0L)
                else {
                    Log.d(
                        TAG,
                        "Timer: Goes to update task \nTask Title: ${taskData.taskTitle}" +
                                "\nTask StartTime: ${taskData.task_start_time} " +
                                "\nTotalTimerRunValue $totalTimeTimerRun: " +
                                "\nisTimerRunning: $isTimerRunning"
                    )
                    isTimerRunning = false
                    onCompletedTask()
                }
            }

        }

    }
//        Button(
//            modifier = Modifier.wrapContentSize(),
//            onClick = {
//                if (currentTime <= 0L) {
//                    currentTime = totalTime
//                    isTimerRunning = true
//                } else {
//                    isTimerRunning = !isTimerRunning
//                }
//            },
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = if (!isTimerRunning || currentTime <= 0L) {
//                    Color.Green
//                } else {
//                    Color.Red
//                }
//            )
//        ) {
//            Text(
//                text =
//                if (isTimerRunning && currentTime > 0L) "Stop"
//                else if (!isTimerRunning && currentTime >= 0L) "Start"
//                else "Restart"
//            )
//
//        }

}

private val scope = MainScope() // could also use an other scope such as viewModelScope if available
var job: Job? = null
var startTimer = false


private fun startUpdates(onCurrentTimeChange: (Long) -> Unit) {
    stopUpdates()
    job = scope.launch {
        while (startTimer) {
            onCurrentTimeChange(CommonFunction.getCurrentDateTimeInTimestamp())
            delay(100L)
        }
    }
}

private fun stopUpdates() {
    job?.cancel()
    job = null
}