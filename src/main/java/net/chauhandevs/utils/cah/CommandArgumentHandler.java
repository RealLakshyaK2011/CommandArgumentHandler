package net.chauhandevs.utils.cah;

import net.chauhandevs.utils.cah.argument.Argument;
import net.chauhandevs.utils.cah.command.Command;
import net.chauhandevs.utils.cah.command.CommandObjects;
import net.chauhandevs.utils.cah.enums.CommandParsingErrorDefinitions;
import net.chauhandevs.utils.cah.registries.ArgumentRegistry;
import net.chauhandevs.utils.cah.registries.CommandRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandArgumentHandler {
    /**
     * Parses the given string command.
     *
     * @param command Command to handle
     *
     */
    public static void handleCommand(String command){
        int i;

        boolean insideQuotes = false;
        boolean checkEscapeAfter = false;
        StringBuilder buffer = new StringBuilder();
        List<String> arrayBuffer = new ArrayList<>();

        List<Character> escapeAbles = new ArrayList<>();
        escapeAbles.add('"');

        /*CHARACTER BY CHARACTER APPROACH*/
        for (var $char : command.toCharArray()){
            if(insideQuotes){
                if(checkEscapeAfter){
                    checkEscapeAfter = false;
                    if(escapeAbles.contains($char)){
                        buffer.replace(buffer.length()-1, buffer.length(), "");
                        buffer.append($char);
                    }
                }else{
                    if($char == '\\'){
                        checkEscapeAfter = true;
                        buffer.append($char);
                    }else if($char == '"'){
                        buffer = new StringBuilder(emptyBuffer(buffer, arrayBuffer));
                        insideQuotes = false;
                    }else{
                        buffer.append($char);
                    }
                }
            }else {
                if($char == '"'){
                    insideQuotes = true;

                    buffer = new StringBuilder(emptyBuffer(buffer, arrayBuffer));
                }else if($char == ' '){
                    buffer = new StringBuilder(emptyBuffer(buffer, arrayBuffer));
                }else{
                    buffer.append($char);
                }
            }

        }
        buffer = new StringBuilder(emptyBuffer(buffer, arrayBuffer));

        String[] processedParts = arrayBuffer.toArray(new String[0]);
        System.out.println(Arrays.toString(processedParts));

        //Resolve The command.
        Command.CommandFactory factory = new Command.CommandFactory();
        CommandObjects expectation = CommandObjects.COMMAND;
        Argument.ArgumentFactory currentArgument = null;

        CommandParsingErrorDefinitions error = null;
        String gotContext = "";

        for(i = 0; i < processedParts.length; i++){
            if(error != null) break;
            switch (expectation){
                case COMMAND:
                    var c = CommandRegistry.getByString(processedParts[i]);
                    if(c == null){
                        error = CommandParsingErrorDefinitions.EXPECTING_COMMAND;
                        gotContext = processedParts[i];
                        break;
                    }
                    factory.setCommandDefinition(c);
                    if(c.allowedArguments.size() > 0){
                        expectation = CommandObjects.ARG;
                    }else expectation = null;
                    break;
                case ARG:
                    var a = ArgumentRegistry.getByString(processedParts[i]);
                    if(a == null){
                        error = CommandParsingErrorDefinitions.EXPECTING_ARG;
                        gotContext = processedParts[i];
                        break;
                    }
                    if(!factory.getDefinition().allowedArguments.contains(a)){
                        error = CommandParsingErrorDefinitions.ARG_NOT_ALLOWED;
                        gotContext = processedParts[i];
                        break;
                    }
                    currentArgument = new Argument.ArgumentFactory().setArgumentDefinition(a);
                    factory.addArgument(currentArgument);
                    if(a.maxOptions > 0)
                        expectation = CommandObjects.OPTION;
                    break;
                case OPTION:
                    if(currentArgument.getDefinition().maxOptions <= currentArgument.getOptions().length){
                        //Decreasing i by 1 so that next loop will be with same iteration number but will go in ARG case.
                        i--;
                        expectation = CommandObjects.ARG;
                        break;
                    }
                    if(processedParts[i].startsWith("-")){
                        if(currentArgument.getDefinition().minOptions > 0){
                            error = CommandParsingErrorDefinitions.EXPECTING_MORE_OPTIONS;
                            break;
                        }
                        //Decreasing i by 1 so that next loop will be with same iteration number but will go in ARG case.
                        i--;
                        expectation = CommandObjects.ARG;
                        break;
                    }
                    currentArgument.addOption(processedParts[i]);
                    break;
                default:

                    break;
            }
        }

        //Check for errors
        if(error != null){
            switch (error){
                case EXPECTING_ARG:
                    System.out.println("Expecting argument, got " + gotContext);
                    break;
                case EXPECTING_COMMAND:
                    System.out.println("Expecting command, got " + gotContext);
                    break;
                case ARG_NOT_ALLOWED:
                    System.out.println("The given argument " + gotContext + ", is not allowed with command " + factory.getDefinition().commandName);
                    break;
                case EXPECTING_MORE_OPTIONS:
                    System.out.println("Expecting more options to argument " + currentArgument.getDefinition().argName);
                    break;
                default:
                    System.out.println("An unknown error occurred while processing the given command!");
                    break;
            }
            return;
        }

        Command c = factory.ConstructCommand();

        c.performAction();
    }

    private static String emptyBuffer(StringBuilder st, List<String> l){
        if(st == null || st.toString().equals(""))return "";
        l.add(st.toString());
        return "";
    }
}