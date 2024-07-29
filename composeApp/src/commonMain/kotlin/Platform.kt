interface Platform {
    val name: String
    val isAndroid: Boolean
    val isIOS: Boolean
    val isDesktop: Boolean
}

expect fun getPlatform(): Platform