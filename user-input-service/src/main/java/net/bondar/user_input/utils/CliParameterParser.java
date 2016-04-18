package net.bondar.user_input.utils;


import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.user_input.domain.Command;
import net.bondar.user_input.interfaces.IParametersParser;
import org.apache.commons.cli.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Provides parsing user input arguments.
 */
public class CliParameterParser implements IParametersParser {

    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parameter holder.
     */
    private final IParameterHolder paramHolder;

    /**
     * Cli options.
     */
    private Options options = new Options();

    /**
     * Creates <code>CliParameterParser</code> instance.
     *
     * @param paramHolder parameter holder
     * @see {@link IParameterHolder}
     */
    public CliParameterParser(IParameterHolder paramHolder) {
        this.paramHolder = paramHolder;
        options.addOption("p", true, "Path to the file you want to split.");
        options.addOption("s", true, "Size of the parts (in Mb).");
    }

    /**
     * Parses user input arguments.
     * <br>
     * Input arguments should contains command name and command parameters.
     * If input arguments is empty - shows help message with possible input application parameters.
     *
     * @param args user input parameters
     * @return input command constructed on the basis of input parameters
     * @see {@link IParameterHolder}
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
                    Command.SPLIT.setFileDestination(cmd.getOptionValue("p"));
                    String sizeToString = cmd.getOptionValue("s");
                    long size = Long.parseLong(sizeToString.substring(0, sizeToString.indexOf("M")).replace(" ", ""));
                    Command.SPLIT.setPartSize(size * Integer.parseInt(paramHolder.getValue("mbValue")));
                    currentCommand = Command.SPLIT;
                } else {
                    log.info("Input command -> MERGE");
                    Command.MERGE.setFileDestination(cmd.getOptionValue("p"));
                    currentCommand = Command.MERGE;
                }
            } else {
                log.error("Wrong input command ->" + Arrays.toString(args));
                help();
                return null;
            }
        } catch (NumberFormatException | ParseException e) {
            log.warn("Catches " + e.getClass() + ", during parsing " + Arrays.toString(args) + ". Message " + e.getMessage());
            throw new ApplicationException("Error during parsing " + Arrays.toString(args) + ". Exception:" + e.getMessage());
        }
        log.info("Finish parsing input: " + Arrays.toString(args));
        return currentCommand;
    }

    /**
     * Shows help message with possible input application parameters.
     */
    private void help() {
        log.info("Input \"help\" to show help, \"split -p <args> -s <args>\" to split or \"merge -p <args>\" to merge.\"");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("FileSplitter options.", options);
    }
}
