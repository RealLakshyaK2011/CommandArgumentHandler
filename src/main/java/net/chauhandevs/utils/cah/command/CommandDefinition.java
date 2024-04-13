package net.chauhandevs.utils.cah.command;

import net.chauhandevs.utils.cah.argument.ArgumentDefinition;
import net.chauhandevs.utils.cah.callback.CommandCallbackHook;

import java.util.List;

public class CommandDefinition {

    public final String commandName;
    public final List<ArgumentDefinition> allowedArguments;

    private CommandCallbackHook callableCallback;

    public CommandDefinition(String commandName, CommandType type, List<ArgumentDefinition> allowedArguments, CommandCallbackHook hook){
        if(type == CommandType.NO_ARGS && allowedArguments != null){
            throw new RuntimeException("Command type given NO_ARGS but allowed Arguments are given non null!");
        } else if (type == CommandType.WITH_ARGS && allowedArguments == null) {
            throw new RuntimeException("Command type given WITH_ARGS but allowed Arguments are given null!");
        }
        this.commandName = commandName;
        this.allowedArguments = allowedArguments;
        this.callableCallback = hook;
    }

    protected void performAction(Command command){
        callableCallback.callbackFunction(command.givenArgs);
    }
}
