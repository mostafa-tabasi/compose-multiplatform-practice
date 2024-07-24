import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import data.remote.InsultCensorService
import data.remote.createHttpClient
import di.initKoin
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(
        batteryManager = remember { BatteryManager() },
        censorService = remember { InsultCensorService(createHttpClient(Darwin.create())) },
    )
}