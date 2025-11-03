package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListChildrenCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListChildrenCommandParserTest {

    private final ListChildrenCommandParser parser = new ListChildrenCommandParser();

    // ========== Positive test cases ==========

    @Test
    public void parse_validParentName_returnsListChildrenCommand() throws Exception {
        String parentName = "John Doe";
        ListChildrenCommand result = parser.parse("n/" + parentName);
        ListChildrenCommand expected = new ListChildrenCommand(parentName);
        assertEquals(expected, result);
    }

    @Test
    public void parse_validParentNameWithExtraWhitespace_returnsListChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("  n/John Doe  ");
        ListChildrenCommand expected = new ListChildrenCommand("John Doe");
        assertEquals(expected, result);
    }

    @Test
    public void parse_parentNameWithSpecialCharacters_returnsListChildrenCommand() throws Exception {
        String parentName = "John O'Brien-Smith";
        ListChildrenCommand result = parser.parse("n/" + parentName);
        ListChildrenCommand expected = new ListChildrenCommand(parentName);
        assertEquals(expected, result);
    }

    @Test
    public void parse_parentNameWithNumbers_returnsListChildrenCommand() throws Exception {
        String parentName = "Parent 123";
        ListChildrenCommand result = parser.parse("n/" + parentName);
        ListChildrenCommand expected = new ListChildrenCommand(parentName);
        assertEquals(expected, result);
    }

    @Test
    public void parse_parentNameWithUnicodeCharacters_returnsListChildrenCommand() throws Exception {
        String parentName = "李明 (Li Ming)";
        ListChildrenCommand result = parser.parse("n/" + parentName);
        ListChildrenCommand expected = new ListChildrenCommand(parentName);
        assertEquals(expected, result);
    }

    // ========== Negative test cases (should throw ParseException) ==========

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("   "));
        assertThrows(ParseException.class, () -> parser.parse("some random text"));
        assertThrows(ParseException.class, () -> parser.parse("John Doe"));
    }

    @Test
    public void parse_emptyParentName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("n/"));
        assertThrows(ParseException.class, () -> parser.parse("n/   "));
    }

    @Test
    public void parse_multiplePrefixes_usesLastValue() throws Exception {
        // ArgumentTokenizer takes the last value when duplicates exist
        ListChildrenCommand result = parser.parse(" n/John n/Jane");
        ListChildrenCommand expected = new ListChildrenCommand("Jane");
        assertEquals(expected, result);
    }

    @Test
    public void parse_textBeforePrefix_usesPrefix() throws Exception {
        // With space prepended, tokenizer accepts text before prefix
        ListChildrenCommand result = parser.parse("invalid n/John");
        ListChildrenCommand expected = new ListChildrenCommand("John");
        assertEquals(expected, result);
    }

    @Test
    public void parse_nullArg_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_singleCharacterParentName_returnsListChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("n/A");
        ListChildrenCommand expected = new ListChildrenCommand("A");
        assertEquals(expected, result);
    }

    @Test
    public void parse_parentNameWithSpaces_trimmed() throws Exception {
        ListChildrenCommand result = parser.parse("n/   John Doe   ");
        ListChildrenCommand expected = new ListChildrenCommand("John Doe");
        assertEquals(expected, result);
    }
}
