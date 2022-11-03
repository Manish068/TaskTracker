package com.devopworld.tasktracker.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.airbnb.lottie.compose.*
import com.devopworld.tasktracker.Navigation.DestinationDeepLink
import com.devopworld.tasktracker.Navigation.Destinations
import com.devopworld.tasktracker.ui.theme.LightPrimaryColor
import com.devopworld.tasktracker.ui.theme.PrimaryColor
import com.devopworld.tasktracker.ui.theme.fontTypography
import com.devopworld.tasktracker.util.Action
import com.devopworld.tasktracker.util.MainEvent
import com.devopworld.tasktracker.util.PreferenceConstant.USER_NAME
import com.devopworld.tasktracker.viewmodel.DataStorePreference
import com.devopworld.tasktracker.viewmodel.PreferenceViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SplashScreen"

@ExperimentalAnimationApi
fun NavGraphBuilder.StartScreen(
    viewModel: PreferenceViewModel,
    nameSaved: Boolean,
    userName: String,
    onSplashScreenComplete:(String, Action)-> Unit,
) {
    composable(Destinations.SplashScreen,
    deepLinks = listOf(
        navDeepLink {
            uriPattern=DestinationDeepLink.SplashPattern
        }
    )
    ) {
        SplashScreen(viewModel = viewModel, nameSaved,userName,onSplashScreenComplete)
    }
}

@Composable
fun SplashScreen(
    viewModel: PreferenceViewModel,
    nameSaved: Boolean,
    userName: String,
    onNextButtonClick:(String,Action)-> Unit,
) {
   val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    Box(modifier = Modifier.fillMaxSize()) {

        if (nameSaved) {
            Column(
                Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ShowAnimation(nameSaved, onNextButtonClick, userName)
            }
        } else {
            var userName by rememberSaveable { mutableStateOf("") }

            Column(
                Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ShowAnimation(nameSaved, onNextButtonClick, userName)

            }

            Column(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InputLayout(
                    userName,
                    onTextChange = {
                        userName = it
                    }
                )
            }
            Column(
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 50.dp, end = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                FabButton(
                    icon = Icons.Filled.ArrowForward
                ) {
                    if (userName.isNotEmpty() && userName.isNotBlank()) {
                        coroutineScope.launch {
                            Log.d(TAG, "SplashScreen: $userName")
                            viewModel.storeValueForKey(USER_NAME, userName)
                                .collect {
                                    Log.d(TAG, "SplashScreen: $userName")
                                    updateViewOnEvent(it, onNextButtonClick, userName)
                                }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please Tell us your name to move further",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }


    }
}


@Composable
fun ShowAnimation(nameSaved: Boolean, onNextButtonClick:(String, Action)-> Unit, userName: String) {
    // to control the lottie animation
    val compositionResult: LottieCompositionResult = rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(com.devopworld.tasktracker.R.raw.tasks)
    )

    val iteration = if (nameSaved) {
        1
    } else {
        LottieConstants.IterateForever
    }


    val lottieAnimation by animateLottieCompositionAsState(
        // pass the composition created above
        compositionResult.value,
        // Iterates Forever
        iterations = iteration,
        // Lottie and pause/play
//            isPlaying = isLottiePlaying,
        // Increasing the speed of change Lottie
        speed = 3f,
        restartOnPlay = false
    )
    // Pass the composition and the progress state
    LottieAnimation(
        compositionResult.value,
        lottieAnimation,
        modifier = Modifier
            .padding(bottom = 150.dp)
            .size(150.dp)
    )

    if (compositionResult.isComplete) {
        if (nameSaved) {
            LaunchedEffect(key1 = Unit) {
                delay(3000L)
                onNextButtonClick(userName,Action.NO_ACTION)
            }
        }
    }

}


private fun updateViewOnEvent(
    event: MainEvent,
    onNextButtonClick:(String,Action)-> Unit,
    name: String?
) {
    when (event) {
        is MainEvent.NamedCachedSuccess -> {
            // desired logic goes here
            onNextButtonClick(name!!,Action.NO_ACTION)
        }
        is MainEvent.CachedNameFetchSuccess -> {
            // desired logic goes here
        }
    }
}


@Composable
fun FabButton(
    icon: ImageVector,
    nextButtonClick: () -> Unit
) {
    FloatingActionButton(
        // on below line we are
        // adding on click for our fab
        onClick = {
            nextButtonClick()
        },
        // on below line we are
        // specifying shape for our button
        shape = RoundedCornerShape(10.dp),
        // on below line we are
        // adding background color.
        backgroundColor = PrimaryColor,
        // on below line we are
        // adding content color.
        contentColor = Color.White
    ) {
        // on below line we are
        // adding icon for fab.
        Icon(icon, "")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputLayout(text: String, onTextChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Tell us your name",
            style = TextStyle(
                fontFamily = fontTypography,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Box(
            modifier = Modifier
                .background(
                    color = LightPrimaryColor,
                    shape = CircleShape
                )
                .wrapContentWidth()
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(0.6f),
                value = text,
                onValueChange = {
                    onTextChange(if (it.length <= 20) it else text)
                },
                singleLine = true,
                shape = CircleShape,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle =
                TextStyle(
                    fontFamily = fontTypography,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )

            )
        }

    }
}


@Preview
@Composable
fun previewInput() {
    InputLayout("text", onTextChange = {

    })
}

@Preview
@Composable
fun PreviewFabButton() {

}


