package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

public class LinkCommandTest {

    private Model model;
    private Parent parent;
    private Student student;
    private Tutor tutor;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());

        parent = new Parent(
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("john@example.com"),
                new Address("10 Kent Ridge Road"),
                new HashSet<>());

        student = new Student(
                new Name("Jane Doe"),
                new Phone("87654321"),
                new Email("jane@example.com"),
                new Address("20 Kent Ridge Road"),
                new HashSet<>());

        tutor = new Tutor(
                new Name("Mr Smith"),
                new Phone("91234567"),
                new Email("smith@example.com"),
                new Address("1 Tutor Lane"),
                new HashSet<>());

        model.addPerson(parent);
        model.addPerson(student);
        model.addPerson(tutor);
    }

    @Test
    public void constructor_nullParentName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkCommand(null, "Jane Doe"));
    }

    @Test
    public void constructor_nullChildName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LinkCommand("John Doe", null));
    }

    @Test
    public void execute_linkSuccess() throws Exception {
        LinkCommand command = new LinkCommand("John Doe", "Jane Doe");
        CommandResult result = command.execute(model);

        assertEquals(String.format(LinkCommand.MESSAGE_SUCCESS, "John Doe", "Jane Doe"),
                result.getFeedbackToUser());
        assertTrue(parent.getChildren().contains(student));
        assertTrue(student.getParents().contains(parent));
    }

    @Test
    public void execute_parentNotFound_throwsCommandException() {
        LinkCommand command = new LinkCommand("NonExistent Parent", "Jane Doe");
        assertThrows(CommandException.class, LinkCommand.MESSAGE_PARENT_NOT_EXIST, () ->
                command.execute(model));
    }

    @Test
    public void execute_childNotFound_throwsCommandException() {
        LinkCommand command = new LinkCommand("John Doe", "NonExistent Child");
        assertThrows(CommandException.class, LinkCommand.MESSAGE_CHILD_NOT_EXIST, () ->
                command.execute(model));
    }

    @Test
    public void execute_notParentType_throwsCommandException() {
        LinkCommand command = new LinkCommand("Mr Smith", "Jane Doe");
        assertThrows(CommandException.class, LinkCommand.MESSAGE_NOT_PARENT, () ->
                command.execute(model));
    }

    @Test
    public void execute_notStudentType_throwsCommandException() {
        LinkCommand command = new LinkCommand("John Doe", "Mr Smith");
        assertThrows(CommandException.class, LinkCommand.MESSAGE_NOT_STUDENT, () ->
                command.execute(model));
    }

    @Test
    public void execute_alreadyLinked_throwsCommandException() throws Exception {
        // Link parent and child first
        new LinkCommand("John Doe", "Jane Doe").execute(model);

        // Try to link again
        LinkCommand command = new LinkCommand("John Doe", "Jane Doe");
        assertThrows(CommandException.class, LinkCommand.MESSAGE_ALREADY_LINKED, () ->
                command.execute(model));
    }

    @Test
    public void equals() {
        LinkCommand command1 = new LinkCommand("John Doe", "Jane Doe");
        LinkCommand command2 = new LinkCommand("John Doe", "Jane Doe");
        LinkCommand command3 = new LinkCommand("Bob Lim", "Jane Doe");
        LinkCommand command4 = new LinkCommand("John Doe", "Alice Tan");

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different parent -> returns false
        assertFalse(command1.equals(command3));

        // different child -> returns false
        assertFalse(command1.equals(command4));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different type -> returns false
        assertFalse(command1.equals(1));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        LinkCommand command = new LinkCommand("John Doe", "Jane Doe");
        assertTrue(command.toString().contains("John Doe"));
        assertTrue(command.toString().contains("Jane Doe"));
    }
}
