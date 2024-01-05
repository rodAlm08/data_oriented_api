package ie.atu.sw.services;

import java.util.List;

import ie.atu.sw.model.ModuleInfo;

/**
 * Interface for services handling module operations. Defines operations for
 * retrieving, updating, creating, and listing module information.
 */
public interface ModuleService {

	/**
	 * Retrieves the information of a specific module based on its code.
	 *
	 * @param moduleCode The code of the module to be retrieved.
	 * @return The ModuleInfo object associated with the given module code.
	 */
	ModuleInfo getModuleInfo(String moduleCode);

	/**
	 * Updates the information of an existing module.
	 *
	 * @param moduleCode The code of the module to be updated.
	 * @param newInfo    The new ModuleInfo object with updated information.
	 */
	void updateModuleInfo(String moduleCode, ModuleInfo newInfo);

	/**
	 * Creates a new module and adds it to the provided module list. The method
	 * implementation should define how the module is created and stored.
	 *
	 * @param moduleInfoList The list of ModuleInfo objects to which the new module
	 *                       will be added.
	 */
	void createModule(List<ModuleInfo> moduleInfoList);

	/**
	 * Lists all the modules. This method should display the details of all modules
	 * in the provided module list.
	 *
	 * @param moduleInfoList The list of ModuleInfo objects to be displayed.
	 */
	void listAllModules(List<ModuleInfo> moduleInfoList);

}
