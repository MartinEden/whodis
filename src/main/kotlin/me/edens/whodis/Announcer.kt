package me.edens.whodis

class Announcer {
    fun announce(host: HostDescription) {
        when (host) {
            is HostDescription.Known -> if (host.announce) {
                say("${host.name} has arrived")
            } else {
                println("Configured not to announce ${host.name}")
            }
            is HostDescription.Unknown -> {
                println("Need to add this host to the lookup: ${host.address}")
                say("Unknown host")
            }
        }
    }

    private fun say(speech: String) {
        val cmd = listOf("espeak", "-s", "130", speech)
        println(cmd.joinToString(" "))
        cmd.runCommand()
    }
}