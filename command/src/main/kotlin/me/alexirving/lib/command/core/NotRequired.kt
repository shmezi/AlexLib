package me.alexirving.lib.command.core

/**
 * Allows for a platform to not require a permission.
 */
class NotRequired<U> : Permission<U> {
    override fun hasPermission(user: U) = true
}