package me.alexirving.lib.command.core.content

/**
 * The result of a command being executed
 * @param default The default message for this result
 * @param success Weather the command was executed successfully
 */
enum class CommandResult(val default: String, val success: Boolean) {
    SUCCESS("", true),
    FAILURE("The command was not used correctly!",false),
    NOT_ENOUGH_ARGS("Not enough arguments were provided!", false),
    NO_ACTION_SET("The develop has not set an action for this command! SHAME!", false),
    COMMAND_NOT_FOUND("The command issued does not exist!", false),
    NO_PERMISSION("You do not have permission to use this command!", false),
    WRONG_ARG_TYPE("An argument was provided with the wrong type!",false)

}