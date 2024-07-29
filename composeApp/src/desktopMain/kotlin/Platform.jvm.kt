class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override val isAndroid: Boolean = false
    override val isIOS: Boolean = false
    override val isDesktop: Boolean = true
}

actual fun getPlatform(): Platform = JVMPlatform()