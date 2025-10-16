package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListChildrenCommand;

public class ListChildrenCommandParserTest {

    private final ListChildrenCommandParser parser = new ListChildrenCommandParser();

    @Test
    public void parse_emptyArg_returnsListAllChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("");
        ListChildrenCommand expected = new ListChildrenCommand();

        assertEquals(expected, result);
    }

    @Test
    public void parse_whitespaceArg_returnsListAllChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("   ");
        ListChildrenCommand expected = new ListChildrenCommand();

        assertEquals(expected, result);
    }

    @Test
    public void parse_validParentName_returnsListChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("n/John Doe");
        ListChildrenCommand expected = new ListChildrenCommand("John Doe");

        assertEquals(expected, result);
    }

    @Test
    public void parse_validParentNameWithWhitespace_returnsListChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("  n/John Doe  ");
        ListChildrenCommand expected = new ListChildrenCommand("John Doe");

        assertEquals(expected, result);
    }

    @Test
    public void parse_emptyParentName_returnsListAllChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("n/");
        ListChildrenCommand expected = new ListChildrenCommand();

        assertEquals(expected, result);
    }

    @Test
    public void parse_emptyParentNameWithWhitespace_returnsListAllChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("n/   ");
        ListChildrenCommand expected = new ListChildrenCommand();

        assertEquals(expected, result);
    }

    @Test
    public void parse_parentNameWithSpecialCharacters_returnsListChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("n/John O'Brien");
        ListChildrenCommand expected = new ListChildrenCommand("John O'Brien");

        assertEquals(expected, result);
    }

    @Test
    public void parse_textWithoutPrefix_returnsListAllChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("some random text");
        ListChildrenCommand expected = new ListChildrenCommand();

        assertEquals(expected, result);
    }

    @Test
    public void parse_multipleWordsWithoutPrefix_returnsListAllChildrenCommand() throws Exception {
        ListChildrenCommand result = parser.parse("John Doe");
        ListChildrenCommand expected = new ListChildrenCommand();

        assertEquals(expected, result);
    }
}
