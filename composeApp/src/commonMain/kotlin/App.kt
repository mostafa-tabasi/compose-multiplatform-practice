import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import composemultiplatformpractice.composeapp.generated.resources.Res
import composemultiplatformpractice.composeapp.generated.resources.compose_multiplatform
import composemultiplatformpractice.composeapp.generated.resources.current_battery_level
import composemultiplatformpractice.composeapp.generated.resources.platform
import dependencies.MyViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
@Preview
fun App(
    batteryManager: BatteryManager,
) {
    MaterialTheme {
        KoinContext {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") { HomeComposable(navController) }
                composable("battery") { BatteryComposable(batteryManager) }
                composable("di") { ScreenWithDIComposable() }
            }
        }
    }
}

@Composable
private fun HomeComposable(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painterResource(Res.drawable.compose_multiplatform), null)
        Text(stringResource(Res.string.platform, getPlatform().name))
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { navController.navigate("battery") },
        ) { Text("Battery Level") }
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = { navController.navigate("di") },
        ) { Text("Dependency Injection") }
    }
}

@Composable
private fun BatteryComposable(batteryManager: BatteryManager) {
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

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ScreenWithDIComposable() {
    val viewModel = koinViewModel<MyViewModel>()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = viewModel.getTitle())
    }
}
