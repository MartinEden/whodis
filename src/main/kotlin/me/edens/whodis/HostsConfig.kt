package me.edens.whodis

import com.google.gson.GsonBuilder
import java.io.File

class HostsConfig(data: List<HostDescription.Known>) {
    private val lookup = data.associateBy { it.address }

    fun get(address: String) = lookup[address] ?: HostDescription.Unknown(address)

    companion object {
        private val configFile = File("/etc/whodis/hosts")
        private val serializer = GsonBuilder().create()
        private val empty = HostsConfig(emptyList())

        fun load(): HostsConfig {
            return if (configFile.exists()) {
                val text = configFile.readText()
                val data = serializer.fromJson(text, Array<HostDescription.Known>::class.java).toList()
                HostsConfig(data)
            } else {
                print(missingFileMessage)
                HostsConfig.empty
            }
        }

        private val missingFileMessage = """
            |Warning, no hosts file exists at ${configFile.absolutePath}
            |To configure, create a JSON file with entries in this format:
            |[
            |    {
            |        "address": "00:00:00:00:00:00",
            |        "name": "Human readable name",
            |        "announce": true
            |    }
            |]
            |Set "announce" to false if you do not want to be notified about a given host"
        """.trimMargin()
    }
}
