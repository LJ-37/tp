package seedu.igraduate.logic.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.igraduate.model.ModuleList;
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
import seedu.igraduate.model.module.Module;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AddCommandTest {

    private static final File FILEPATH = Paths.get("./commandteststorage/deleteCommandData.json").toFile();

    private Storage storage = Storage.getStorage(FILEPATH);
    private Ui ui = new Ui();
    private ModuleList moduleList = new ModuleList();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Test
    void executeAddCommand_ExistingModule_exceptionThrown()
        throws InvalidCommandException, InvalidModuleTypeException,
        InputNotNumberException, IncorrectParameterCountException,
        SaveModuleFailException, ExistingModuleException,
        ModuleNotFoundException, PrerequisiteNotFoundException, InvalidListTypeException {
        ArrayList<String> preRequisites = new ArrayList<>();
        ArrayList<String> untakenPreRequisites = new ArrayList<>();
        AddCommand addCommand = new AddCommand("cs1010", "Programming", "core", 4.0,
                preRequisites, untakenPreRequisites);
        addCommand.execute(moduleList, ui, storage);
        String line = "add Programming -mc 4 -t core -c cs1010";
        Command testAddCommand = Parser.parseCommand(line);
        Exception exception = assertThrows(ExistingModuleException.class,
            () -> testAddCommand.execute(moduleList, ui, storage));
        assertEquals(ExistingModuleException.EXISTING_MODULE_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void executeAddCommand_validParameters_success()
        throws InvalidCommandException, InvalidModuleTypeException,
        InputNotNumberException, IncorrectParameterCountException, ModuleNotFoundException,
        SaveModuleFailException, ExistingModuleException, PrerequisiteNotFoundException,
        ModuleNotCompleteException, ModularCreditExceedsLimitException, UnableToDeletePrereqModuleException,
        InvalidModuleGradeException, InvalidListTypeException, PrerequisiteNotMetException {
        String line = "add Computer Org -mc 4 -t core -c cs2100";
        Command addCommand = Parser.parseCommand(line);
        System.setOut(new PrintStream(outContent));
        addCommand.execute(moduleList, ui, storage);
        Module module = moduleList.getByCode("cs2100");
        assertEquals(String.format(Ui.MODULE_ADDED_MESSAGE, "CS2100", "Computer Org", "4.0")
                + System.lineSeparator()
                + module + System.lineSeparator(), outContent.toString());
        System.setOut(originalOut);
    }
}