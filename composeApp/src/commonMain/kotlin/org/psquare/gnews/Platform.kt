package org.psquare.gnews

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform