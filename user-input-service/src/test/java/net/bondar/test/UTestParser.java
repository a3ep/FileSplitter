package net.bondar.test;

import net.bondar.splitter.exceptions.ApplicationException;
import net.bondar.splitter.interfaces.IParameterHolder;
import net.bondar.splitter.utils.ApplicationParameterHolder;
import net.bondar.user_input.interfaces.IParameterParser;
import net.bondar.user_input.utils.CliParameterParser;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * List of correct arguments.
     */
    private List<String> correctArgs = new ArrayList<>();

    /**
     * List of incorrect arguments.
     */
    private List<String> incorrectArgs = new ArrayList<>();


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
                {"merge, -p, /test/test.txt\" + partSuffix + \"001",},
                {"help"},
                {"exit"}
        };
    }


    /**
     * Initializes parser instance.
     */
    @org.testng.annotations.BeforeClass
    public static void setUp() {
        IParameterHolder paramHolder = EasyMock.createMock(ApplicationParameterHolder.class);
        paramParser = new CliParameterParser(paramHolder);
        expect(paramHolder.getValue("partSuffix")).andReturn("_part_").times(0, Integer.MAX_VALUE);
        EasyMock.replay(paramHolder);
        partSuffix = paramHolder.getValue("partSuffix");
    }

//    /**
//     * Initializes testing arguments.
//     */
//    @Before
//    public void setUpTestParseCorrect(){
//        incorrectArgs.add("abc");
//        incorrectArgs.add("split, -p, asdffsa");
//        incorrectArgs.add("split, -p, /home/test/test.txt, -s, asdasdas");
//        incorrectArgs.add("merge");
//        incorrectArgs.add("merge, -p, 1231231");
//        incorrectArgs.add("");
//        incorrectArgs.add("-p, /home/test/test.test, -s, 5M");
//        correctArgs.add("split, -p, /test.txt, -s, 5M");
//        correctArgs.add("merge, -p, /test/test.txt" + partSuffix + "001");
//        correctArgs.add("help");
//        correctArgs.add("exit");
//    }

    /**
     * Tests the applications work with incorrect arguments.
     */
    @org.testng.annotations.Test(dataProvider = "testParseCorrect")
    public void testParseIncorrect(String arg) {
        try {
            paramParser.parse(arg.split(", "));
            fail("Expected incorrect arguments. Arguments: " + arg);
        } catch (ApplicationException e) {
            assertEquals(ApplicationException.class, e.getClass());
        }
//        for (String[] arg : getArgs(incorrectArgs)) {
//            try {
//                paramParser.parse(arg);
//                fail("Expected incorrect arguments. Arguments: " + Arrays.toString(arg));
//            } catch (ApplicationException e) {
//                assertEquals(ApplicationException.class, e.getClass());
//            }
//        }
    }

    /**
     * Tests the applications work with correct arguments.
     */
    @Test
    public void testCorrectArguments() {
        for (String[] arg : getArgs(correctArgs)) {
            paramParser.parse(arg);
        }
    }

    /**
     * Cleaning testing arguments.
     */
    @After
    public void tearDown() {
        incorrectArgs.clear();
        correctArgs.clear();
    }

    /**
     * Gets list of arguments.
     *
     * @param argsToString list of arguments strings
     * @return list of  arguments arrays
     */
    private List<String[]> getArgs(List<String> argsToString) {
        return argsToString.stream().map(arg -> arg.split(", ")).collect(Collectors.toList());
    }
}
