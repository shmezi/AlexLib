package me.alexirving.lib.command.core

/**
 * The permission a [U] needs to execute the command
 */
interface Permission<U> {
    /**
     * Check if a user has the permission needed to execute the command.
     * @param user The user to check against.
     * @return If the user has permission to use the command.
     */
    fun hasPermission(user: U): Boolean
}