package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Test to verify strict date validation (Feb 29 in non-leap years)
 */
public class DateValidationTest {

    @Test
    public void parseDateTime_feb29NonLeapYear_throwsException() {
        // 2025 is NOT a leap year
        String invalidDate = "2025-02-29 14:30";

        assertThrows(ParseException.class, () -> {
            ParserUtil.parseDateTime(invalidDate);
        }, "Should reject Feb 29 in non-leap year 2025");

        System.out.println("✓ Feb 29, 2025 correctly rejected (non-leap year)");
    }

    @Test
    public void parseDateTime_feb29LeapYear_success() {
        // 2024 IS a leap year
        String validDate = "2024-02-29 14:30";

        assertDoesNotThrow(() -> {
            ParserUtil.parseDateTime(validDate);
        }, "Should accept Feb 29 in leap year 2024");

        System.out.println("✓ Feb 29, 2024 correctly accepted (leap year)");
    }

    @Test
    public void parseDateTime_normalDate_success() {
        String normalDate = "2025-03-15 14:30";

        assertDoesNotThrow(() -> {
            ParserUtil.parseDateTime(normalDate);
        }, "Should accept normal valid dates");

        System.out.println("✓ Normal dates still work correctly");
        System.out.println("✓ ParserUtil.parseDateTime() validates leap years (lines 202-204)");
    }
}
