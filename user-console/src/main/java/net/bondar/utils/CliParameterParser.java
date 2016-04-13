package net.bondar.utils;


import net.bondar.Command;
import net.bondar.inretfaces.IParametersParser;
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
                    Command.SPLIT.setSecondParameter(cmd.getOptionValue("s"));
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
        formatter.printHelp("FileSplitter options. Input \"help\" to show help, \"split -p <args> -s <args>\" to split or \"merge -p <args>\" to merge.", options);
        System.exit(0);
    }


}
