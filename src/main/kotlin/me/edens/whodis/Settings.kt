package me.edens.whodis

import com.google.gson.GsonBuilder
import java.io.File

data class Settings(
    val timeoutInSeconds: Long = 10 * 60,
    val checkFrequencyInSeconds: Long = 20
) {
    companion object {
        private val configFile = File("/etc/whodis/settings")
        private val serializer = GsonBuilder().create()

        fun load(): Settings {
            val settings = if (configFile.exists()) {
                val text = configFile.readText()
                println("Loaded settings from ${configFile.absolutePath}")
                serializer.fromJson(text, Settings::class.java)
            } else {
                println("No settings file found at ${configFile.absolutePath}. Using defaults")
                Settings()
            }
            return settings
        }
    }
}
