package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.classroom.TuitionClass;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<TuitionClass> PREDICATE_SHOW_ALL_CLASSES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in
     * the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a tuitionClass with the same identity as {@code tuitionClass}
     * exists in the address book.
     */
    boolean hasClass(TuitionClass tuitionClass);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given tuition class.
     * {@code tuitionClass} must not already exist in the address book.
     */
    void addClass(TuitionClass tuitionClass);

    /**
     * Deletes the given tuition class.
     * The tuition class must exist in the address book.
     */
    void deleteClass(TuitionClass target);

    /**
     * Replaces the given tuition class {@code target} with {@code editedClass}.
     * {@code target} must exist in the address book.
     * The class identity of {@code editedClass} must not be the same as another
     * existing class in the address book.
     */
    void setClass(TuitionClass target, TuitionClass editedClass);

    /**
     * Adds the given person to the specified class.
     * {@code student} must already exist in the address book.
     * {@code toJoin} must already exist in the address book.
     */
    void addStudentToClass(Student student, TuitionClass toJoin);

    /**
     * Adsigns the given tutor to the specified class.
     * {@code tutor} must already exist in the address book.
     * {@code toJoin} must already exist in the address book.
     */
    void assignTutorToClass(Tutor tutor, TuitionClass toJoin);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another
     * existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered class list */
    ObservableList<TuitionClass> getFilteredClassList();

    /**
     * Updates the filter of the filtered person list to filter by the given
     * {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered class list to filter by the given
     * {@code class}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClassList(Predicate<TuitionClass> predicate);
}
