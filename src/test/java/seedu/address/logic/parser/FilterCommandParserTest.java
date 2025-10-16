package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonType;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validStudent_returnsFilterCommand() throws Exception {
        FilterCommand expected = new FilterCommand(PersonType.STUDENT);
        assertEquals(expected, parser.parse("ro/student"));
        assertEquals(expected, parser.parse("  ro/STUDENT  ")); // case-insensitive, trimmed
    }

    @Test
    public void parse_validTutor_returnsFilterCommand() throws Exception {
        FilterCommand expected = new FilterCommand(PersonType.TUTOR);
        assertEquals(expected, parser.parse("ro/tutor"));
    }

    @Test
    public void parse_validParent_returnsFilterCommand() throws Exception {
        FilterCommand expected = new FilterCommand(PersonType.PARENT);
        assertEquals(expected, parser.parse("ro/parent"));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("student"));
    }

    @Test
    public void parse_invalidRole_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("ro/mentor"));
        assertThrows(ParseException.class, () -> parser.parse("ro/"));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("something ro/student"));
    }
}
