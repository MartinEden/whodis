package me.edens.whodis

import java.time.Duration
import java.time.Instant

class MacAddressFinder(private val checkFrequency: Duration = Duration.ofSeconds(20)) {
    private val ipAddressRegex = (1..4).map { """[\d]+""" }.joinToString(".")
    private val macAddressPartRegex = "[a-f0-9]{2}"
    private val macAddressRegex = (1..6).map { macAddressPartRegex }.joinToString(":")
    private val arpRegex = Regex("""^[^ ]+ \($ipAddressRegex\) at (?<macAddress>$macAddressRegex)""")

    private var lastRefreshed: Instant = Instant.EPOCH
    private val timeSinceLastRefresh: Duration
        get() = Duration.between(lastRefreshed, Instant.now())

    fun getMacAddresses(): Set<String> {
        if (timeSinceLastRefresh > checkFrequency) {
            triggerRefresh()
        }
        val rawARP = "arp -a".runCommand()
        return rawARP
                .split("\n")
                .mapNotNull { arpRegex.find(it) }
                .map { it.groups["macAddress"]!!.value }
                .toSet()
    }

    private fun triggerRefresh() {
        println("Refreshing ARP cache...")
        "nmap -n -sP 192.168.1.0/24".runCommandInBackground()
        lastRefreshed = Instant.now()
    }
}