import net.chauhandevs.utils.cah.CommandArgumentHandler;
import net.chauhandevs.utils.cah.argument.*;
import net.chauhandevs.utils.cah.callback.CommandCallbackHook;
import net.chauhandevs.utils.cah.command.*;
import net.chauhandevs.utils.cah.registries.*;

import java.util.ArrayList;
import java.util.List;

public class REAL_TEST1 {
    public static void main(String[] args) {
        ArgumentDefinition arg1 = new ArgumentDefinition(ArgumentType.WITH_OPTIONS, "help", '?', 0, 0);
        ArgumentDefinition arg2 = new ArgumentDefinition(ArgumentType.WITH_OPTIONS, "exit", 'q', 1, 0);
        ArgumentDefinition arg3 = new ArgumentDefinition(ArgumentType.WITH_OPTIONS, "addData", '+', 10, 1);
        ArgumentDefinition arg4 = new ArgumentDefinition(ArgumentType.WITH_OPTIONS, "removeIndex", '-', 1, 1);
        ArgumentRegistry.registerArg(arg1);
        ArgumentRegistry.registerArg(arg2);
        ArgumentRegistry.registerArg(arg3);
        ArgumentRegistry.registerArg(arg4);

        List<ArgumentDefinition> allowedDefs = new ArrayList<>();
        allowedDefs.add(arg1);
        allowedDefs.add(arg2);
        allowedDefs.add(arg3);
        allowedDefs.add(arg4);

        CommandDefinition definition = new CommandDefinition("theCommand", CommandType.WITH_ARGS, allowedDefs, new Callback());
        CommandRegistry.registerCommand(definition);

        CommandArgumentHandler.handleCommand("theCommand -? -q ohio -+ \"Cheez\" \"Yoink!\"");
    }
}

class Callback implements CommandCallbackHook{

    @Override
    public void callbackFunction(Argument[] args) {
        System.out.println("Hello, World! From callbackFunction");
        System.out.println("Given Args: ");
        for (int i = 0; i < args.length; i++){
            ArgumentDefinition def = args[i].arg;
            System.out.println(def.argName + " --- " + def.argId + ":");
            for(int j = 0; j < args[i].givenOptions.length; j++){
                System.out.println("--------" + args[i].givenOptions[j]);
            }
        }
    }
}
