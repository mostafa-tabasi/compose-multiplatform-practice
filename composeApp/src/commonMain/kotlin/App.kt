import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import data.remote.InsultCensorService
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import screen.BatteryScreen
import screen.CounterScreen
import screen.HomeScreen
import screen.NetworkCallSampleScreen
import screen.diWithViewModelSampleScreen.DISampleScreen
import screen.username.UsernameScreen
import util.BatteryManager

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
                composable("home") { HomeScreen(navController) }
                composable("battery") { BatteryScreen(batteryManager) }
                composable("di") { DISampleScreen() }
                composable("network_call") { NetworkCallSampleScreen(censorService) }
                composable("counter") { CounterScreen() }
                composable("username") { UsernameScreen() }
            }
        }
    }
}
