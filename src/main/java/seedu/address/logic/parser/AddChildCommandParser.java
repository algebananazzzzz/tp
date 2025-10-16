package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import seedu.address.logic.commands.AddChildCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link AddChildCommand} object.
 * <p>
 * Expected command format:
 * {@code add_child /p PARENT_NAME /c CHILD_NAME}
 */
public class AddChildCommandParser implements Parser<AddChildCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@link AddChildCommand}
     * and returns a new AddChildCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public AddChildCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PARENT, PREFIX_STUDENT);

        // Ensure both prefixes are present and no preamble (extra text before first prefix)
        if (!arePrefixesPresent(argMultimap, PREFIX_PARENT, PREFIX_STUDENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddChildCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PARENT, PREFIX_STUDENT);

        String parentName = argMultimap.getValue(PREFIX_PARENT).get().trim();
        String childName = argMultimap.getValue(PREFIX_STUDENT).get().trim();

        return new AddChildCommand(parentName, childName);
    }

    /**
     * Returns true if none of the prefixes contains empty values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        for (Prefix prefix : prefixes) {
            if (argumentMultimap.getValue(prefix).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
