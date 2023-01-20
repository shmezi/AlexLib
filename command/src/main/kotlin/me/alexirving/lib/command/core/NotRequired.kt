package me.alexirving.lib.command.core

class NotRequired<U> : Permission<U> {
    override fun hasPermission(user: U) = true
}