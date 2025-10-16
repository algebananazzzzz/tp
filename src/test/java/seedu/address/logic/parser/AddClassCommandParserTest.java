package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddClassCommandParserTest {

    private final AddClassCommandParser parser = new AddClassCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("  "));
    }

    @Test
    public void parse_validArgsMinimal_returnsAddClassCommand() throws Exception {
        System.out.println("Testing minimal case...");
        AddClassCommand result = parser.parse("c/Sec1-Math-A");
        System.out.println("Parsed successfully: " + result);

        AddClassCommand expected = new AddClassCommand("Sec1-Math-A");
        System.out.println("Expected: " + expected);
        System.out.println("Are they equal? " + result.equals(expected));

        assertEquals(expected, result);
    }

    @Test
    public void parse_validArgsWithTutor_returnsAddClassCommand() throws Exception {
        AddClassCommand expected = new AddClassCommand("Sec1-Math-A", "Ms Lee");
        try {
            AddClassCommand result = parser.parse("c/Sec1-Math-A tutor/Ms Lee");
            assertEquals(expected, result);
        } catch (ParseException e) {
            System.out.println("ParseException message: " + e.getMessage());
            throw e; // re-throw to fail the test
        }
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        // class name without c/ is preamble -> invalid in strict mode
        assertThrows(ParseException.class, () -> parser.parse("Sec1-Math-A"));
        assertThrows(ParseException.class, () -> parser.parse("   Sec1-Math-A   "));
    }

    @Test
    public void parse_invalidLegacyTutorPrefix_throwsParseException() {
        // t/ is not accepted; only tutor/
        assertThrows(ParseException.class, () -> parser.parse("c/Sec1-Math-A t/Ms Lee"));
    }

    @Test
    public void parse_missingClassNamePrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("tutor/Ms Lee"));
    }

    @Test
    public void parse_emptyClassNameValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("c/   tutor/Ms Lee"));
    }

    @Test
    public void parse_emptyTutorNameValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("c/Math101 tutor/   "));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_returnsAddClassCommand() throws Exception {
        AddClassCommand expected = new AddClassCommand("Math101", "Ms Lee");
        AddClassCommand result = parser.parse("  c/Math101   tutor/Ms Lee  ");
        assertEquals(expected, result);
    }
}
