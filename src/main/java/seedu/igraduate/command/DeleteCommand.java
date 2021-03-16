package seedu.igraduate.command;

import seedu.igraduate.Storage;
import seedu.igraduate.ModuleList;
import seedu.igraduate.Ui;

import seedu.igraduate.exception.ModuleNotFoundException;
import seedu.igraduate.exception.SaveModuleFailException;

import seedu.igraduate.module.Module;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Handles delete command.
 */
public class DeleteCommand extends Command {
    protected String moduleCode;

    private static final Logger LOGGER = Logger.getLogger(DeleteCommand.class.getName());

    /**
     * Child class of the command class that contains the module code. 
     * 
     * @param moduleCode module code. 
     */
    public DeleteCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Deletes a module from moduleList.
     * 
     * @param moduleList Module list consisting of all modules.
     * @param ui User interface for printing result.
     * @param storage Storage for storing module list data.
     * @throws ModuleNotFoundException If the module specified does not exists.
     * @throws SaveModuleFailException If module data fails to save to file.
     */
    @Override
    public void execute(ModuleList moduleList, Ui ui, Storage storage)
            throws ModuleNotFoundException, SaveModuleFailException {
        LOGGER.log(Level.INFO, "Executing delete command...");
        try {
            Module module = moduleList.getByCode(moduleCode);
            String moduleType = moduleList.getModuleType(module);
            moduleList.delete(module);
            storage.saveModulesToFile(moduleList);
            ui.printDeletedModuleSuccess(moduleCode, moduleType);
            LOGGER.log(Level.INFO, String.format("Successfully deleted %s module.", moduleCode));
        } catch (ModuleNotFoundException e) {
            LOGGER.log(Level.WARNING, "Failed to delete non-existence module.", e);
            throw new ModuleNotFoundException();
        } finally {
            LOGGER.log(Level.INFO, "End of delete command execution.");
        }
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
