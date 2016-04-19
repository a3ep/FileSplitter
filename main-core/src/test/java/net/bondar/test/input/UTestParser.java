package net.bondar.test.input;

import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.utils.ApplicationParameterHolder;
import net.bondar.user_input.interfaces.IParameterParser;
import net.bondar.user_input.utils.CliParameterParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test parameter parser.
 */
public class UTestParser {

    /**
     * Parameter parser.
     */
    private static IParameterParser paramParser;

    /**
     * Parameter holder.
     */
    private static IParameterHolder paramHolder;

    /**
     * List of arguments.
     */
    private List<String[]> args = new ArrayList<>();

    @BeforeClass
    /**
     * Initializes parser instance.
     */
    public static void start() {
        paramHolder = new ApplicationParameterHolder();
        paramParser = new CliParameterParser(paramHolder);
    }

    /**
     * Tests the applications work with incorrect arguments.
     */
    @Test
    public void testWrongArguments() {
        args.add(new String[]{"abc"});
        args.add(new String[]{"split", "-p", "asdffsa"});
        args.add(new String[]{"split", "-p", "/home/vsevolod/test/200MB.zip", "-s", "asdasdas"});
        args.add(new String[]{"merge"});
        args.add(new String[]{"merge", "-p", "1231231"});
        args.add(new String[]{""});
        args.add(new String[]{"-p", "/home/vsevolod/test/200MB.zip", "-s", "5M"});
        for (String[] arg : args) {
            try {
                paramParser.parse(arg);
                fail("Different variants of wrong arguments");
            } catch (ApplicationException e) {
                assertEquals(true, e.getMessage().contains("Error during parsing"));
            }
        }
    }

    /**
     * Tests the applications work with correct arguments.
     */
    @Test
    public void testCorrectArguments() {
        args.add(new String[]{"split", "-p", "/test/txt", "-s", "5M"});
        args.add(new String[]{"merge", "-p", "/test/test.txt" + paramHolder.getValue("partSuffix") + "001"});
        args.add(new String[]{"help"});
        args.add(new String[]{"exit"});
        for (String[] arg : args) {
            paramParser.parse(arg);
        }
    }
}
