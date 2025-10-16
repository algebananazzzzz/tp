package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonType;
import seedu.address.model.tag.Tag;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsCorrectNumberOfPersons() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        assertEquals(12, persons.length);
    }

    @Test
    public void getSamplePersons_allPersonsAreNotNull() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        for (Person person : persons) {
            assertNotNull(person);
        }
    }

    @Test
    public void getSamplePersons_containsExpectedPersonTypes() {
        Person[] persons = SampleDataUtil.getSamplePersons();

        long parentCount = 0;
        long tutorCount = 0;
        long studentCount = 0;

        for (Person person : persons) {
            if (person.getPersonType() == PersonType.PARENT) {
                parentCount++;
            } else if (person.getPersonType() == PersonType.TUTOR) {
                tutorCount++;
            } else if (person.getPersonType() == PersonType.STUDENT) {
                studentCount++;
            }
        }

        assertEquals(5, parentCount);
        assertEquals(1, tutorCount);
        assertEquals(6, studentCount);
    }

    @Test
    public void getSampleAddressBook_notNull() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        assertNotNull(addressBook);
    }

    @Test
    public void getSampleAddressBook_containsAllSamplePersons() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        Person[] samplePersons = SampleDataUtil.getSamplePersons();

        assertEquals(samplePersons.length, addressBook.getPersonList().size());

        for (Person samplePerson : samplePersons) {
            boolean found = addressBook.getPersonList().stream()
                .anyMatch(person -> person.isSamePerson(samplePerson));
            assertTrue(found, "Sample person should be in address book: " + samplePerson.getName());
        }
    }

    @Test
    public void getTagSet_emptyArray_returnsEmptySet() {
        Set<Tag> tags = SampleDataUtil.getTagSet();
        assertNotNull(tags);
        assertEquals(0, tags.size());
    }

    @Test
    public void getTagSet_singleTag_returnsSetWithOneTag() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friend");
        assertNotNull(tags);
        assertEquals(1, tags.size());
        assertTrue(tags.contains(new Tag("friend")));
    }

    @Test
    public void getTagSet_multipleTags_returnsSetWithAllTags() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friend", "colleague", "neighbour");
        assertNotNull(tags);
        assertEquals(3, tags.size());
        assertTrue(tags.contains(new Tag("friend")));
        assertTrue(tags.contains(new Tag("colleague")));
        assertTrue(tags.contains(new Tag("neighbour")));
    }

    @Test
    public void getTagSet_duplicateTags_returnsSetWithUniqueTagsOnly() {
        Set<Tag> tags = SampleDataUtil.getTagSet("friend", "friend", "colleague");
        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertTrue(tags.contains(new Tag("friend")));
        assertTrue(tags.contains(new Tag("colleague")));
    }
}
