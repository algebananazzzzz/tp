package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.ListChildrenCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListChildrenCommand object.
 */
public class ListChildrenCommandParser implements Parser<ListChildrenCommand> {

    @Override
    public ListChildrenCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmed = args.trim();

        if (trimmed.isEmpty()) {
            return new ListChildrenCommand();
        }

        ArgumentMultimap map = ArgumentTokenizer.tokenize(" " + trimmed, PREFIX_NAME);

        if (map.getValue(PREFIX_NAME).isPresent()) {
            String parentName = map.getValue(PREFIX_NAME).get().trim();

            if (parentName.isEmpty()) {
                return new ListChildrenCommand();
            }

            return new ListChildrenCommand(parentName);
        }

        return new ListChildrenCommand();
    }
}
