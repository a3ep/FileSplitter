package net.bondar.test;

import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.utils.ApplicationParameterHolder;
import net.bondar.user_input.interfaces.IParameterParser;
import net.bondar.user_input.utils.CliParameterParser;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.expect;
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
     * Part-file suffix.
     */
    private static String partSuffix;

    /**
     * Initializes parser instance.
     */
    @BeforeClass
    public static void setUp() {
        IParameterHolder paramHolder = EasyMock.createMock(ApplicationParameterHolder.class);
        paramParser = new CliParameterParser(paramHolder);
        expect(paramHolder.getValue("partSuffix")).andReturn("_part_").times(0, Integer.MAX_VALUE);
        EasyMock.replay(paramHolder);
        partSuffix = paramHolder.getValue("partSuffix");
    }

    /**
     * Initializes correct testing arguments.
     */
    @DataProvider(name = "testParseIncorrect")
    public Object[][] createData1() {
        return new Object[][]{
                {"abc"},
                {"split, -p, asdffsa",},
                {"split, -p, /home/test/test.txt, -s, asdasdas"},
                {"merge"},
                {"merge, -p, 1231231",},
                {"merge, -p, 1231231",},
                {"-p, /home/test/test.test, -s, 5M"}
        };
    }

    /**
     * Initializes correct testing arguments.
     */
    @DataProvider(name = "testParseCorrect")
    public Object[][] createData2() {
        return new Object[][]{
                {"split, -p, /test.txt, -s, 5M"},
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
            paramParser.parse(arg.split(", "));
            fail("Expected incorrect arguments. Arguments: " + arg);
        } catch (ApplicationException e) {
            assertEquals(ApplicationException.class, e.getClass());
        }
    }

    /**
     * Tests the applications work with correct arguments.
     */
    @Test(dataProvider = "testParseCorrect")
    public void testCorrectArguments(String arg) {
        paramParser.parse(arg.split(", "));
    }
}
