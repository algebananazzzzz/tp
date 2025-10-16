package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLASSES;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.TuitionClass;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * Edits the name of an existing class in TutBook.
 */
public class EditClassCommand extends Command {

    public static final String COMMAND_WORD = "editclass";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the name of an existing class.\n"
            + "Parameters: o/OLD_CLASS_NAME c/NEW_CLASS_NAME\n"
            + "  o/ specifies the old class name\n"
            + "  c/ specifies the new class name\n"
            + "  Class names can contain spaces.\n"
            + "Example: " + COMMAND_WORD + " o/Sec2-Math-A c/Sec3-Math-A\n"
            + "Example: " + COMMAND_WORD + " o/Advanced Math c/Honors Mathematics";

    public static final String MESSAGE_EDIT_CLASS_SUCCESS = "Edited class from %1$s to %2$s";
    public static final String MESSAGE_CLASS_NOT_FOUND = "Class %1$s does not exist";
    public static final String MESSAGE_DUPLICATE_CLASS = "Class %1$s already exists";

    private final String oldClassName;
    private final String newClassName;

    /**
     * Creates an EditClassCommand to edit the specified class.
     * @param oldClassName The current name of the class to be edited
     * @param newClassName The new name for the class
     */
    public EditClassCommand(String oldClassName, String newClassName) {
        requireNonNull(oldClassName);
        requireNonNull(newClassName);
        this.oldClassName = oldClassName;
        this.newClassName = newClassName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        TuitionClass oldClass = model.getFilteredClassList().stream()
                .filter(c -> c.getName().value.equals(oldClassName))
                .findFirst()
                .orElse(null);

        if (oldClass == null) {
            throw new CommandException(String.format(MESSAGE_CLASS_NOT_FOUND, oldClassName));
        }

        TuitionClass newClass = new TuitionClass(new ClassName(newClassName));

        if (!oldClass.isSameClass(newClass) && model.hasClass(newClass)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_CLASS, newClassName));
        }

        for (Student student : new ArrayList<>(oldClass.getStudents())) {
            student.removeClass(oldClass);
            newClass.addStudent(student);
        }

        if (oldClass.isAssignedToTutor()) {
            Tutor tutor = oldClass.getTutor();
            tutor.removeClass(oldClass);
            newClass.setTutor(tutor);
        }

        model.setClass(oldClass, newClass);
        model.updateFilteredClassList(PREDICATE_SHOW_ALL_CLASSES);

        return new CommandResult(String.format(MESSAGE_EDIT_CLASS_SUCCESS, oldClassName, newClassName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditClassCommand)) {
            return false;
        }

        EditClassCommand otherCommand = (EditClassCommand) other;
        return oldClassName.equals(otherCommand.oldClassName)
                && newClassName.equals(otherCommand.newClassName);
    }

    @Override
    public int hashCode() {
        return oldClassName.hashCode() + newClassName.hashCode();
    }
}
