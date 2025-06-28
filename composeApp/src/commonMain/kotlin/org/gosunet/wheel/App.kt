package org.gosunet.wheel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.toPersistentList
import org.gosunet.wheel.ui.Typography
import org.gosunet.wheel.utils.toColor
import org.jetbrains.compose.ui.tooling.preview.Preview
import wheelcmp.composeapp.generated.resources.Res
import wheelcmp.composeapp.generated.resources.spin_wheel_background
import wheelcmp.composeapp.generated.resources.spin_wheel_center
import wheelcmp.composeapp.generated.resources.spin_wheel_tick

@Composable
@Preview
fun App() {
    MaterialTheme(typography = Typography) {
        val colors1 = remember {
            listOf(
                "380048",
                "2B003D",
                "40004A",
                "590058",
                "730067"
            ).map { it.toColor() }
        }

        val colors2 = remember {
            listOf(
                "F9A114",
                "FD7D1B",
                "F9901A",
                "F6A019",
                "EFC017"
            ).map { it.toColor() }
        }

        val items = remember {
            List(8) { index ->
                val colors = if (index % 2 == 0) colors1 else colors2

                SpinWheelItem(
                    colors = colors.toPersistentList()
                ) {
                    Text(
                        text = "$$index",
                        style = TextStyle(color = Color(0xFF4CAF50), fontSize = 20.sp)
                    )
                }

            }.toPersistentList()
        }
        var pickerValue by remember { mutableIntStateOf(0) }

        val spinState = rememberSpinWheelState(
            items = items,
            backgroundImage = Res.drawable.spin_wheel_background,
            centerImage = Res.drawable.spin_wheel_center,
            indicatorImage = Res.drawable.spin_wheel_tick,
            onSpinningFinished = null,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp, end = 40.dp, bottom = 40.dp, top = 100.dp)
        ) {
            Box(modifier = Modifier.size(300.dp)) {
                SpinWheelComponent(spinState)
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = {
                spinState.goto(pickerValue)
            }) {
                Text(text = "Goto")
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = {
                spinState.launchInfinite()
            }
            ) {
                Text(text = "Infinite")
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = {
                spinState.stoppingWheel(pickerValue)
            }) {
                Text(text = "Stop")
            }
            Spacer(modifier = Modifier.size(20.dp))

            TextField(
                value = pickerValue.toString(),
                onValueChange = {
                    pickerValue = it.toIntOrNull() ?: 0
                }
            )
        }
    }
}