package me.alexirving.lib.commands.command

enum class CommandResult(val default: String?, val success: Boolean) {
    SUCCESS(null, true),
    NOT_ENOUGH_ARGS("Not enough arguments were provided!", false),
    NO_ACTION_SET("The develop has not set an action for this command! SHAME!", false),
    COMMAND_NOT_FOUND("The command issued does not exist!", false),
    NO_PERMISSION("You do not have permission to use this command!", false)
    ;
}