package com.mstf.composemultiplatformpractice

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import data.remote.InsultCensorService
import data.remote.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp
import util.BatteryManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                // Check all the stuff we need to check in splash screen
                // and at the end return false to dismiss the splash
                false
            }
        }

        setContent {
            App(
                batteryManager = remember { BatteryManager(applicationContext) },
                censorService = remember { InsultCensorService(createHttpClient(OkHttp.create())) },
            )
        }
    }
}