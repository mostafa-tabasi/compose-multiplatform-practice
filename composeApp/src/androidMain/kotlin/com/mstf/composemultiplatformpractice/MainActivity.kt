package com.mstf.composemultiplatformpractice

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import data.remote.InsultCensorService
import data.remote.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp
import util.BatteryManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                batteryManager = remember { BatteryManager(applicationContext) },
                censorService = remember { InsultCensorService(createHttpClient(OkHttp.create())) },
            )
        }
    }
}