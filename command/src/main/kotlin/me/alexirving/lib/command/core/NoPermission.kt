package me.alexirving.lib.command.core

/**
 * A very basic implementation of [Permission] that makes the Permission ignored.
 */
class NoPermission<U> : Permission<U> {
    override fun hasPermission(user: U) = true
}