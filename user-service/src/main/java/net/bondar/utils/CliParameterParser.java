package net.bondar.utils;


import net.bondar.domain.Command;
import net.bondar.interfaces.IParametersParser;
import org.apache.commons.cli.*;

/**
 *
 */
public class CliParameterParser implements IParametersParser{
    private Options options = new Options();

    public CliParameterParser() {
        options.addOption("p", true, "Path to the file you want to split.");
        options.addOption("s", true, "Size of the parts (in Mb).");
    }

    public Command parse(String[] args) {
        Command currentCommand = null;
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
           String arg = (String)cmd.getArgList().get(0);
            if (arg.equals("help")) {
                help();
            }
            else if(arg.equals("exit")){
                currentCommand = Command.EXIT;
            }
            else if (cmd.hasOption("p")) {
                if (cmd.hasOption("s")) {
                    Command.SPLIT.setFirstParameter(cmd.getOptionValue("p"));
                    String sizeToString = cmd.getOptionValue("s");
                    int size = Integer.parseInt(sizeToString.substring(0, sizeToString.indexOf("M")).replace(" ", "")) * 512 * 1024;
                    Command.SPLIT.setSecondParameter(size);
                    currentCommand = Command.SPLIT;
                } else {
                    Command.MERGE.setFirstParameter(cmd.getOptionValue("p"));
                    currentCommand = Command.MERGE;
                }
            }else{
                help();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentCommand;
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("FileSplitter options. \nInput \"help\" to show help, \n\"split -p <args> -s <args>\" to split \nor \"merge -p <args>\" to merge.", options);
        System.exit(0);
    }


}
