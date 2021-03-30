package seedu.igraduate.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.igraduate.model.list.ModuleList;
import seedu.igraduate.logic.Parser;
import seedu.igraduate.storage.Storage;
import seedu.igraduate.ui.Ui;

import seedu.igraduate.exception.InvalidModuleGradeException;
import seedu.igraduate.exception.UnableToDeletePrereqModuleException;
import seedu.igraduate.exception.ModularCreditExceedsLimitException;
import seedu.igraduate.exception.PrerequisiteNotFoundException;
import seedu.igraduate.exception.ModuleNotFoundException;
import seedu.igraduate.exception.SaveModuleFailException;
import seedu.igraduate.exception.InputNotNumberException;
import seedu.igraduate.exception.ExistingModuleException;
import seedu.igraduate.exception.ModuleNotCompleteException;
import seedu.igraduate.exception.IncorrectParameterCountException;
import seedu.igraduate.exception.InvalidCommandException;
import seedu.igraduate.exception.InvalidModuleTypeException;
import seedu.igraduate.exception.InvalidListTypeException;
import seedu.igraduate.exception.PrerequisiteNotMetException;
import seedu.igraduate.exception.AddSelfToPrereqException;
import seedu.igraduate.exception.InvalidModularCreditException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class UpdateCommandUnitTest {
    private static final File FILEPATH = Paths.get("./commandteststorage/deleteCommandData.json").toFile();

    private Storage storage = Storage.getStorage(FILEPATH);
    private Ui ui = new Ui();
    private ModuleList moduleList = new ModuleList();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void populateList()
            throws InvalidCommandException, InvalidModuleTypeException, InputNotNumberException,
            IncorrectParameterCountException, ExistingModuleException, ModularCreditExceedsLimitException,
            ModuleNotCompleteException, SaveModuleFailException, InvalidModuleGradeException,
            UnableToDeletePrereqModuleException, PrerequisiteNotFoundException,
            ModuleNotFoundException, InvalidListTypeException, PrerequisiteNotMetException, AddSelfToPrereqException,
            InvalidModularCreditException {
        String module = "add Programming Methodology -mc 4 -t core -c cs1010";
        Command addModule = Parser.parseCommand(module);
        addModule.execute(moduleList, ui, storage);
        String setToDone = "done cs1010 -g A+";
        Command doneModule = Parser.parseCommand(setToDone);
        doneModule.execute(moduleList, ui, storage);
    }

    @Test
    void executeUpdateCommand_validParameters_success()
            throws InvalidModularCreditException, InputNotNumberException,
            InvalidModuleGradeException, PrerequisiteNotFoundException, ModuleNotCompleteException,
            ExistingModuleException, InvalidModuleTypeException, PrerequisiteNotMetException,
            ModuleNotFoundException, InvalidListTypeException, AddSelfToPrereqException,
            SaveModuleFailException, ModularCreditExceedsLimitException, UnableToDeletePrereqModuleException {
        Command updateCommand = new UpdateCommand("CS1010",
                new ArrayList<String>(Arrays.asList("-g", "A-", "-mc", "2")));
        System.setOut(new PrintStream(outContent));
        updateCommand.execute(moduleList, ui, storage);
        assertEquals("Nice! I've updated this module:" + System.lineSeparator()
                + "  " + "[C][✓] CS1010   Programming Methodology                                  A-   2 MC"
                + System.lineSeparator(), outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    void executeUpdateCommand_nonexistentModule_exceptionThrown() {
        UpdateCommand updateCommand = new UpdateCommand("CS2040",
                new ArrayList<String>(Arrays.asList("-g", "A-", "-mc", "2")));
        Exception exception = assertThrows(ModuleNotFoundException.class,
            () -> updateCommand.execute(moduleList, ui, storage));
        assertEquals(ModuleNotFoundException.MODULE_NOT_FOUND_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void executeUpdateCommand_invalidGrade_exceptionThrown() {
        UpdateCommand updateCommand = new UpdateCommand("CS1010",
                new ArrayList<String>(Arrays.asList("-g", "V-", "-mc", "2")));
        Exception exception = assertThrows(InvalidModuleGradeException.class,
            () -> updateCommand.execute(moduleList, ui, storage));
        assertEquals(InvalidModuleGradeException.INVALID_MODULE_GRADE_ERROR_MESSAGE, exception.getMessage());
    }

    @AfterEach
    void tearDownList() throws InvalidCommandException, InvalidModuleTypeException, InputNotNumberException,
            IncorrectParameterCountException, ExistingModuleException, ModularCreditExceedsLimitException,
            ModuleNotCompleteException, SaveModuleFailException, InvalidModuleGradeException,
            UnableToDeletePrereqModuleException, PrerequisiteNotFoundException,
            ModuleNotFoundException, InvalidListTypeException, PrerequisiteNotMetException, AddSelfToPrereqException,
            InvalidModularCreditException {
        String module = "Delete cs1010";
        Command deleteModule = Parser.parseCommand(module);
        deleteModule.execute(moduleList, ui, storage);
    }
}
