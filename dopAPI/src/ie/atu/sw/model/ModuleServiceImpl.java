package ie.atu.sw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ie.atu.sw.services.Examiner;
import ie.atu.sw.services.ModuleService;

/**
 * Implementation of the ModuleService interface. This class provides
 * functionality to manage modules, including creating, updating, and listing
 * module information. It interacts with users through the command line to
 * gather input and display information.
 */
public class ModuleServiceImpl implements ModuleService, Serializable {
	private static final long serialVersionUID = 1L;

	private final Scanner scanner = new Scanner(System.in);
	private final Map<String, ModuleInfo> modules = new HashMap<>();
	private static final String moduleDataFile = "module_data.dat";

	/**
	 * Creates a new module and adds it to the module list and file storage. It
	 * prompts the user for module details and examiner selections, then saves the
	 * module information.
	 *
	 * @param moduleInfoList The list of modules to which the new module will be
	 *                       added.
	 */
	@Override
	public void createModule(List<ModuleInfo> moduleInfoList) {
		InternalExaminer selectedInternalExaminer = null;
		ExternalExaminer selectedExternalExaminer = null;
		System.out.println("Enter Module Code: ");
		String moduleCode = scanner.nextLine();

		if (modules.containsKey(moduleCode)) {
			System.out.println("Module already exists.");
			return;
		}

		System.out.println("Enter Module Title: ");
		String moduleTitle = scanner.nextLine();
		System.out.println("Enter Number of Registrations: ");
		int reg = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter Program Code: ");
		String programCode = scanner.nextLine();
		System.out.println("Enter Program Title: ");
		String programTitle = scanner.nextLine();
		System.out.println("Enter Year: ");
		int year = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Enter School: ");
		String school = scanner.nextLine();
		System.out.println("Enter Department: ");
		String department = scanner.nextLine();

		List<Examiner> allExaminers = ExaminerUtil.loadExaminers();
		if (allExaminers == null) {
			System.out.println("Error loading examiners.");
			return;
		}

		List<InternalExaminer> internalExaminers = new ArrayList<>();
		List<ExternalExaminer> externalExaminers = new ArrayList<>();

		for (Examiner examiner : allExaminers) {
			if (examiner instanceof InternalExaminer) {
				internalExaminers.add((InternalExaminer) examiner);
			} else if (examiner instanceof ExternalExaminer) {
				externalExaminers.add((ExternalExaminer) examiner);
			}
		}

		if (internalExaminers.isEmpty()) {
			System.out.println("No internal examiners available.");
			return;
		}

		while (true) {
			System.out.println("List of Existing Internal Examiners:");
			for (int i = 0; i < internalExaminers.size(); i++) {
				System.out.println((i + 1) + ". " + internalExaminers.get(i).getName());
			}

			System.out.println("Select an Internal Examiner for the Module (enter the number): ");
			int internalExaminerChoice = scanner.nextInt();

			if (internalExaminerChoice >= 1 && internalExaminerChoice <= internalExaminers.size()) {
				selectedInternalExaminer = internalExaminers.get(internalExaminerChoice - 1);
				break;
			} else {
				System.out.println("Invalid selection. Please enter a valid number.");
			}
		}

		if (externalExaminers.isEmpty()) {
			System.out.println("No external examiners available.");
			return;
		}

		while (true) {
			System.out.println("List of Existing External Examiners:");
			for (int i = 0; i < externalExaminers.size(); i++) {
				System.out.println((i + 1) + ". " + externalExaminers.get(i).getName());
			}

			System.out.println("Select an External Examiner for the Module (enter the number): ");
			int externalExaminerChoice = scanner.nextInt();

			if (externalExaminerChoice >= 1 && externalExaminerChoice <= externalExaminers.size()) {
				selectedExternalExaminer = externalExaminers.get(externalExaminerChoice - 1);
				break;
			} else {
				System.out.println("Invalid selection. Please enter a valid number.");
			}
		}

		ModuleInfo moduleInfo = new ModuleInfo(moduleCode, moduleTitle, reg, programCode, programTitle, year, school,
				department, selectedInternalExaminer, selectedExternalExaminer);

		modules.put(moduleCode, moduleInfo);
		moduleInfoList.add(moduleInfo);
		FileUtil.saveToFile(moduleDataFile, moduleInfoList);
		System.out.println("Module created successfully.");

	}

	/**
	 * Retrieves the information of a specific module based on its code.
	 *
	 * @param moduleCode The code of the module to be retrieved.
	 * @return The ModuleInfo object associated with the given module code.
	 */
	@Override
	public ModuleInfo getModuleInfo(String moduleCode) {
		return modules.get(moduleCode);
	}

	/**
	 * Updates the information of an existing module.
	 *
	 * @param moduleCode The code of the module to be updated.
	 * @param newInfo    The new information to be updated in the module.
	 */
	@Override
	public void updateModuleInfo(String moduleCode, ModuleInfo newInfo) {
		if (modules.containsKey(moduleCode)) {
			modules.put(moduleCode, newInfo);
			System.out.println("Module information updated successfully.");
		} else {
			System.out.println("Module not found.");
		}
	}

	/**
	 * Lists all the modules currently stored. This method retrieves and displays
	 * the list of modules from file storage.
	 *
	 * @param moduleInfoList The list of ModuleInfo objects to be displayed.
	 */
	@Override
	public void listAllModules(List<ModuleInfo> moduleInfoList) {
		moduleInfoList = FileUtil.loadFromFile(moduleDataFile);
		if (moduleInfoList == null) {
			moduleInfoList = new ArrayList<>();
		}

		System.out.println("\nList of Modules:\n");
		for (ModuleInfo module : moduleInfoList) {
			System.out.println(module);
		}
	}

}
