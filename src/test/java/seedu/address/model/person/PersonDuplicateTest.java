package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

/**
 * Test to verify duplicate checking now considers roles
 */
public class PersonDuplicateTest {

    @Test
    public void isSamePerson_sameNameDifferentRole_returnsFalse() {
        // Create a student named "John Doe"
        Person student = new PersonBuilder()
                .withName("John Doe")
                .withPhone("91234567")
                .withEmail("john@student.com")
                .withPersonType(PersonType.STUDENT)
                .build();

        // Create a parent with same name
        Person parent = new PersonBuilder()
                .withName("John Doe")
                .withPhone("98765432")
                .withEmail("john@parent.com")
                .withPersonType(PersonType.PARENT)
                .build();

        // After fix: same name + different role = NOT duplicate
        assertFalse(student.isSamePerson(parent),
            "Same name with different role should NOT be considered duplicate");

        // Same name + same role = duplicate
        Person anotherStudent = new PersonBuilder()
                .withName("John Doe")
                .withPhone("87654321")
                .withEmail("john2@student.com")
                .withPersonType(PersonType.STUDENT)
                .build();

        assertTrue(student.isSamePerson(anotherStudent),
            "Same name with same role should be considered duplicate");

        System.out.println("✓ Person.isSamePerson() now checks both name AND role (line 140-142)");
    }
}
