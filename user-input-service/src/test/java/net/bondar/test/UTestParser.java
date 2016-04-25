package net.bondar.test;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.utils.ApplicationConfigHolder;
import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.*;
import net.bondar.input.service.InputParserService;
import net.bondar.input.utils.*;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.fail;
import static org.testng.Assert.assertEquals;

/**
 * Test parameter parser.
 */
public class UTestParser {

    /**
     * Parameter parser.
     */
    private static IInputParserService inputParserService;

    /**
     * Part-file suffix.
     */
    private static String partSuffix;

    /**
     * Initializes parser instance.
     */
    @BeforeClass
    public static void setUp() {
        IConfigHolder configHolder = EasyMock.createMock(ApplicationConfigHolder.class);
        expect(configHolder.getValue("partSuffix")).andReturn("_part_").times(0, Integer.MAX_VALUE);
        EasyMock.replay(configHolder);
        partSuffix = configHolder.getValue("partSuffix");
        ICommandHolder commandHolder = new CommandHolder();
        ICommandFinder commandFinder = new FileCommandFinder(commandHolder);
        IParameterHolder parameterHolder = new ParameterHolder();
        IParameterFinder parameterFinder = new FileParameterFinder(parameterHolder);
        AbstractConverterFactory converterFactory = new ConverterFactory();
        IParameterParser parameterParser = new ParameterParser(converterFactory);
        ICommandVerifier commandVerifier = new FileCommandVerifier(configHolder);
        inputParserService = new InputParserService(commandFinder, parameterFinder, parameterParser, commandVerifier);
    }

    /**
     * Initializes correct testing arguments.
     */
    @DataProvider(name = "testParseIncorrect")
    public Object[][] createData1() {
        return new Object[][]{
                {"abc"},
                {"split, -p, /home/test/test.txt, -s, asdasdas"},
                {"merge"},
                {"-p, /home/test/test.test, -s, 5M"}
        };
    }

    /**
     * Initializes correct testing arguments.
     */
    @DataProvider(name = "testParseCorrect")
    public Object[][] createData2() {
        return new Object[][]{
                {"split, -p, /test.txt, -s, 5MB"},
                {"merge, -p, /test/test.txt" + partSuffix + "001"},
                {"help"},
                {"exit"}
        };
    }

    /**
     * Tests the applications work with incorrect arguments.
     */
    @Test(dataProvider = "testParseIncorrect")
    public void testParseIncorrect(String arg) {
        try {
            inputParserService.parse(arg.split(", "));
            fail("Expected incorrect arguments. Arguments: " + arg);
        } catch (ParsingException e) {
            assertEquals(ParsingException.class, e.getClass());
        }
    }

    /**
     * Tests the applications work with correct arguments.
     */
    @Test(dataProvider = "testParseCorrect")
    public void testCorrectArguments(String arg) {
        inputParserService.parse(arg.split(", "));
    }
}
