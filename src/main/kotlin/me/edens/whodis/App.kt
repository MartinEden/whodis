package me.edens.whodis

import java.lang.Thread.sleep

fun main() {
    val hostsConfig = HostsConfig.load()
    val finder = MacAddressFinder()
    val announcer = Announcer()
    var state = State()
    while (true) {
        val latestAddresses = finder.getMacAddresses()
        state = state.update(latestAddresses, hostsConfig, announcer)
        sleep(1000)
    }
}