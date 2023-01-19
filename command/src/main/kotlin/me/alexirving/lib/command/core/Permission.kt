package me.alexirving.lib.command.core

/**
 * Represents an implementation of a permission.
 * Allow / Deny a command to a user in any platform
 */
interface Permission<U> {
    /**
     * Check if a user has the permission
     * @param user The user to check
     * @return If the user has permission to use the command.
     */
    fun hasPermission(user: U): Boolean
}