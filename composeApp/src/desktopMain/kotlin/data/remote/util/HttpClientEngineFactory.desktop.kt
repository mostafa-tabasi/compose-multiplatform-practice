package data.remote.util

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class HttpClientEngineFactory actual constructor() {
    actual fun create(): HttpClientEngine = OkHttp.create()
}