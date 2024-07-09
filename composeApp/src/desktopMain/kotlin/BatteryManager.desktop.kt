import oshi.SystemInfo
import kotlin.math.roundToInt

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class BatteryManager {
    actual fun getBatteryLevel(): Int {
        val systemInfo = SystemInfo()
        val batteryLevel = systemInfo.hardware.powerSources.firstOrNull()

        return batteryLevel?.remainingCapacityPercent?.times(100)?.roundToInt() ?: -1
    }
}