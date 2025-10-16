package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;

/**
 * Filters persons by role (Student / Tutor / Parent).
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters contacts by role.\n"
            + "Parameters: ro/STUDENT|TUTOR|PARENT\n"
            + "Example: " + COMMAND_WORD + " ro/student";

    public static final String MESSAGE_SUCCESS = "Filtered by role: %s (%d shown)";
    public static final String MESSAGE_EMPTY_AFTER_FILTER = "No contacts match role: %s";

    private final PersonType role;
    private final Predicate<Person> predicate;

    /**
     * Creates a filter command that shows persons whose concrete type matches {@code role}.
     */
    public FilterCommand(PersonType role) {
        this.role = requireNonNull(role);
        this.predicate = person -> person.getPersonType() == role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        int size = model.getFilteredPersonList().size();

        if (size == 0) {
            // Keep the filter applied (so UI shows empty list), but tell user clearly.
            return new CommandResult(String.format(MESSAGE_EMPTY_AFTER_FILTER, role.name().toLowerCase()));
        }

        // Standard feedback includes count + keeps current filtered list in UI
        return new CommandResult(String.format(MESSAGE_SUCCESS, role.name().toLowerCase(), size));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FilterCommand)) {
            return false;
        }
        FilterCommand o = (FilterCommand) other;
        return role == o.role;
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{role=" + role + "}";
    }
}
