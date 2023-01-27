package me.alexirving.lib.permission

abstract class PermissionPlatform<U> {
    /**
     * [First ID of node | Node]
     */
    private val mapping = mutableMapOf<String, PermissionNode<U>>()
    abstract fun getPermissions(): PermissionNode<U>
    abstract fun hasPermission():
    fun hasPermission(user: U) {
    }

}