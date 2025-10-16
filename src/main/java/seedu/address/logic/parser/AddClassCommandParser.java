package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTOR;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddClassCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddClass object
 */
public class AddClassCommandParser implements Parser<AddClassCommand> {

    @Override
    public AddClassCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmed = args.trim();
        String cmd = AddClassCommand.COMMAND_WORD;
        if (trimmed.regionMatches(true, 0, cmd, 0, cmd.length())) {
            trimmed = trimmed.substring(cmd.length()).trim();
        }

        ArgumentMultimap map = ArgumentTokenizer.tokenize(" " + trimmed, PREFIX_CLASS, PREFIX_TUTOR);

        if (!arePrefixesPresent(map, PREFIX_CLASS) || !map.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE));
        }

        String className = ParserUtil.parseClassName(map.getValue(PREFIX_CLASS).get());

        if (className.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE));
        }

        // Check for invalid prefix like t/ in the class name value
        if (className.contains(" t/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClassCommand.MESSAGE_USAGE));
        }

        // Only parse tutor name if present
        if (map.getValue(PREFIX_TUTOR).isPresent()) {
            String tutorName = ParserUtil.parseTutorName(map.getValue(PREFIX_TUTOR).get());
            return tutorName.isEmpty() ? new AddClassCommand(className) : new AddClassCommand(className, tutorName);
        }
        return new AddClassCommand(className);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
