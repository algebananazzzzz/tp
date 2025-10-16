package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private final Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FilterCommand studentFilter = new FilterCommand(PersonType.STUDENT);
        FilterCommand tutorFilter = new FilterCommand(PersonType.TUTOR);

        // same object -> returns true
        assertTrue(studentFilter.equals(studentFilter));

        // same values -> returns true
        FilterCommand studentFilterCopy = new FilterCommand(PersonType.STUDENT);
        assertTrue(studentFilter.equals(studentFilterCopy));

        // different types -> returns false
        assertFalse(studentFilter.equals(1));

        // null -> returns false
        assertFalse(studentFilter.equals(null));

        // different role -> returns false
        assertFalse(studentFilter.equals(tutorFilter));
    }

    @Test
    public void execute_studentRole_filtersCorrectly() {
        execute_role_filtersCorrectly(PersonType.STUDENT);
    }

    @Test
    public void execute_tutorRole_filtersCorrectly() {
        execute_role_filtersCorrectly(PersonType.TUTOR);
    }

    @Test
    public void execute_parentRole_filtersCorrectly() {
        execute_role_filtersCorrectly(PersonType.PARENT);
    }

    @Test
    public void execute_emptyAddressBook_showsEmptyMessage() {
        // Empty models
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmpty = new ModelManager(new AddressBook(), new UserPrefs());

        FilterCommand command = new FilterCommand(PersonType.STUDENT);
        String expectedMessage = "No contacts match role: student";

        assertCommandSuccess(command, emptyModel, expectedMessage, expectedEmpty);
        assertEquals(0, emptyModel.getFilteredPersonList().size());
    }

    @Test
    public void toStringMethod() {
        FilterCommand cmd = new FilterCommand(PersonType.STUDENT);
        String expected = FilterCommand.class.getCanonicalName() + "{role=" + PersonType.STUDENT + "}";
        assertEquals(expected, cmd.toString());
    }

    /**
     * Executes filter for a given role, computes expected list dynamically from the typical data,
     * and asserts the feedback and filtered contents.
     */
    private void execute_role_filtersCorrectly(PersonType role) {
        Predicate<Person> predicate = p -> p.getPersonType() == role;

        // Build expected list and message dynamically (robust to changes in typical data)
        ObservableList<Person> expectedList = expectedModel.getAddressBook()
                .getPersonList().filtered(predicate);
        expectedModel.updateFilteredPersonList(predicate);

        int expectedCount = expectedList.size();
        final String roleLower = role.name().toLowerCase();

        final String expectedMessage = (expectedCount == 0)
            ? "No contacts match role: " + roleLower
            : String.format("Filtered by role: %s (%d shown)", roleLower, expectedCount);

        FilterCommand command = new FilterCommand(role);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // extra safety: the two filtered lists should be identical element-wise
        var actualIds = model.getFilteredPersonList().stream().collect(Collectors.toList());
        var expectedIds = expectedModel.getFilteredPersonList().stream().collect(Collectors.toList());
        assertTrue(Objects.equals(actualIds, expectedIds));
    }
}
