package net.chauhandevs.utils.cah.registries;

import net.chauhandevs.utils.cah.command.CommandDefinition;

import java.util.ArrayList;
import java.util.List;

public final class CommandRegistry {
    private static List<CommandDefinition> registeredCommands = new ArrayList<>();

    public static void registerCommand(CommandDefinition command){
        registeredCommands.add(command);
    }

    public static CommandDefinition[] getRegisteredCommandsDefinitions(){
        CommandDefinition[] commands = new CommandDefinition[registeredCommands.size()];
        int i = 0;
        for(CommandDefinition c : registeredCommands){
            commands[i] = c;
            i++;
        }
        return commands;
    }

    public static CommandDefinition getByString(String st){
        for (CommandDefinition def : registeredCommands){
            if(def.commandName.equals(st)){
                return def;
            }
        }
        return null;
    }
}
