package me.edens.whodis

import com.google.gson.GsonBuilder
import java.io.File
import kotlin.system.exitProcess


sealed class HostDescription {
    class Known(val address: String, val name: String, val announce: Boolean) : HostDescription() {
        override fun toString() = name
    }

    class Unknown(val address: String) : HostDescription() {
        override fun toString() = "Unknown ($address)"
    }
}

class Config(data: List<HostDescription.Known>) {
    private val lookup = data.associateBy { it.address }

    fun get(address: String) = lookup[address] ?: HostDescription.Unknown(address)

    companion object {
        private val configFile = File("/etc/whodis/hosts")
        private val serializer = GsonBuilder().create()
        private val empty = Config(emptyList())

        fun load(): Config {
            return if (configFile.exists()) {
                val text = configFile.readText()
                val data = serializer.fromJson(text, Array<HostDescription.Known>::class.java).toList()
                Config(data)
            } else {
                println("Warning, no hosts file exists at ${configFile.absolutePath}")
                println("To configure, create a JSON file with entries in this format:")
                println("""[
                    |    {
                    |        "address": "00:00:00:00:00:00",
                    |        "name": "Human readable name",
                    |        "announce": true
                    |    }
                    |]
                """.trimMargin())
                println("'announce' controls whether you are notified about this host")
                Config.empty
            }
        }
    }
}
