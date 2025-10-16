package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LinkCommandParserTest {

    private final LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("  "));
    }

    @Test
    public void parse_validArgs_returnsLinkCommand() throws Exception {
        LinkCommand expected = new LinkCommand("John Doe", "Jane Doe");
        LinkCommand result = parser.parse("parent/John Doe child/Jane Doe");
        assertEquals(expected, result);
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsLinkCommand() throws Exception {
        LinkCommand expected = new LinkCommand("John Doe", "Jane Doe");
        LinkCommand result = parser.parse("  parent/John Doe   child/Jane Doe  ");
        assertEquals(expected, result);
    }

    @Test
    public void parse_missingParentPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("John Doe child/Jane Doe"));
    }

    @Test
    public void parse_missingChildPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("parent/John Doe Jane Doe"));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("extra parent/John Doe child/Jane Doe"));
    }

    @Test
    public void parse_missingAllPrefixes_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("some random text"));
    }

    @Test
    public void parse_onlyParentPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("parent/John Doe"));
    }

    @Test
    public void parse_onlyChildPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("child/Jane Doe"));
    }

    @Test
    public void parse_reversedOrder_returnsLinkCommand() throws Exception {
        LinkCommand expected = new LinkCommand("John Doe", "Jane Doe");
        LinkCommand result = parser.parse("child/Jane Doe parent/John Doe");
        assertEquals(expected, result);
    }
}
