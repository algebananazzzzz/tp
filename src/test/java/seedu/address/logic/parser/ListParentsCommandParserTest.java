package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListParentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListParentsCommandParserTest {

    private final ListParentsCommandParser parser = new ListParentsCommandParser();

    // ========== Positive test cases ==========

    @Test
    public void parse_validChildName_returnsListParentsCommand() throws Exception {
        String childName = "Alice Tan";
        ListParentsCommand result = parser.parse("n/" + childName);
        ListParentsCommand expected = new ListParentsCommand(childName);
        assertEquals(expected, result);
    }

    @Test
    public void parse_validChildNameWithExtraWhitespace_returnsListParentsCommand() throws Exception {
        ListParentsCommand result = parser.parse("  n/Alice Tan  ");
        ListParentsCommand expected = new ListParentsCommand("Alice Tan");
        assertEquals(expected, result);
    }

    @Test
    public void parse_childNameWithSpecialCharacters_returnsListParentsCommand() throws Exception {
        String childName = "Alice Smith 2nd";
        ListParentsCommand result = parser.parse("n/" + childName);
        ListParentsCommand expected = new ListParentsCommand(childName);
        assertEquals(expected, result);
    }

    @Test
    public void parse_childNameWithNumbers_returnsListParentsCommand() throws Exception {
        String childName = "Child 123";
        ListParentsCommand result = parser.parse("n/" + childName);
        ListParentsCommand expected = new ListParentsCommand(childName);
        assertEquals(expected, result);
    }

    @Test
    public void parse_childNameWithUnicodeCharacters_returnsListParentsCommand() throws Exception {
        String childName = "李明 (Li Ming)";
        ListParentsCommand result = parser.parse("n/" + childName);
        ListParentsCommand expected = new ListParentsCommand(childName);
        assertEquals(expected, result);
    }

    // ========== Negative test cases (should throw ParseException) ==========

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("   "));
        assertThrows(ParseException.class, () -> parser.parse("Alice Tan"));
    }

    @Test
    public void parse_emptyChildName_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("n/"));
    }

    @Test
    public void parse_emptyChildNameWithWhitespace_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("n/   "));
    }

    @Test
    public void parse_multiplePrefixes_usesLastValue() throws Exception {
        // ArgumentTokenizer takes the last value when duplicates exist
        ListParentsCommand result = parser.parse(" n/Alice n/Bob");
        ListParentsCommand expected = new ListParentsCommand("Bob");
        assertEquals(expected, result);
    }

    // ========== Edge cases and boundary tests ==========

    @Test
    public void parse_nullArg_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

    @Test
    public void parse_singleCharacterChildName_returnsListParentsCommand() throws Exception {
        ListParentsCommand result = parser.parse("n/A");
        ListParentsCommand expected = new ListParentsCommand("A");
        assertEquals(expected, result);
    }

    @Test
    public void parse_childNameWithLeadingSpaces_trimmed() throws Exception {
        ListParentsCommand result = parser.parse("n/   Alice Tan");
        ListParentsCommand expected = new ListParentsCommand("Alice Tan");
        assertEquals(expected, result);
    }

    @Test
    public void parse_childNameWithTrailingSpaces_trimmed() throws Exception {
        ListParentsCommand result = parser.parse("n/Alice Tan   ");
        ListParentsCommand expected = new ListParentsCommand("Alice Tan");
        assertEquals(expected, result);
    }

    @Test
    public void parse_casePreservation_returnsExactCase() throws Exception {
        String childName = "ALiCe TaN";
        ListParentsCommand result = parser.parse("n/" + childName);
        ListParentsCommand expected = new ListParentsCommand(childName);
        assertEquals(expected, result);
    }

}
