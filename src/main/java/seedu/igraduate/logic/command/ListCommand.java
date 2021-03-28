package seedu.igraduate.logic.command;

import seedu.igraduate.storage.Storage;
import seedu.igraduate.model.list.ModuleList;
import seedu.igraduate.ui.Ui;
import seedu.igraduate.exception.InvalidListTypeException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles list command.
 */
public class ListCommand extends Command {
    private static final Logger LOGGER = Logger.getLogger(ListCommand.class.getName());
    private String scope;

    public ListCommand(String scope) {
        this.scope = scope;
    }

    /**
     * Prints list of all modules.
     *
     * @param moduleList Module list consisting of all modules.
     * @param ui User interface for printing result.
     * @param storage Storage for storing module list data.
     */
    @Override
    public void execute(ModuleList moduleList, Ui ui, Storage storage) throws InvalidListTypeException {
        LOGGER.log(Level.INFO, "Executing list command...");
        String checkCode;
        switch (scope) {
        case "all" :
            if (moduleList.isEmpty()) {
                assert moduleList.isEmpty() : "List should be empty";
                ui.printListEmptyMessage();
                return;
            }
            ui.printEntireList(moduleList.getModules());
            LOGGER.log(Level.INFO, "Printed Entire List.");
            break;
        case "complete" :
            if (moduleList.isCompletedModulesEmpty()) {
                ui.printCompleteListEmptyMessage();
                return;
            }
            ui.printCompletedList(moduleList.getModules());
            LOGGER.log(Level.INFO, "Printed Completed Modules.");
            break;
        case "incomplete":
            if (moduleList.isIncompletedModulesEmpty()) {
                ui.printIncompleteListEmptyMessage();
                return;
            }
            ui.printIncompletedList(moduleList.getModules());
            LOGGER.log(Level.INFO, "Printed Incomplete Modules.");
            break;
        default:
            LOGGER.log(Level.INFO, "Failed to print a valid list");
        }
        LOGGER.log(Level.INFO, "End of list command execution.");
    }


    /**
     * {@inheritDoc}
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
