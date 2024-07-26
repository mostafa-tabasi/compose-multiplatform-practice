package screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import composemultiplatformpractice.composeapp.generated.resources.Res
import composemultiplatformpractice.composeapp.generated.resources.current_battery_level
import org.jetbrains.compose.resources.stringResource
import util.BatteryManager


@Composable
fun BatteryScreen(batteryManager: BatteryManager) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            stringResource(
                Res.string.current_battery_level,
                batteryManager.getBatteryLevel()
            )
        )
    }
}