@file:OptIn(ExperimentalTestApi::class)

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

class CounterComposableKtTest {

    @Test
    fun testCounterComposable() = runComposeUiTest {
        setContent {
            CounterComposable()
        }

        onNodeWithTag("counter_text").assertExists()
        onNodeWithTag("counter_button").assertExists()

        onNodeWithTag("counter_text").assertTextEquals("0")

        onNodeWithTag("counter_button").performClick()
        onNodeWithTag("counter_text").assertTextEquals("1")
    }
}