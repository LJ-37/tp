package seedu.igraduate;

import seedu.igraduate.command.Command;
import seedu.igraduate.command.AddCommand;
import seedu.igraduate.command.DeleteCommand;
import seedu.igraduate.command.ExitCommand;
import seedu.igraduate.command.ListCommand;
import seedu.igraduate.command.ProgressCommand;

import seedu.igraduate.exception.IncorrectParameterCountException;
import seedu.igraduate.exception.InvalidCommandException;

public class Parser {
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_PROGRESS = "progress";
    private static final String COMMAND_EXIT = "exit";

    private static final int COMMAND_ADD_LENGTH = 6;
    private static final int COMMAND_DELETE_LENGTH = 2;
    private static final int COMMAND_LIST_LENGTH = 2;
    private static final int COMMAND_PROGRESS_LENGTH = 1;
    private static final int COMMAND_EXIT_LENGTH = 1;

    /**
     * Parses user input and identifies the command to be executed.
     *
     * @param line user input.
     */
    public static Command parseCommand(String line)
            throws InvalidCommandException, IncorrectParameterCountException {
        if (line.trim().length() == 0) { 
            throw new InvalidCommandException();
        }
        String[] commands = line.split("\\s+");
        String command = commands[0].toLowerCase();

        switch (command) { 
        case COMMAND_ADD:
            return executeAddCommand(commands);
        case COMMAND_DELETE:
            return executeDeleteCommand(commands);
        case COMMAND_LIST:
            return executeListCommand(commands);
        case COMMAND_PROGRESS:
            return executeProgressCommand(commands);
        case COMMAND_EXIT:
            // Fallthrough
            return executeExitCommand(commands);
        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Extracts relevant parameters and creates new instance of relevant Command class to execute.
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return new instance of AddCommand class.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static Command executeAddCommand(String[] commands)
            throws InvalidCommandException { 
        if (commands.length != COMMAND_ADD_LENGTH) { 
            throw new InvalidCommandException();
        }
        String moduleCode = extractModuleCode(commands);
        String moduleType = extractModuleType(commands);
        double moduleCredits = extractModuleCredits(commands);

        return new AddCommand(moduleCode, moduleType, moduleCredits);
    }

    /**
     * Extracts relevant parameters and creates new instance of relevant Command class to execute.
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return new instance of DeleteCommand class.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static Command executeDeleteCommand(String[] commands)
            throws InvalidCommandException { 
        if (commands.length != COMMAND_DELETE_LENGTH) { 
            throw new InvalidCommandException();
        }
        String code = extractModuleCode(commands);

        return new DeleteCommand(code);
    }

    /**
     * Extracts relevant parameters and creates new instance of relevant Command class to execute.
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return new instance of ListCommand class.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static Command executeListCommand(String[] commands)
            throws InvalidCommandException { 
        if (commands.length != COMMAND_LIST_LENGTH) { 
            throw new InvalidCommandException();
        }
        String scope = extractListScope(commands);

        return new ListCommand(scope);
    }

    /**
     * Extracts relevant parameters and creates new instance of relevant Command class to execute.
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return new instance of ProgressCommand class.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static Command executeProgressCommand(String[] commands)
            throws IncorrectParameterCountException {
        if (commands.length != COMMAND_PROGRESS_LENGTH) { 
            throw new IncorrectParameterCountException();
        }
        return new ProgressCommand();
    }

    /**
     * Extracts relevant parameters and creates new instance of relevant Command class to execute.
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return new instance of ExitCommand class.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static Command executeExitCommand(String[] commands)
            throws InvalidCommandException { 
        if (commands.length != COMMAND_EXIT_LENGTH) { 
            throw new InvalidCommandException();
        }
        return new ExitCommand();
    }

    /**
     * Extracts module code from user input. Method is called if user runs "Add" or "Delete" command.
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return module code.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static String extractModuleCode(String[] commands)
            throws InvalidCommandException { 
        return commands[1].toUpperCase().trim();
    }

    /**
     * Extracts module type from user input. Method is called if user runs "Add" command.
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return module type.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static String extractModuleType(String[] commands)
            throws InvalidCommandException { 
        for (int i = 0; i < commands.length; i++) { 
            if (commands[i].equals("-t")) { 
                String type = commands[i + 1].toLowerCase().trim();
                switch (type) {
                case "core":
                case "ue":
                case "math":
                case "ge":
                    return type;
                default: 
                    throw new InvalidCommandException();
                }
            }
        }
        throw new InvalidCommandException();
    }

    /**
     * Extracts module credits from user input.
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return number of modular credits.
     * @throws NumberFormatException if number is not given as modular credits.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static double extractModuleCredits(String[] commands)
            throws NumberFormatException, InvalidCommandException {
        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals("-c")) {
                return Double.parseDouble(commands[i + 1]);
            }
        }
        throw new InvalidCommandException();
    }

    /**
     * Determines the option user selects if "List" command is run.
     * Options are:
     * 1. List all modules
     * 2. List modules taken
     * 3. List modules not taken
     *
     * @param commands user input split into substrings with " " as delimiter.
     * @return the option user selects.
     * @throws InvalidCommandException if command format is not recognised.
     */
    public static String extractListScope(String[] commands)
        throws InvalidCommandException {
        String scope = commands[1].trim().toLowerCase();
        switch (scope) {
        case "all":
        case "left":
        case "taken":
            return scope;
        default:
            throw new InvalidCommandException();
        }
    } 
}