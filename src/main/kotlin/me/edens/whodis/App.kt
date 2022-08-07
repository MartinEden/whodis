package me.edens.whodis

import java.lang.Thread.sleep
import java.time.Duration
import java.time.Instant

fun main() {
    val config = Config.load()
    val finder = MacAddressFinder()
    val announcer = Announcer()
    var state = State()
    while (true) {
        val latestAddresses = finder.getMacAddresses()
        state = state.update(latestAddresses, config, announcer)
        sleep(1000)
    }
}