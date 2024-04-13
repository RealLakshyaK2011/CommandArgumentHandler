import net.chauhandevs.utils.cah.CommandArgumentHandler;
import net.chauhandevs.utils.cah.command.Command;

public class TEST2 {
    public static void main(String[] args) {
        String string = "HELLO MEWO -G --GGS \"APPLE\" \"GIMME MONEY \\\"ROBBERY!\\\" \"";
        System.out.println(string);
        CommandArgumentHandler.handleCommand(string);
    }
}
