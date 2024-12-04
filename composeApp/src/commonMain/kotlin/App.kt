import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import data.remote.InsultCensorService
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import screen.BatteryScreen
import screen.ClipAndMaskScreen
import screen.CounterScreen
import screen.HomeScreen
import screen.NestedScrollScreen
import screen.NetworkCallSampleScreen
import screen.PagerScreen
import screen.TransitionSharedElementScreen
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            navController,
                            platform = getPlatform(),
                        )
                    }
                    composable("battery") { BatteryScreen(batteryManager) }
                    composable("network_call") { NetworkCallSampleScreen(censorService) }
                    composable("counter") { CounterScreen() }
                    composable("username") { UsernameScreen() }
                    composable("pager") { PagerScreen(platform = getPlatform()) }
                    composable("transition_shared_element") { TransitionSharedElementScreen() }
                    composable("nested_scroll") { NestedScrollScreen() }
                    composable("clip_mask") { ClipAndMaskScreen() }
                }
            }
        }
    }
}
