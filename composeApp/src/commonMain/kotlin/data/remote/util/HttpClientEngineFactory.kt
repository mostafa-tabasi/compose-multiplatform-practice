package data.remote.util

import io.ktor.client.engine.HttpClientEngine

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class HttpClientEngineFactory() {
    fun create(): HttpClientEngine
}