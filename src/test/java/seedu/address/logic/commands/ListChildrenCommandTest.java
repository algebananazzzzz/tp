package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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

public class ListChildrenCommandTest {

    @Test
    public void execute_listAllChildren_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        Student student1 = new Student(
                new Name("Alice Tan"),
                new Phone("91234567"),
                new Email("alice@example.com"),
                new Address("123 Main St"),
                new java.util.HashSet<>()
        );
        Student student2 = new Student(
                new Name("Bob Lee"),
                new Phone("98765432"),
                new Email("bob@example.com"),
                new Address("456 Second St"),
                new java.util.HashSet<>()
        );

        model.addPerson(student1);
        model.addPerson(student2);

        ListChildrenCommand command = new ListChildrenCommand();
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Listed all children"));
        assertTrue(feedback.contains("Alice Tan"));
        assertTrue(feedback.contains("Bob Lee"));
    }

    @Test
    public void execute_noChildren_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        ListChildrenCommand command = new ListChildrenCommand();
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Listed all children"));
        assertTrue(feedback.contains("[No children]"));
    }

    @Test
    public void execute_listChildrenByParent_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        Student student1 = new Student(
                new Name("Charlie Wong"),
                new Phone("91234567"),
                new Email("charlie@example.com"),
                new Address("123 Main St"),
                new java.util.HashSet<>()
        );
        Student student2 = new Student(
                new Name("David Lim"),
                new Phone("98765432"),
                new Email("david@example.com"),
                new Address("456 Second St"),
                new java.util.HashSet<>()
        );
        Parent parent = new Parent(
                new Name("John Doe"),
                new Phone("87654321"),
                new Email("john@example.com"),
                new Address("789 Third St"),
                new java.util.HashSet<>()
        );

        model.addPerson(student1);
        model.addPerson(student2);
        model.addPerson(parent);

        parent.addChild(student1);
        parent.addChild(student2);

        ListChildrenCommand command = new ListChildrenCommand("John Doe");
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Listed children for parent: John Doe"));
        assertTrue(feedback.contains("Charlie Wong"));
        assertTrue(feedback.contains("David Lim"));
    }

    @Test
    public void execute_parentNotFound_throwsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        ListChildrenCommand command = new ListChildrenCommand("NonExistent Parent");

        assertThrows(CommandException.class, String.format(ListChildrenCommand.MESSAGE_PARENT_NOT_FOUND,
                "NonExistent Parent"), () -> command.execute(model));
    }

    @Test
    public void execute_parentWithNoChildren_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        Parent parent = new Parent(
                new Name("Jane Smith"),
                new Phone("87654321"),
                new Email("jane@example.com"),
                new Address("789 Third St"),
                new java.util.HashSet<>()
        );

        model.addPerson(parent);

        ListChildrenCommand command = new ListChildrenCommand("Jane Smith");
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Listed children for parent: Jane Smith"));
        assertTrue(feedback.contains("[No children]"));
    }

    @Test
    public void execute_caseInsensitiveParentName_success() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        Student student = new Student(
                new Name("Emily Tan"),
                new Phone("91234567"),
                new Email("emily@example.com"),
                new Address("123 Main St"),
                new java.util.HashSet<>()
        );
        Parent parent = new Parent(
                new Name("John Doe"),
                new Phone("87654321"),
                new Email("john@example.com"),
                new Address("789 Third St"),
                new java.util.HashSet<>()
        );

        model.addPerson(student);
        model.addPerson(parent);
        parent.addChild(student);

        ListChildrenCommand command = new ListChildrenCommand("john doe");
        CommandResult result = command.execute(model);

        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Listed children for parent: john doe"));
        assertTrue(feedback.contains("Emily Tan"));
    }

    @Test
    public void equals() {
        ListChildrenCommand listAllCommand = new ListChildrenCommand();
        ListChildrenCommand listAllCommandCopy = new ListChildrenCommand();
        ListChildrenCommand listByParentCommand = new ListChildrenCommand("John Doe");
        ListChildrenCommand listByParentCommandCopy = new ListChildrenCommand("John Doe");
        ListChildrenCommand listByDifferentParentCommand = new ListChildrenCommand("Jane Smith");

        assertTrue(listAllCommand.equals(listAllCommand));

        assertTrue(listAllCommand.equals(listAllCommandCopy));

        assertFalse(listAllCommand.equals(1));

        assertFalse(listAllCommand.equals(null));

        assertFalse(listAllCommand.equals(listByParentCommand));

        assertTrue(listByParentCommand.equals(listByParentCommandCopy));

        assertFalse(listByParentCommand.equals(listByDifferentParentCommand));
    }
}
