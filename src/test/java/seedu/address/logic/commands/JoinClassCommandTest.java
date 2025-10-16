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
import seedu.address.model.classroom.ClassName;
import seedu.address.model.classroom.TuitionClass;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

public class JoinClassCommandTest {

    private Model model;
    private Student student;
    private Tutor tutor;
    private TuitionClass tuitionClass;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());

        student = new Student(
                new Name("Alice Tan"),
                new Phone("98765432"),
                new Email("alice@example.com"),
                new Address("10 Kent Ridge Road"),
                new HashSet<>());

        tutor = new Tutor(
                new Name("Mr Smith"),
                new Phone("91234567"),
                new Email("smith@example.com"),
                new Address("1 Tutor Lane"),
                new HashSet<>());

        tuitionClass = new TuitionClass(new ClassName("CS2103T"));

        model.addPerson(student);
        model.addPerson(tutor);
        model.addClass(tuitionClass);
    }

    @Test
    public void constructor_nullClassName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JoinClassCommand("Alice Tan", null));
    }

    @Test
    public void constructor_nullPersonName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JoinClassCommand(null, "CS2103T"));
    }

    @Test
    public void execute_studentJoinSuccess() throws Exception {
        JoinClassCommand command = new JoinClassCommand("Alice Tan", "CS2103T");
        CommandResult result = command.execute(model);

        assertEquals(String.format(JoinClassCommand.MESSAGE_SUCCESS, "Student", "CS2103T", "Alice Tan"),
                result.getFeedbackToUser());
        assertTrue(tuitionClass.hasStudent(student));
    }

    @Test
    public void execute_tutorJoinSuccess() throws Exception {
        JoinClassCommand command = new JoinClassCommand("Mr Smith", "CS2103T");
        CommandResult result = command.execute(model);

        assertEquals(String.format(JoinClassCommand.MESSAGE_SUCCESS, "Tutor", "CS2103T", "Mr Smith"),
                result.getFeedbackToUser());
        assertTrue(tuitionClass.hasTutor(tutor));
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        JoinClassCommand command = new JoinClassCommand("NonExistent Person", "CS2103T");
        assertThrows(CommandException.class, JoinClassCommand.MESSAGE_PERSON_NOT_EXIST, () ->
                command.execute(model));
    }

    @Test
    public void execute_classNotFound_throwsCommandException() {
        JoinClassCommand command = new JoinClassCommand("Alice Tan", "NonExistent");
        assertThrows(CommandException.class, JoinClassCommand.MESSAGE_CLASS_NOT_EXIST, () ->
                command.execute(model));
    }

    @Test
    public void execute_studentAlreadyInClass_throwsCommandException() throws Exception {
        // Add student to class first
        new JoinClassCommand("Alice Tan", "CS2103T").execute(model);

        // Try to add again
        JoinClassCommand command = new JoinClassCommand("Alice Tan", "CS2103T");
        assertThrows(CommandException.class, JoinClassCommand.MESSAGE_STUDENT_ALREADY_IN_CLASS, () ->
                command.execute(model));
    }

    @Test
    public void execute_tutorAlreadyInClass_throwsCommandException() throws Exception {
        // Add tutor to class first
        new JoinClassCommand("Mr Smith", "CS2103T").execute(model);

        // Try to add again
        JoinClassCommand command = new JoinClassCommand("Mr Smith", "CS2103T");
        assertThrows(CommandException.class, JoinClassCommand.MESSAGE_TUTOR_ALREADY_ASSIGNED, () ->
                command.execute(model));
    }

    @Test
    public void equals() {
        JoinClassCommand command1 = new JoinClassCommand("Alice Tan", "CS2103T");
        JoinClassCommand command2 = new JoinClassCommand("Alice Tan", "CS2103T");
        JoinClassCommand command3 = new JoinClassCommand("Bob Lim", "CS2103T");
        JoinClassCommand command4 = new JoinClassCommand("Alice Tan", "CS2101");

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        assertTrue(command1.equals(command2));

        // different person -> returns false
        assertFalse(command1.equals(command3));

        // different class -> returns false
        assertFalse(command1.equals(command4));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different type -> returns false
        assertFalse(command1.equals(1));
    }

    @Test
    public void toString_returnsCorrectFormat() {
        JoinClassCommand command = new JoinClassCommand("Alice Tan", "CS2103T");
        assertTrue(command.toString().contains("Alice Tan"));
        assertTrue(command.toString().contains("CS2103T"));
    }
}
