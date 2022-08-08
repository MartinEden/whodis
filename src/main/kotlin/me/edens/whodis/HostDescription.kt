package me.edens.whodis

sealed class HostDescription {
    class Known(val address: String, val name: String, val announce: Boolean) : HostDescription() {
        override fun toString() = name
    }

    class Unknown(val address: String) : HostDescription() {
        override fun toString() = "Unknown ($address)"
    }
}