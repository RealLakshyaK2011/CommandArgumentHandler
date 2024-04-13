package net.chauhandevs.utils.cah.argument;

import java.util.ArrayList;
import java.util.List;

public class Argument {
    public final String[] givenOptions;
    public final ArgumentDefinition arg;
    public Argument(String[] args, ArgumentDefinition def){
        this.givenOptions = args;
        this.arg = def;
    }

    public static final class ArgumentFactory{

        private ArgumentDefinition definition;
        private List<String> options = new ArrayList<>();

        public Argument ConstructArg() {
            if(definition == null) throw new RuntimeException("Cannot build when definition is not provided");
            return new Argument(options.toArray(new String[0]), definition);
        }

        public ArgumentFactory setArgumentDefinition(ArgumentDefinition def){
            this.definition = def;
            return this;
        }

        public ArgumentFactory addOption(String arg){
            options.add(arg);
            return this;
        }

        public ArgumentFactory removeArgument(String option){
            options.remove(option);
            return this;
        }

        public ArgumentDefinition getDefinition() {
            return definition;
        }

        public String[] getOptions() {
            return options.toArray(new String[0]);
        }
    }
}
