package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonType;

/**
 * Parses input arguments and creates a new FilterCommand object.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    @Override
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap map = ArgumentTokenizer.tokenize(" " + args, PREFIX_PERSON_TYPE);

        // No preamble allowed, and ro/ is mandatory
        if (!arePrefixesPresent(map, PREFIX_PERSON_TYPE) || !map.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String roleRaw = map.getValue(PREFIX_PERSON_TYPE).orElse("").trim();
        PersonType role = ParserUtil.parsePersonType(roleRaw);

        return new FilterCommand(role);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
