package net.bondar.test;

import net.bondar.core.interfaces.IConfigHolder;
import net.bondar.core.utils.ConfigHolder;
import net.bondar.input.exceptions.ParsingException;
import net.bondar.input.interfaces.*;
import net.bondar.input.interfaces.client.AbstractParameterConverterFactory;
import net.bondar.input.service.ParserService;
import net.bondar.input.utils.*;
import net.bondar.splitter.utils.Command;
import net.bondar.splitter.utils.FileParameterConverterFactory;
import net.bondar.splitter.utils.Parameter;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.fail;

/**
 * Test parameter parser.
 */
public class UTestParser {

    /**
     * Parameter parser.
     */
    private static IParserService inputParserService;

    /**
     * Part-file suffix.
     */
    private static String partSuffix;

    /**
     * Initializes parser instance.
     */
    @BeforeClass
    public static void setUp() {
        IConfigHolder configHolder = EasyMock.createMock(ConfigHolder.class);
        expect(configHolder.getValue("partSuffix")).andReturn("_part_").anyTimes();
        EasyMock.replay(configHolder);
        partSuffix = configHolder.getValue("partSuffix");
        CommandHolder commandHolder = new CommandHolder(Arrays.asList(Command.values()));
        CommandFinder commandFinder = new CommandFinder(commandHolder);
        ParameterHolder parameterHolder = new ParameterHolder(Arrays.asList(Parameter.values()));
        ParameterFinder parameterFinder = new ParameterFinder(parameterHolder);
        AbstractParameterConverterFactory converterFactory = new FileParameterConverterFactory();
        ParameterParser parameterParser = new ParameterParser(parameterFinder, converterFactory);
        inputParserService = new ParserService(commandFinder, parameterParser);
    }

    /**
     * Initializes incorrect testing arguments.
     */
    @DataProvider(name = "testParseIncorrect")
    public Object[][] createIncorrectData() {
        return new Object[][]{
                {""},
                {"01234"},
                {"abcde"},
                {"split, -p, /test.txt, -s, a1b2c3"},
                {"sp1it, -p, /home/test/test.txt, -s, a1b2c3"},
                {"sp1it, -p, /home/test/test.txt, -1, 10MB"},
                {"merge"},
                {"merge, -1, /test/test.txt" + partSuffix + "001"},
                {"he1p"},
                {"-p, /home/test/test.test, -s, 5M"}
        };
    }

    /**
     * Initializes correct testing arguments.
     */
    @DataProvider(name = "testParseCorrect")
    public Object[][] createCorrectData() {
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
    public void testParseIncorrect(final String arg) {
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
    public void testCorrectArguments(final String arg) {
        inputParserService.parse(arg.split(", "));
    }
}
