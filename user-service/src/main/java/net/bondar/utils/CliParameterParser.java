package net.bondar.utils;


import net.bondar.domain.Command;
import net.bondar.exceptions.ApplicationException;
import net.bondar.interfaces.IParametersParser;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 *
 */
public class CliParameterParser implements IParametersParser {

    /**
     *
     */
    private final Logger log = Logger.getLogger("userLogger");

    /**
     *
     */
    private Options options = new Options();

    /**
     *
     */
    public CliParameterParser() {
        options.addOption("p", true, "Path to the file you want to split.");
        options.addOption("s", true, "Size of the parts (in Mb).");
    }

    /**
     * @param args
     * @return
     */
    public Command parse(String[] args) {
        log.info("Start parsing input: " + Arrays.toString(args));
        Command currentCommand = null;
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String arg = (String) cmd.getArgList().get(0);
            if (arg.equals("help")) {
                help();
            } else if (arg.equals("exit")) {
                log.info("Input command -> EXIT");
                currentCommand = Command.EXIT;
            } else if (cmd.hasOption("p")) {
                if (cmd.hasOption("s")) {
                    log.info("Input command -> SPLIT");
                    Command.SPLIT.setFirstParameter(cmd.getOptionValue("p"));
                    String sizeToString = cmd.getOptionValue("s");
                    int size = Integer.parseInt(sizeToString.substring(0, sizeToString.indexOf("M")).replace(" ", "")) * 512 * 1024;
                    Command.SPLIT.setSecondParameter(size);
                    currentCommand = Command.SPLIT;
                } else {
                    log.info("Input command -> MERGE");
                    Command.MERGE.setFirstParameter(cmd.getOptionValue("p"));
                    currentCommand = Command.MERGE;
                }
            } else {
                log.error("Wrong input command ->" + Arrays.toString(args));
                help();
                return currentCommand;
            }
        } catch (NumberFormatException | ParseException e) {
            log.warn("Catches " + e.getClass() + ", during parsing " + Arrays.toString(args) + ". Message " + e.getMessage());
            throw new ApplicationException("Error during parsing " + Arrays.toString(args) + ". Exception:" + e.getMessage());
        }
        log.info("Finish parsing input: " + Arrays.toString(args));
        return currentCommand;
    }

    /**
     *
     */
    private void help() {
        log.info("Input \"help\" to show help, \"split -p <args> -s <args>\" to split or \"merge -p <args>\" to merge.\"");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("FileSplitter options.", options);
    }


}
