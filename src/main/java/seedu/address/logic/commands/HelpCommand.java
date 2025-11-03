package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.\n\n"
            + "Available Commands:\n\n"
            + "1. " + AddCommand.COMMAND_WORD + " - Add a person\n"
            + "   " + AddCommand.MESSAGE_USAGE + "\n\n"
            + "2. " + AddClassCommand.COMMAND_WORD + " - Add a tuition class\n"
            + "   " + AddClassCommand.MESSAGE_USAGE + "\n\n"
            + "3. " + EditCommand.COMMAND_WORD + " - Edit a person\n"
            + "   " + EditCommand.MESSAGE_USAGE + "\n\n"
            + "4. " + EditClassCommand.COMMAND_WORD + " - Edit a class\n"
            + "   " + EditClassCommand.MESSAGE_USAGE + "\n\n"
            + "5. " + DeleteCommand.COMMAND_WORD + " - Delete a person\n"
            + "   " + DeleteCommand.MESSAGE_USAGE + "\n\n"
            + "6. " + DeleteClassCommand.COMMAND_WORD + " - Delete a class\n"
            + "   " + DeleteClassCommand.MESSAGE_USAGE + "\n\n"
            + "7. " + ListCommand.COMMAND_WORD + " - List all persons\n"
            + "   Usage: " + ListCommand.COMMAND_WORD + "\n\n"
            + "8. " + ListChildrenCommand.COMMAND_WORD + " - List children of a parent\n"
            + "   " + ListChildrenCommand.MESSAGE_USAGE + "\n\n"
            + "9. " + FindCommand.COMMAND_WORD + " - Find persons by keyword\n"
            + "   " + FindCommand.MESSAGE_USAGE + "\n\n"
            + "10. " + FilterCommand.COMMAND_WORD + " - Filter persons by criteria\n"
            + "   " + FilterCommand.MESSAGE_USAGE + "\n\n"
            + "11. " + JoinClassCommand.COMMAND_WORD + " - Add a person to a class\n"
            + "   " + JoinClassCommand.MESSAGE_USAGE + "\n\n"
            + "12. " + AttendCommand.COMMAND_WORD + " - Mark attendance\n"
            + "   " + AttendCommand.MESSAGE_USAGE + "\n\n"
            + "13. " + LinkCommand.COMMAND_WORD + " - Link a parent to a child\n"
            + "   " + LinkCommand.MESSAGE_USAGE + "\n\n"
            + "14. " + ClearCommand.COMMAND_WORD + " - Clear all entries\n"
            + "   Usage: " + ClearCommand.COMMAND_WORD + "\n\n"
            + "15. " + ExitCommand.COMMAND_WORD + " - Exit the program\n"
            + "   Usage: " + ExitCommand.COMMAND_WORD + "\n\n"
            + "16. " + AddSessionCommand.COMMAND_WORD + " - Add a session to a class\n"
            + "   " + AddSessionCommand.MESSAGE_USAGE + "\n\n"
            + "17. " + DeleteSessionCommand.COMMAND_WORD + " - Delete a session from a class\n"
            + "   " + DeleteSessionCommand.MESSAGE_USAGE + "\n\n"
            + "18. " + ListClassCommand.COMMAND_WORD + " - List all classes\n"
            + "   Usage: " + ListClassCommand.COMMAND_WORD + "\n\n"
            + "19. " + ListParentsCommand.COMMAND_WORD + " - List parents of a child\n"
            + "   " + ListParentsCommand.MESSAGE_USAGE + "\n\n"
            + "20. " + ListSessionCommand.COMMAND_WORD + " - List sessions of a class\n"
            + "   " + ListSessionCommand.MESSAGE_USAGE + "\n\n"
            + "21. " + ListStudentsCommand.COMMAND_WORD + " - List students in a class\n"
            + "   " + ListStudentsCommand.MESSAGE_USAGE + "\n\n"
            + "22. " + UnjoinClassCommand.COMMAND_WORD + " - Remove a person from a class\n"
            + "   " + UnjoinClassCommand.MESSAGE_USAGE + "\n\n"
            + "23. " + ViewSessionCommand.COMMAND_WORD + " - View session details\n"
            + "   " + ViewSessionCommand.MESSAGE_USAGE + "\n\n"
            + "24. " + COMMAND_WORD + " - Show this help message\n"
            + "   " + MESSAGE_USAGE;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
