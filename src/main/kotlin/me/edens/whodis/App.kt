package me.edens.whodis

import java.lang.Thread.sleep
import java.time.Duration

fun main() {
    val settings = Settings.load()
    val hostsConfig = HostsConfig.load()
    val finder = MacAddressFinder(checkFrequency = Duration.ofSeconds(settings.checkFrequencyInSeconds))
    val announcer = Announcer()
    var state = State(settings, hostsConfig)
    while (true) {
        val latestAddresses = finder.getMacAddresses()
        state = state.update(latestAddresses, announcer)
        sleep(1000)
    }
}