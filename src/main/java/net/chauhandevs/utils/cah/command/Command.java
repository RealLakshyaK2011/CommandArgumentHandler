package net.chauhandevs.utils.cah.command;

import net.chauhandevs.utils.cah.argument.Argument;

import java.util.ArrayList;
import java.util.List;

public class Command {
    public final Argument[] givenArgs;
    public final CommandDefinition command;
    public Command(Argument[] args, CommandDefinition def){
        this.givenArgs = args;
        this.command = def;
    }

    public void performAction(){
        command.performAction(this);
    }

    public static final class CommandFactory{
        private CommandDefinition definition;
        private List<Argument> arguments = new ArrayList<>();
        private List<Argument.ArgumentFactory> constructableArgs = new ArrayList<>();

        public Command ConstructCommand() {
            if(definition == null){
                throw new RuntimeException("Cannot build when definition is not provided");
            }
            for (Argument.ArgumentFactory constructAble: constructableArgs){
                arguments.add(constructAble.ConstructArg());
            }
            for(Argument arg : arguments){
                if(!definition.allowedArguments.contains(arg.arg)) return null;
            }
            return new Command(arguments.toArray(new Argument[0]), definition);
        }

        public void setCommandDefinition(CommandDefinition def){
            this.definition = def;
        }

        public CommandFactory addArgument(Argument arg){
            arguments.add(arg);
            return this;
        }

        public CommandFactory addArgument(Argument.ArgumentFactory cons){
            constructableArgs.add(cons);
            return this;
        }

        public CommandFactory removeArgument(Argument arg){
            arguments.remove(arg);
            return this;
        }

        public CommandDefinition getDefinition() {
            return definition;
        }

        public List<Argument> getArgs() {
            return arguments;
        }
    }
}
