package net.bondar.user_input.utils;


import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.user_input.domain.Command;
import net.bondar.user_input.interfaces.IParameterParser;
import org.apache.commons.cli.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides parsing user input arguments.
 */
public class CliParameterParser implements IParameterParser {

    private static final int MEGABYTE_VALUE = 1048576;
    /**
     * Logger.
     */
    private final Logger log = LogManager.getLogger(getClass());

    /**
     * Parameter holder.
     */
    private final IParameterHolder paramHolder;

    /**
     * Command line parser.
     */
    private final CommandLineParser parser;

    /**
     * Cli options.
     */
    private Options options = new Options();

    /**
     * List of command's names.
     */
    private List<String> commandNames = new ArrayList<>();

    /**
     * Creates <code>CliParameterParser</code> instance.
     *
     * @param paramHolder parameter holder
     * @see {@link IParameterHolder}
     */
    public CliParameterParser(IParameterHolder paramHolder) {
        this.paramHolder = paramHolder;
        parser = new BasicParser();
        for (Command c : Command.values()) {
            commandNames.add(c.name().toLowerCase());
        }
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
     * @throws ApplicationException when occurred error during parsing parameters
     * @see {@link IParameterHolder}
     */
    public Command parse(String[] args) throws ApplicationException {
        log.debug("Start parsing input: " + Arrays.toString(args));
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.getArgList().isEmpty()) {
                log.error("Wrong input command ->" + Arrays.toString(args));
                help();
                throw new ApplicationException("Wrong input command ->" + Arrays.toString(args) + ". Please check your input.");
            }
            String arg = (String) cmd.getArgList().get(0);
            if (arg.equals("help")) {
                help();
            } else if (arg.equals("exit")) {
                log.debug("Input command -> EXIT");
                return Command.EXIT;
            } else if (!commandNames.contains(arg)) {
                log.error("Wrong command ->" + arg);
                throw new ApplicationException("Wrong command. Please check your input.");
            } else if (cmd.hasOption("p")) {
                String destString = cmd.getOptionValue("p");
                if (cmd.hasOption("s")) {
                    String sizeString = cmd.getOptionValue("s");
                    Pattern p = Pattern.compile("^[0-9]+");
                    Matcher m = p.matcher(sizeString.substring(0, sizeString.length() - 1));
                    if (!sizeString.contains("M") || !m.matches()) {
                        log.error("Wrong part size ->" + sizeString);
                        throw new ApplicationException("Wrong part size. Please check your input.");
                    }
                    log.debug("Input command -> SPLIT");
                    Command.SPLIT.setFileDestination(cmd.getOptionValue("p"));
                    String sizeToString = cmd.getOptionValue("s");
                    long size = Long.parseLong(sizeToString.substring(0, sizeToString.indexOf("M")).replace(" ", ""));
                    Command.SPLIT.setPartSize(size * MEGABYTE_VALUE);
                    return Command.SPLIT;
                } else {
                    if (!destString.contains(paramHolder.getValue("partSuffix"))) {
                        log.error("Wrong file destination ->" + destString);
                        throw new ApplicationException("Wrong file destination. Please check your input.");
                    }
                    log.debug("Input command -> MERGE");
                    Command.MERGE.setFileDestination(cmd.getOptionValue("p"));
                    return Command.MERGE;
                }
            } else {
                log.error("Wrong input command ->" + Arrays.toString(args));
                help();
                throw new ApplicationException("Wrong input command ->" + Arrays.toString(args) + ". Please check your input.");
            }
        } catch (NumberFormatException | ParseException | ApplicationException e) {
            log.warn("Error while parsing " + Arrays.toString(args) + ". Message " + e.getMessage());
            throw new ApplicationException("Error while parsing " + Arrays.toString(args) + ".", e);
        }
        log.debug("Finish parsing input: " + Arrays.toString(args));
        return Command.EMPTY;
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
