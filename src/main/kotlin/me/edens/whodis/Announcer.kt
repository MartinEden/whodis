package me.edens.whodis

class Announcer {
    fun announce(host: HostDescription) {
        when (host) {
            is HostDescription.Known -> if (host.announce) {
                say("${host.name} has arrived.")
            }
            is HostDescription.Unknown -> {
                println("Need to add this host to the lookup: ${host.address}")
                say("Unknown host")
            }
        }
    }

    private fun say(speech: String) {
        val cmd = """say "$speech""""
        println(cmd)
        cmd.runCommand()
    }
}