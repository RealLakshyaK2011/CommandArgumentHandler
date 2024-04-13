package net.chauhandevs.utils.cah.argument;

public class ArgumentDefinition {
    public final ArgumentType Type;
    public final String  argName;
    public final char argId;
    public final int maxOptions;
    public final int minOptions;
    public ArgumentDefinition(ArgumentType type, String argName, char argId, int maxOptions, int minOptions){
        /**
         * maxOptions + minOptions == 0 if they both are 0. (Check for if they both are 0 or not)
         */
        if(type == ArgumentType.FLAG && (maxOptions + minOptions != 0)){
            throw new RuntimeException("Argument type is given FLAG but max and min options are not 0!");
        }
        this.Type = type;
        this.maxOptions = maxOptions;
        this.minOptions = minOptions;
        this.argName = argName;
        this.argId = argId;
    }
}
