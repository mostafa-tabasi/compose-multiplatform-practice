import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import composemultiplatformpractice.composeapp.generated.resources.Res
import composemultiplatformpractice.composeapp.generated.resources.compose_multiplatform
import composemultiplatformpractice.composeapp.generated.resources.current_battery_level
import composemultiplatformpractice.composeapp.generated.resources.platform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    batteryManager: BatteryManager,
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(painterResource(Res.drawable.compose_multiplatform), null)
            Text(stringResource(Res.string.platform, getPlatform().name))
            Text(stringResource(Res.string.current_battery_level, batteryManager.getBatteryLevel()))
        }
    }
}