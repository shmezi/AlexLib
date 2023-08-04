package me.alexirving.lib.command.core.content

/**
 * The result of a user running a command.
 * @param default The default message returned from this result.
 * @param success Whether the result is one that the command was executed successfully.
 */
enum class CommandResult(val default: String, val success: Boolean) {
    /**
     * The command was run with no issues
     */
    SUCCESS("", true),

    /**
     * The command failed, the cause was caused by an external factor outside of the library. (By returning false)
     */
    FAILURE("The command was not used correctly!", false),

    /**
     * The user did not provide enough arguments
     */
    NOT_ENOUGH_ARGS("Not enough arguments were provided!", false),

    /**
     * The command failed because You, Yes You failed as a developer and haven't set the action of the command using the [me.alexirving.lib.command.core.content.builder.CommandBuilder.action]
     * :( Fix this please.
     */
    NO_ACTION_SET("The developer has not set an action for this command! SHAME!", false),

    /**
     * The command the user ran was either not registered correctly or does not exist.
     */
    COMMAND_NOT_FOUND("The command issued does not exist!", false),

    /**
     * The user running the command does not have the correct permissions to run the command.
     */
    NO_PERMISSION("You do not have permission to use this command!", false),

    /**
     * The user used the wrong type of argument
     */
    WRONG_ARG_TYPE("An argument was provided with the wrong type!", false)

}