package com.devopworld.tasktracker.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.devopworld.tasktracker.R

@Composable
fun TaskCreationAppBar(){
    TopAppBar(
        title = {
            Text(modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.new_task),
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(id = R.string.close) )
            }
        }
    )
}

@Preview(
    showBackground=true,
    device = Devices.NEXUS_6
)
@Composable
fun previewTaskAppBar(){
TaskCreationAppBar()
}