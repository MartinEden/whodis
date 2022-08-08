package me.edens.whodis

import java.time.Duration
import java.time.Instant

data class HostState(val lastSeen: Instant, val description: HostDescription?)

class State(private val data: Map<String, HostState> = emptyMap()) {

    fun update(latestAddresses: Set<String>, hostsConfig: HostsConfig, announcer: Announcer): State {
        var data = data
        val now = Instant.now()
        for (address in latestAddresses) {
            val host = hostsConfig.get(address)
            if (address !in data) {
                announcer.announce(host)
            }
            data = data + (address to HostState(now, host))
        }
        for ((address, hostState) in data) {
            if (Duration.between(hostState.lastSeen, now) > Duration.ofMinutes(30)) {
                data = data - address
                println("Haven't seen ${hostState.description} in a while")
            }
        }
        return State(data)
    }
}