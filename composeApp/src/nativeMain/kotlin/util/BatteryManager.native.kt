package util

import platform.UIKit.UIDevice

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class BatteryManager {
    actual fun getBatteryLevel(): Int {
        UIDevice.currentDevice.batteryMonitoringEnabled = true
        val batteryLevel = UIDevice.currentDevice.batteryLevel

        return (batteryLevel * 100).roundToInt()
    }
}