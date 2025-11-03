package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ArgumentTokenizerTest {

    private final Prefix prefixA = new Prefix("a/");
    private final Prefix prefixB = new Prefix("b/");
    private final Prefix prefixC = new Prefix("c/");
    private final Prefix prefixE = new Prefix("e/");
    private final Prefix prefixT = new Prefix("t/");

    @Test
    @DisplayName("Tokenize with empty string returns empty preamble")
    public void tokenize_emptyArgsString_returnsEmptyPreamble() throws ParseException {
        String argsString = "";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString);

        assertEquals("", argMultimap.getPreamble());
    }

    @Test
    @DisplayName("Tokenize with only preamble returns preamble with no prefix values")
    public void tokenize_onlyPreamble_returnsPreambleOnly() throws ParseException {
        String argsString = "some random string";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("some random string", argMultimap.getPreamble());
        assertFalse(argMultimap.getValue(prefixA).isPresent());
    }

    @Test
    @DisplayName("Tokenize with prefix at start has empty preamble")
    public void tokenize_prefixAtStart_emptyPreamble() throws ParseException {
        String argsString = "a/value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("", argMultimap.getPreamble());
        assertEquals("value", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with preamble and one prefix")
    public void tokenize_preambleAndOnePrefix_success() throws ParseException {
        String argsString = "preamble a/value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("preamble", argMultimap.getPreamble());
        assertEquals("value", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with multiple prefixes")
    public void tokenize_multiplePrefixes_success() throws ParseException {
        String argsString = "preamble a/valueA b/valueB c/valueC";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB, prefixC);

        assertEquals("preamble", argMultimap.getPreamble());
        assertEquals("valueA", argMultimap.getValue(prefixA).get());
        assertEquals("valueB", argMultimap.getValue(prefixB).get());
        assertEquals("valueC", argMultimap.getValue(prefixC).get());
    }

    @Test
    @DisplayName("Tokenize with repeated prefix accumulates all values")
    public void tokenize_repeatedPrefix_accumulatesValues() throws ParseException {
        String argsString = "a/value1 a/value2 a/value3";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals(3, argMultimap.getAllValues(prefixA).size());
        assertTrue(argMultimap.getAllValues(prefixA).contains("value1"));
        assertTrue(argMultimap.getAllValues(prefixA).contains("value2"));
        assertTrue(argMultimap.getAllValues(prefixA).contains("value3"));
    }

    @Test
    @DisplayName("Tokenize with empty value after prefix")
    public void tokenize_emptyValue_returnsEmptyString() throws ParseException {
        String argsString = "a/ b/value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB);

        assertEquals("", argMultimap.getValue(prefixA).get());
        assertEquals("value", argMultimap.getValue(prefixB).get());
    }

    @Test
    @DisplayName("Tokenize trims leading and trailing whitespace from values")
    public void tokenize_valueWithWhitespace_trimmed() throws ParseException {
        String argsString = "a/  value with spaces  ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("value with spaces", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with prefix substring in preamble does not match")
    public void tokenize_prefixSubstringInPreamble_notMatched() throws ParseException {
        // "google" contains "e/" but it's not a valid prefix
        String argsString = "google e/value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixE);

        assertEquals("google", argMultimap.getPreamble());
        assertEquals("value", argMultimap.getValue(prefixE).get());
    }

    @Test
    @DisplayName("Tokenize with prefix substring in value does not match")
    public void tokenize_prefixSubstringInValue_notMatched() throws ParseException {
        // "valuable" contains "a/" but it's not a valid prefix
        String argsString = "a/valuable b/test";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB);

        assertEquals("valuable", argMultimap.getValue(prefixA).get());
        assertEquals("test", argMultimap.getValue(prefixB).get());
    }

    @Test
    @DisplayName("Tokenize with prefix at start after whitespace")
    public void tokenize_prefixAfterWhitespace_matched() throws ParseException {
        String argsString = " a/value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        // Preamble is the space before "a/"
        assertEquals("", argMultimap.getPreamble());
        assertEquals("value", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with consecutive prefixes without values")
    public void tokenize_consecutivePrefixes_emptyValues() throws ParseException {
        String argsString = "a/b/c/";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB, prefixC);

        assertEquals("", argMultimap.getValue(prefixA).get());
        assertEquals("", argMultimap.getValue(prefixB).get());
        assertEquals("", argMultimap.getValue(prefixC).get());
    }

    @Test
    @DisplayName("Tokenize with unrecognized prefix treats it as value")
    public void tokenize_unrecognizedPrefix_treatedAsValue() throws ParseException {
        String argsString = "a/value c/ignored";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("value c/ignored", argMultimap.getValue(prefixA).get());
        assertFalse(argMultimap.getValue(prefixC).isPresent());
    }

    @Test
    @DisplayName("Tokenize with multiple spaces between prefixes")
    public void tokenize_multipleSpacesBetweenPrefixes_success() throws ParseException {
        String argsString = "a/value1    b/value2";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB);

        assertEquals("value1", argMultimap.getValue(prefixA).get());
        assertEquals("value2", argMultimap.getValue(prefixB).get());
    }

    @Test
    @DisplayName("Tokenize with newlines and tabs")
    public void tokenize_withNewlinesAndTabs_treatedAsWhitespace() throws ParseException {
        String argsString = "a/value1\n\tb/value2";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB);

        assertEquals("value1", argMultimap.getValue(prefixA).get());
        assertEquals("value2", argMultimap.getValue(prefixB).get());
    }

    @Test
    @DisplayName("Tokenize with value containing prefix-like substring")
    public void tokenize_valueContainsPrefixLike_notMatched() throws ParseException {
        // "email@domain.com" contains "a/" but shouldn't match
        String argsString = "a/email@domain.com b/test";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB);

        assertEquals("email@domain.com", argMultimap.getValue(prefixA).get());
        assertEquals("test", argMultimap.getValue(prefixB).get());
    }

    @Test
    @DisplayName("Tokenize example from javadoc")
    public void tokenize_javadocExample_success() throws ParseException {
        String argsString = "some preamble text t/ 11.00 t/12.00 k/ m/ July";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixT,
                new Prefix("k/"), new Prefix("m/"));

        assertEquals("some preamble text", argMultimap.getPreamble());
        assertEquals(2, argMultimap.getAllValues(prefixT).size());
        assertTrue(argMultimap.getAllValues(prefixT).contains("11.00"));
        assertTrue(argMultimap.getAllValues(prefixT).contains("12.00"));
        assertEquals("", argMultimap.getValue(new Prefix("k/")).get());
        assertEquals("July", argMultimap.getValue(new Prefix("m/")).get());
    }

    @Test
    @DisplayName("Tokenize with no prefixes specified returns all as preamble")
    public void tokenize_noPrefixesSpecified_allAsPreamble() throws ParseException {
        String argsString = "a/value b/value c/value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString);

        assertEquals("a/value b/value c/value", argMultimap.getPreamble());
    }

    @Test
    @DisplayName("Tokenize with prefix immediately followed by another prefix")
    public void tokenize_prefixFollowedByPrefix_firstHasEmptyValue() throws ParseException {
        String argsString = "a/b/value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB);

        assertEquals("", argMultimap.getValue(prefixA).get());
        assertEquals("value", argMultimap.getValue(prefixB).get());
    }

    @Test
    @DisplayName("Tokenize with only whitespace preamble")
    public void tokenize_onlyWhitespacePreamble_returnsWhitespace() throws ParseException {
        String argsString = "   a/value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("", argMultimap.getPreamble());
        assertEquals("value", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with trailing whitespace after last value")
    public void tokenize_trailingWhitespace_trimmed() throws ParseException {
        String argsString = "a/value   ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("value", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with prefix at end without value")
    public void tokenize_prefixAtEnd_emptyValue() throws ParseException {
        String argsString = "preamble a/";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("preamble", argMultimap.getPreamble());
        assertEquals("", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with case-sensitive prefixes")
    public void tokenize_caseSensitivePrefixes_differentPrefixes() throws ParseException {
        Prefix lowerA = new Prefix("a/");
        Prefix upperA = new Prefix("A/");
        String argsString = "a/lower A/upper";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, lowerA, upperA);

        assertEquals("lower", argMultimap.getValue(lowerA).get());
        assertEquals("upper", argMultimap.getValue(upperA).get());
    }

    @Test
    @DisplayName("Tokenize with special characters in values")
    public void tokenize_specialCharactersInValue_preserved() throws ParseException {
        String argsString = "a/hello@world.com#test!";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("hello@world.com#test!", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with Unicode characters")
    public void tokenize_unicodeCharacters_preserved() throws ParseException {
        String argsString = "a/你好世界 b/こんにちは";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA, prefixB);

        assertEquals("你好世界", argMultimap.getValue(prefixA).get());
        assertEquals("こんにちは", argMultimap.getValue(prefixB).get());
    }

    @Test
    @DisplayName("Tokenize prefix-like text attached to word is not matched")
    public void tokenize_prefixLikeAttachedToWord_notMatched() throws ParseException {
        // "tea/coffee" contains "a/" but shouldn't match because not preceded by whitespace
        String argsString = "tea/coffee a/real";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("tea/coffee", argMultimap.getPreamble());
        assertEquals("real", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with longer prefix strings")
    public void tokenize_longerPrefix_success() throws ParseException {
        Prefix longPrefix = new Prefix("class/");
        String argsString = "class/CS2103T";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, longPrefix);

        assertEquals("", argMultimap.getPreamble());
        assertEquals("CS2103T", argMultimap.getValue(longPrefix).get());
    }

    @Test
    @DisplayName("Tokenize with overlapping prefix strings")
    public void tokenize_overlappingPrefixes_matchesCorrectly() throws ParseException {
        Prefix shortPrefix = new Prefix("c/");
        Prefix longPrefix = new Prefix("class/");
        String argsString = "c/short class/long";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, shortPrefix, longPrefix);

        assertEquals("short", argMultimap.getValue(shortPrefix).get());
        assertEquals("long", argMultimap.getValue(longPrefix).get());
    }

    @Test
    @DisplayName("Real-world parser example: no prepended space needed")
    public void tokenize_realWorldExample_noPrependedSpace() throws ParseException {
        Prefix classPrefix = new Prefix("c/");
        Prefix sessionPrefix = new Prefix("s/");

        // This should work WITHOUT prepending a space
        String argsString = "c/CS2103T s/Week 1";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, classPrefix, sessionPrefix);

        assertEquals("", argMultimap.getPreamble());
        assertEquals("CS2103T", argMultimap.getValue(classPrefix).get());
        assertEquals("Week 1", argMultimap.getValue(sessionPrefix).get());
    }

    @Test
    @DisplayName("Real-world parser example: with unwanted preamble")
    public void tokenize_realWorldExample_withPreamble() throws ParseException {
        Prefix classPrefix = new Prefix("c/");
        Prefix sessionPrefix = new Prefix("s/");

        String argsString = "extra text c/CS2103T s/Week 1";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, classPrefix, sessionPrefix);

        assertEquals("extra text", argMultimap.getPreamble());
        assertEquals("CS2103T", argMultimap.getValue(classPrefix).get());
        assertEquals("Week 1", argMultimap.getValue(sessionPrefix).get());
    }

    // New test cases for field length validation

    @Test
    @DisplayName("Tokenize with field exactly at max length (50 chars) succeeds")
    public void tokenize_fieldAtMaxLength_success() throws ParseException {
        String maxLengthValue = "a".repeat(50); // Exactly 50 characters
        String argsString = "a/" + maxLengthValue;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals(maxLengthValue, argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with field exceeding max length throws ParseException")
    public void tokenize_fieldExceedsMaxLength_throwsParseException() {
        String tooLongValue = "a".repeat(51); // 51 characters, exceeds limit
        String argsString = "a/" + tooLongValue;

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA));
    }

    @Test
    @DisplayName("Tokenize with multiple fields where one exceeds max length throws ParseException")
    public void tokenize_oneFieldExceedsMaxLength_throwsParseException() {
        String validValue = "short";
        String tooLongValue = "b".repeat(51);
        String argsString = "a/" + validValue + " b/" + tooLongValue;

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA, prefixB));
    }

    @Test
    @DisplayName("Tokenize with repeated field where one exceeds max length throws ParseException")
    public void tokenize_repeatedFieldExceedsMaxLength_throwsParseException() {
        String validValue = "valid";
        String tooLongValue = "c".repeat(51);
        String argsString = "a/" + validValue + " a/" + tooLongValue;

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA));
    }

    @Test
    @DisplayName("Tokenize with field value of 49 characters succeeds")
    public void tokenize_fieldWith49Chars_success() throws ParseException {
        String value49Chars = "x".repeat(49);
        String argsString = "a/" + value49Chars;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals(value49Chars, argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with field value of 100 characters throws ParseException")
    public void tokenize_fieldWith100Chars_throwsParseException() {
        String value100Chars = "x".repeat(100);
        String argsString = "a/" + value100Chars;

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA));
    }

    @Test
    @DisplayName("Tokenize with empty field value succeeds")
    public void tokenize_emptyFieldValue_success() throws ParseException {
        String argsString = "a/";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals("", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with preamble does not validate preamble length")
    public void tokenize_preambleExceedsMaxLength_success() throws ParseException {
        String longPreamble = "preamble " + "x".repeat(100); // Long preamble
        String argsString = longPreamble + " a/valid";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals(longPreamble, argMultimap.getPreamble());
        assertEquals("valid", argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with whitespace-padded field that exceeds max length after trim throws ParseException")
    public void tokenize_whitespace_paddedFieldExceedsMax_throwsParseException() {
        String value = "a".repeat(51);
        String argsString = "a/  " + value + "  ";

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA));
    }

    @Test
    @DisplayName("Tokenize with multiple long fields where all exceed limit throws ParseException")
    public void tokenize_multipleLongFields_throwsParseException() {
        String tooLongA = "a".repeat(51);
        String tooLongB = "b".repeat(51);
        String argsString = "a/" + tooLongA + " b/" + tooLongB;

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA, prefixB));
    }

    @Test
    @DisplayName("Tokenize with field containing spaces within 50 char limit succeeds")
    public void tokenize_fieldWithSpacesWithinLimit_success() throws ParseException {
        String valueWithSpaces = "this is a valid field with spaces";
        String argsString = "a/" + valueWithSpaces;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, prefixA);

        assertEquals(valueWithSpaces, argMultimap.getValue(prefixA).get());
    }

    @Test
    @DisplayName("Tokenize with special characters exceeding max length throws ParseException")
    public void tokenize_specialCharsExceedingMaxLength_throwsParseException() {
        String specialChars = "!@#$%^&*()".repeat(6); // More than 50 chars
        String argsString = "a/" + specialChars;

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA));
    }

    @Test
    @DisplayName("Tokenize with Unicode characters exceeding max length throws ParseException")
    public void tokenize_unicodeCharsExceedingMaxLength_throwsParseException() {
        String unicodeChars = "你好".repeat(26); // More than 50 characters
        String argsString = "a/" + unicodeChars;

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA));
    }

    @Test
    @DisplayName("Tokenize with mixed valid and invalid fields throws ParseException")
    public void tokenize_mixedValidAndInvalidFields_throwsParseException() {
        String validA = "valid";
        String validB = "also valid";
        String tooLongC = "c".repeat(51);
        String argsString = "a/" + validA + " b/" + validB + " c/" + tooLongC;

        assertThrows(ParseException.class, () -> ArgumentTokenizer.tokenize(argsString, prefixA, prefixB, prefixC));
    }
}
