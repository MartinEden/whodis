package me.edens.whodis

import java.util.concurrent.TimeUnit

fun Iterable<String>.runCommand(): String {
    val proc = startProcess()
    proc.waitFor(60, TimeUnit.MINUTES)
    return proc.inputStream.bufferedReader().readText()
}

fun Iterable<String>.runCommandInBackground() {
    startProcess()
}

private fun Iterable<String>.startProcess(): Process {
    return ProcessBuilder(*this.toList().toTypedArray())
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
}
