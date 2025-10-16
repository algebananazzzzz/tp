package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Parent;
import seedu.address.model.person.PersonType;
import seedu.address.model.person.Student;

/**
 * Links an existing {@link Student} to a {@link Parent}.
 *
 * Command format:
 * {@code add_child /p PARENT_NAME /c CHILD_NAME}
 */
public class AddChildCommand extends Command {

    public static final String COMMAND_WORD = "add_child";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a child to a parent in TutBook.\n"
            + "Parameters: /p PARENT_NAME /c CHILD_NAME\n"
            + "Example: " + COMMAND_WORD + " /p John Tan /c Emily Tan";

    public static final String MESSAGE_SUCCESS = "Linked %1$s as child of %2$s";
    public static final String MESSAGE_PARENT_NOT_FOUND =
            "Parent not found: %1$s. Please ensure the parent exists.";
    public static final String MESSAGE_CHILD_NOT_FOUND =
            "Student not found: %1$s. Please ensure the child exists.";
    public static final String MESSAGE_PARENT_AMBIGUOUS =
            "Multiple parents named \"%1$s\" found. Please disambiguate.";
    public static final String MESSAGE_CHILD_AMBIGUOUS =
            "Multiple children named \"%1$s\" found. Please disambiguate.";
    public static final String MESSAGE_ALREADY_LINKED =
            "%1$s is already linked to parent %2$s.";

    private final String parentNameRaw;
    private final String childNameRaw;

    /**
     * Constructs an AddStudentCommand.
     *
     * @param parentNameRaw Name of the parent.
     * @param childNameRaw  Name of the child.
     */
    public AddChildCommand(String parentNameRaw, String childNameRaw) {
        requireNonNull(parentNameRaw);
        requireNonNull(childNameRaw);
        this.parentNameRaw = parentNameRaw.trim();
        this.childNameRaw = childNameRaw.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Parent parent = findUniqueParentByName(model, parentNameRaw);
        Student child = findUniqueStudentByName(model, childNameRaw);

        // Check if already linked
        if (parent.getChildren().contains(child)) {
            throw new CommandException(String.format(MESSAGE_ALREADY_LINKED,
                    child.getName(), parent.getName()));
        }

        // Link both sides (bidirectional)
        parent.addChild(child);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                child.getName(), parent.getName()));
    }

    /**
     * Finds a unique {@link Parent} by name.
     */
    private Parent findUniqueParentByName(Model model, String name) throws CommandException {
        List<Person> persons = model.getAddressBook().getPersonList();
        Parent found = null;

        for (Person p : persons) {
            if (p.getPersonType() == PersonType.PARENT && p.getName().fullName.equalsIgnoreCase(name)) {
                if (found != null) {
                    throw new CommandException(String.format(MESSAGE_PARENT_AMBIGUOUS, name));
                }
                found = (Parent) p;
            }
        }

        if (found == null) {
            throw new CommandException(String.format(MESSAGE_PARENT_NOT_FOUND, name));
        }

        return found;
    }

    /**
     * Finds a unique {@link Student} by name.
     */
    private Student findUniqueStudentByName(Model model, String name) throws CommandException {
        List<Person> persons = model.getAddressBook().getPersonList();
        Student found = null;

        for (Person p : persons) {
            if (p.getPersonType() == PersonType.STUDENT && p.getName().fullName.equalsIgnoreCase(name)) {
                if (found != null) {
                    throw new CommandException(String.format(MESSAGE_CHILD_AMBIGUOUS, name));
                }
                found = (Student) p;
            }
        }

        if (found == null) {
            throw new CommandException(String.format(MESSAGE_CHILD_NOT_FOUND, name));
        }

        return found;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddChildCommand)) {
            return false;
        }
        AddChildCommand o = (AddChildCommand) other;
        return parentNameRaw.equals(o.parentNameRaw)
                && childNameRaw.equals(o.childNameRaw);
    }
}
