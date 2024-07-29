import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val isAndroid: Boolean = true
    override val isIOS: Boolean = false
    override val isDesktop: Boolean = false
}

actual fun getPlatform(): Platform = AndroidPlatform()