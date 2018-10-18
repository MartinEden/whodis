package me.edens.whodis

import java.util.concurrent.TimeUnit

fun String.runCommand(): String {
    val proc = startProcess()
    proc.waitFor(60, TimeUnit.MINUTES)
    return proc.inputStream.bufferedReader().readText()
}

fun String.runCommandInBackground() {
    startProcess()
}

private fun String.startProcess(): Process {
    val parts = this.split("\\s".toRegex())
    return ProcessBuilder(*parts.toTypedArray())
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
}
