import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.remote.InsultCensorService
import data.remote.createHttpClient
import di.initKoin
import io.ktor.client.engine.okhttp.OkHttp

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ComposeMultiplatformPractice",
        ) {
            App(
                batteryManager = remember { BatteryManager() },
                censorService = remember { InsultCensorService(createHttpClient(OkHttp.create())) },
            )
        }
    }
}