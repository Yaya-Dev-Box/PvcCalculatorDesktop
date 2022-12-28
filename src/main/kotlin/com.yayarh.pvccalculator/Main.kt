package com.yayarh.pvccalculator

import PrefsMan
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.yayarh.pvccalculator.Extensions.toIntOrZero

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "PVC Calculator", icon = painterResource("default_icon.png")) { App() }
}

@Composable
@Preview
fun App() {
    val prefsMan = PrefsMan()
    var currentScreen by remember { mutableStateOf("home") }

    MaterialTheme {
        Row {
            if (currentScreen == "home") HomeScreen({ currentScreen = "settings" }, prefsMan)
            else SettingsScreen({ currentScreen = "home" }, prefsMan)
        }
    }
}


@Composable
fun HomeScreen(onNavigateToSettings: () -> Unit, prefs: PrefsMan) {
    var pvcVerticalLength by rememberSaveable { mutableStateOf("") }
    var pvcVerticalCount by rememberSaveable { mutableStateOf("0") }

    var pvcHorizontalLength by rememberSaveable { mutableStateOf("") }
    var pvcHorizontalCount by rememberSaveable { mutableStateOf("0") }

    var glassVerticalLength by rememberSaveable { mutableStateOf("") }
    var glassHorizontalLength by rememberSaveable { mutableStateOf("") }

    var shutterVerticalLength by rememberSaveable { mutableStateOf("") }
    var shutterHorizontalLength by rememberSaveable { mutableStateOf("") }


    val glassTotalPrice =
        (((glassVerticalLength.toIntOrZero() * glassHorizontalLength.toIntOrZero())
                * prefs.getGlassPrice()) / 10000).toString() + " DA"

    val shutterTotalPrice =
        (((shutterVerticalLength.toIntOrZero() * shutterHorizontalLength.toIntOrZero())
                * prefs.getShutterPrice()) / 10000).toString() + " DA"


    val pvcTotalPrice =
        (((pvcVerticalLength.toIntOrZero() * pvcVerticalCount.toIntOrZero()) +
                (pvcHorizontalLength.toIntOrZero() * pvcHorizontalCount.toIntOrZero())) *
                prefs.getPvcPrice() / 100).toString() + " DA"


    val totalPrice =
        (((glassVerticalLength.toIntOrZero() * glassHorizontalLength.toIntOrZero()) * prefs.getGlassPrice()) / 10000) +
                (((pvcVerticalLength.toIntOrZero() * pvcVerticalCount.toIntOrZero()) + (pvcHorizontalLength.toIntOrZero() * pvcHorizontalCount.toIntOrZero())) * prefs.getPvcPrice() / 100) +
                (((shutterVerticalLength.toIntOrZero() * shutterHorizontalLength.toIntOrZero()) * prefs.getShutterPrice()) / 10000)

    val scrollState = rememberScrollState()

    Column(Modifier.fillMaxWidth().verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally) {

        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onNavigateToSettings
        ) { Icon(painter = painterResource("settings_black_24dp.svg"), null) }

        Spacer(Modifier.height(16.dp))

        //region PVC region

        Text(text = "PVC" + " " + prefs.getPvcPrice() + " DA/m")

        Spacer(Modifier.height(16.dp))

        Row {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                DimensionTextField(
                    value = pvcVerticalLength,
                    placeholder = "Vertical bars length (cm)",
                    onValueChange = { pvcVerticalLength = it }
                )

                Spacer(Modifier.height(8.dp))

                Row {
                    IconButton({
                        pvcVerticalCount = (pvcVerticalCount.toIntOrZero() - 1).toString()
                    }, content = { Icon(painter = painterResource("remove_black_24dp.svg"), null) })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = pvcVerticalCount, modifier = Modifier.align(Alignment.CenterVertically), fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton({
                        pvcVerticalCount = (pvcVerticalCount.toIntOrZero() + 1).toString()
                    }, content = { Icon(painter = painterResource("add_black_24dp.svg"), null) })
                }
            }

            Spacer(Modifier.width(32.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DimensionTextField(
                    value = pvcHorizontalLength,
                    placeholder = "Horizontal bars length (cm)",
                    onValueChange = { pvcHorizontalLength = it }
                )

                Spacer(Modifier.height(8.dp))

                Row {
                    IconButton({
                        pvcHorizontalCount = (pvcHorizontalCount.toIntOrZero() - 1).toString()
                    }, content = { Icon(painter = painterResource("remove_black_24dp.svg"), null) })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = pvcHorizontalCount, modifier = Modifier.align(Alignment.CenterVertically), fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton({
                        pvcHorizontalCount = (pvcHorizontalCount.toIntOrZero() + 1).toString()
                    }, content = { Icon(painter = painterResource("add_black_24dp.svg"), null) })
                }
            }
        }

        //endregion

        Spacer(Modifier.height(48.dp))

        Row {
            Column {
                //region Glass region

                Text(
                    text = "Glass" + " " + prefs.getGlassPrice() + " DA/m",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(16.dp))

                DimensionTextField(
                    value = glassVerticalLength,
                    placeholder = "Vertical bars length (cm)",
                    onValueChange = { glassVerticalLength = it }
                )

                DimensionTextField(
                    value = glassHorizontalLength,
                    placeholder = "Horizontal bars length (cm)",
                    onValueChange = { glassHorizontalLength = it }
                )

                //endregion
            }
            Spacer(Modifier.width(48.dp))

            Column {
                //region Shutter region

                Text(
                    text = "Shutter" + " " + prefs.getShutterPrice() + " DA/m",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(16.dp))

                DimensionTextField(
                    value = shutterVerticalLength,
                    placeholder = "Vertical bars length (cm)",
                    onValueChange = { shutterVerticalLength = it }
                )

                DimensionTextField(
                    value = shutterHorizontalLength,
                    placeholder = "Horizontal bars length (cm)",
                    onValueChange = { shutterHorizontalLength = it }
                )

                //endregion
            }
        }
        Spacer(Modifier.height(32.dp))


        Spacer(Modifier.height(64.dp))

        Text(text = "PVC: $pvcTotalPrice")
        Spacer(Modifier.height(16.dp))
        Text(text = "Glass: $glassTotalPrice")
        Spacer(Modifier.height(16.dp))
        Text(text = "Shutter: $shutterTotalPrice")

        Spacer(Modifier.height(16.dp))

        Text(text = "Total: $totalPrice DA")
        Spacer(Modifier.height(32.dp))

    }

}

@Composable
fun SettingsScreen(onNavigatingBack: () -> Unit, prefs: PrefsMan) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(modifier = Modifier.align(Alignment.Start), onClick = onNavigatingBack) {
            Icon(
                painterResource("arrow_back_black_24dp.svg"),
                null
            )
        }

        var pvcPrice: String by rememberSaveable { mutableStateOf(prefs.getPvcPrice().toString()) }
        var glassPrice: String by rememberSaveable {
            mutableStateOf(prefs.getGlassPrice().toString())
        }
        var shutterPrice: String by rememberSaveable {
            mutableStateOf(prefs.getShutterPrice().toString())
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "PVC", Modifier.weight(2F))
            Spacer(modifier = Modifier.weight(1F))

            PriceTextField(
                modifier = Modifier.weight(7F),
                value = pvcPrice,
                placeholder = "Price",
                onValueChange = { pvcPrice = it })
            Spacer(modifier = Modifier.weight(1F))
            Text("DA/m", Modifier.weight(2F))
        }


        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "Glass", Modifier.weight(2F))
            Spacer(modifier = Modifier.weight(1F))
            PriceTextField(
                modifier = Modifier.weight(7F),
                value = glassPrice,
                placeholder = "Price",
                onValueChange = { glassPrice = it })
            Spacer(modifier = Modifier.weight(1F))
            Text("DA/m", Modifier.weight(2F))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "Shutter", Modifier.weight(2F))
            Spacer(modifier = Modifier.weight(1F))
            PriceTextField(
                modifier = Modifier.weight(7F),
                value = shutterPrice,
                placeholder = "Price",
                onValueChange = { shutterPrice = it })
            Spacer(modifier = Modifier.weight(1F))
            Text("DA/m", Modifier.weight(2F))
        }

        Spacer(Modifier.height(24.dp))

        Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            border = BorderStroke(1.dp, Brush.linearGradient(listOf(Color.Black, Color.Gray))),
            onClick = {
                prefs.setPvcPrice(pvcPrice.toIntOrZero())
                prefs.setGlassPrice(glassPrice.toIntOrZero())
                prefs.setShutterPrice(shutterPrice.toIntOrZero())
            }) { Text("Save") }

    }
}


@Composable
fun DimensionTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { if (it.isBlank() || it.toIntOrNull() != null) onValueChange(it) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}

@Composable
fun PriceTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier.width(128.dp),
        value = value,
        onValueChange = { if (it.isBlank() || it.toIntOrNull() != null) onValueChange(it) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}