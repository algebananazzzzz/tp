package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTOR;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.TuitionClass;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutor;

/**
 * Adds a new TuitionClass to the system.
 * Supports optional tutor assignment.
 */
public class AddClassCommand extends Command {

    public static final String COMMAND_WORD = "addclass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a class to TutBook.\n"
            + "Parameters: " + PREFIX_CLASS + "CLASS_NAME [" + PREFIX_TUTOR + "TUTOR_NAME]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " " + PREFIX_CLASS + "Sec2-Math-A\n"
            + "  " + COMMAND_WORD + " " + PREFIX_CLASS + "Sec2-Math-A " + PREFIX_TUTOR + "Ms Lim";

    public static final String MESSAGE_SUCCESS = "New class added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLASS = "This class already exists.";
    public static final String MESSAGE_TUTOR_NOT_FOUND = "Tutor not found: %1$s. Please ensure the tutor exists.";
    public static final String MESSAGE_TUTOR_AMBIGUOUS = "Multiple tutors named \"%1$s\" found. Please disambiguate.";

    private final String classNameRaw;
    private final String tutorNameRaw; // optional

    public AddClassCommand(String classNameRaw) {
        this(classNameRaw, null);
    }

    /**
     * Adds a class to the address book.
     */
    public AddClassCommand(String classNameRaw, String tutorNameRaw) {
        requireNonNull(classNameRaw);
        this.classNameRaw = classNameRaw.trim();
        this.tutorNameRaw = (tutorNameRaw == null) ? null : tutorNameRaw.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ClassName className = new ClassName(classNameRaw);

        Tutor tutor = null;
        if (tutorNameRaw != null && !tutorNameRaw.isEmpty()) {
            tutor = findTutorByName(model, tutorNameRaw);
        }

        TuitionClass newClass = (tutor == null)
                ? new TuitionClass(className)
                : new TuitionClass(className, tutor);

        if (model.hasClass(newClass)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLASS);
        }

        model.addClass(newClass);
        final String displayName = newClass.getClassName().toString();
        return new CommandResult(String.format(MESSAGE_SUCCESS, displayName));
    }

    /**
     * Finds a unique Tutor in the address book by name (case-insensitive).
     */
    private Tutor findTutorByName(Model model, String name) throws CommandException {
        List<Person> persons = model.getAddressBook().getPersonList();
        Tutor found = null;

        for (Person p : persons) {
            if (p instanceof Tutor && p.getName().fullName.equalsIgnoreCase(name)) {
                if (found != null) {
                    throw new CommandException(String.format(MESSAGE_TUTOR_AMBIGUOUS, name));
                }
                found = (Tutor) p;
            }
        }

        if (found == null) {
            throw new CommandException(String.format(MESSAGE_TUTOR_NOT_FOUND, name));
        }

        return found;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddClassCommand)) {
            return false;
        }
        AddClassCommand o = (AddClassCommand) other;
        return classNameRaw.equals(o.classNameRaw)
                && ((tutorNameRaw == null && o.tutorNameRaw == null)
                        || (tutorNameRaw != null && tutorNameRaw.equals(o.tutorNameRaw)));
    }
}
