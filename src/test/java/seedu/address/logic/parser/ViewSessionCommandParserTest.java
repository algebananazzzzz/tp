package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ViewSessionCommandParserTest {

    private final ViewSessionCommandParser parser = new ViewSessionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = "c/Math101 s/Week 1 Tutorial";
        ViewSessionCommand expectedCommand = new ViewSessionCommand("Math101", "Week 1 Tutorial");

        ViewSessionCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_whitespaceInValues_trimmedCorrectly() throws Exception {
        String userInput = "c/  Math101  s/  Week 3 Tutorial  ";
        ViewSessionCommand expectedCommand = new ViewSessionCommand("Math101", "Week 3 Tutorial");

        ViewSessionCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_missingClassName_throwsParseException() {
        String userInput = "s/Week 1 Tutorial";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingSessionName_throwsParseException() {
        String userInput = "c/Math101";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyClassName_throwsParseException() {
        String userInput = "c/   s/Week 1 Tutorial";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptySessionName_throwsParseException() {
        String userInput = "c/Math101 s/   ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        String userInput = "some preamble c/Math101 s/Week 1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateClassPrefix_throwsParseException() {
        String userInput = "c/Math101 c/Math102 s/Week 1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicateSessionPrefix_throwsParseException() {
        String userInput = "c/Math101 s/Week 1 s/Week 2";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_whitespaceOnlyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

    @Test
    public void parse_validArgsWithExtraWhitespace_success() throws Exception {
        String userInput = "  c/Math101   s/Week 1 Tutorial  ";
        ViewSessionCommand expectedCommand = new ViewSessionCommand("Math101", "Week 1 Tutorial");

        ViewSessionCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_sessionNameWithSpecialCharacters_success() throws Exception {
        String userInput = "c/Math101 s/Week 1 - Advanced Tutorial (Part A)";
        ViewSessionCommand expectedCommand = new ViewSessionCommand("Math101",
                "Week 1 - Advanced Tutorial (Part A)");

        ViewSessionCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_classNameWithSpaces_success() throws Exception {
        String userInput = "c/Advanced Math 101 s/Week 1 Tutorial";
        ViewSessionCommand expectedCommand = new ViewSessionCommand("Advanced Math 101", "Week 1 Tutorial");

        ViewSessionCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_multipleClassesInInput_throwsParseException() {
        String userInput = "c/Math101 c/Science101 s/Week 1";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_multipleSessionsInInput_throwsParseException() {
        String userInput = "c/Math101 s/Week 1 s/Week 2";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_onlyPrefixesWithoutValues_throwsParseException() {
        String userInput = "c/ s/";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_classNameAndSessionNameWithNumbers_success() throws Exception {
        String userInput = "c/Math101 s/Tutorial 1";
        ViewSessionCommand expectedCommand = new ViewSessionCommand("Math101", "Tutorial 1");

        ViewSessionCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_classNameWithNumbers_success() throws Exception {
        String userInput = "c/Math101-2024 s/Week 1";
        ViewSessionCommand expectedCommand = new ViewSessionCommand("Math101-2024", "Week 1");

        ViewSessionCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }
}

