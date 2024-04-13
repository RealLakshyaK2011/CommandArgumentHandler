package net.chauhandevs.utils.cah.registries;

import net.chauhandevs.utils.cah.argument.ArgumentDefinition;
import net.chauhandevs.utils.cah.command.CommandObjects;

import java.util.ArrayList;
import java.util.List;

public final class ArgumentRegistry {
    private static List<ArgumentDefinition> registeredArgs = new ArrayList<>();

    public static void registerArg(ArgumentDefinition arg){
        registeredArgs.add(arg);
    }

    public static ArgumentDefinition[] getRegisteredArgsDefinition(){
        ArgumentDefinition[] args = new ArgumentDefinition[registeredArgs.size()];
        int i = 0;
        for(ArgumentDefinition c : registeredArgs){
            args[i] = c;
            i++;
        }
        return args;
    }

    /**
     * Get the ArgumentDefinition object by the type and its name
     * @param arg The arg, Excluding hyphens (-)
     * @return
     */
    public static ArgumentDefinition getByString(String arg){
       CommandObjects type = null;

       if(arg.startsWith("--")){
           type = CommandObjects._2xHYPHEN_ARG;
       } else if (arg.startsWith("-")) {
           type = CommandObjects.ARG;
       }

       String THE_W_STRING;
       boolean id = false;
       if(type == CommandObjects._2xHYPHEN_ARG){
           THE_W_STRING = arg.substring(2);
       } else if(type == CommandObjects.ARG){
           THE_W_STRING = "" + arg.toCharArray()[1];
           id = true;
       }else return null;

       for(ArgumentDefinition def : registeredArgs){
           if(THE_W_STRING.equals((id ? def.argId : def.argName) + "")){
              return def;
           }
       }
        return null;
    }

    public static boolean isRegistered(ArgumentDefinition def){
        return registeredArgs.contains(def);
    }
}
