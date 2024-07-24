import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import composemultiplatformpractice.composeapp.generated.resources.Res
import composemultiplatformpractice.composeapp.generated.resources.compose_multiplatform
import composemultiplatformpractice.composeapp.generated.resources.current_battery_level
import composemultiplatformpractice.composeapp.generated.resources.platform
import data.remote.InsultCensorService
import data.remote.util.NetworkError
import data.remote.util.onError
import data.remote.util.onSuccess
import dependencies.MyViewModel
import kotlinx.coroutines.launch
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
    censorService: InsultCensorService,
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
                composable("network_call") { NetworkCallComposable(censorService) }
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(painterResource(Res.drawable.compose_multiplatform), null)
        Text(stringResource(Res.string.platform, getPlatform().name))

        Button(
            onClick = { navController.navigate("battery") },
        ) { Text("Battery Level") }

        Button(
            onClick = { navController.navigate("di") },
        ) { Text("Dependency Injection") }

        Button(
            onClick = { navController.navigate("network_call") },
        ) { Text("Network Call") }
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

@Composable
fun NetworkCallComposable(censorService: InsultCensorService) {
    var censoredWord by remember { mutableStateOf<String?>(null) }
    var uncensoredWord by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<NetworkError?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = uncensoredWord,
            onValueChange = { uncensoredWord = it }
        )
        Button(onClick = {
            if (!loading) {
                scope.launch {
                    loading = true
                    error = null

                    censorService.censorWord(uncensoredWord)
                        .onSuccess { censoredWord = it }
                        .onError { error = it }
                    loading = false
                }
            }
        }) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(12.dp),
                    strokeWidth = 1.dp,
                    color = Color.White,
                )
            } else {
                Text("Censor!")
            }
        }
        censoredWord?.let { Text(it) }
        error?.let { Text(it.name, color = Color.Red) }
    }
}
