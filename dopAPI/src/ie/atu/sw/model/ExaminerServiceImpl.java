package ie.atu.sw.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import ie.atu.sw.enums.ExternalExaminerActions;
import ie.atu.sw.services.Examiner;
import ie.atu.sw.services.ExternalExaminerService;

/**
 * Implementation of the ExternalExaminerService interface. This class provides
 * functionality to manage actions performed by external examiners on
 * examination papers. It includes methods to record, list, and manage actions
 * such as comments, approvals, and rejections.
 */
public class ExaminerServiceImpl implements ExternalExaminerService, Serializable {
	private static final long serialVersionUID = 1L;
	private final Scanner scanner = new Scanner(System.in);
	private static final String externalExaminerActionsDataFile = "external_examiner_actions.dat";
	private final Map<String, List<ExternalExaminerAction>> actionsByPaperId = new HashMap<>();
	private final Map<String, Examiner> externalExaminers = new HashMap<>();

	/**
	 * Records an action taken by an external examiner on an examination paper. The
	 * method prompts the user to choose an external examiner and an examination
	 * paper, and then record the action to the selected paper.
	 */
	@Override
	public void recordAction() {
		ExternalExaminer selectedExternalExaminer = null;

		List<Examiner> allExaminers = ExaminerUtil.loadExaminers();

		if (allExaminers == null) {
			System.out.println("Error loading examiners.");
			return;
		}

		List<ExternalExaminer> externalExaminers = new ArrayList<>();

		for (Examiner examiner : allExaminers) {
			if (examiner instanceof ExternalExaminer) {
				externalExaminers.add((ExternalExaminer) examiner);
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
				System.out.println(selectedExternalExaminer);
				break;
			} else {
				System.out.println("Invalid selection. Please enter a valid number.");
			}
		}

		List<String> moduleCodesLinkedToExaminer = findModulesForExaminer(selectedExternalExaminer);

		List<ExaminationPaper> papersForSelectedExaminer = new ArrayList<>();

		for (String moduleCode : moduleCodesLinkedToExaminer) {
			String fileName = moduleCode + "_examination_papers.dat";
			File file = new File(fileName);

			if (!file.exists() || file.length() == 0) {
				System.out.println("File not found or is empty: " + fileName);
				continue;
			}

			List<ExaminationPaper> papers = FileUtil.loadFromFile(fileName);

			if (papers != null) {
				papersForSelectedExaminer.addAll(papers);
			}
		}

		if (papersForSelectedExaminer.isEmpty()) {
			System.out.println("No examination papers linked to the selected external examiner.");
			return;
		}

		System.out.println("Select an Examination Paper to record action:");
		for (int i = 0; i < papersForSelectedExaminer.size(); i++) {
			System.out.println((i + 1) + ". " + papersForSelectedExaminer.get(i).getPaperId());
		}

		int paperChoice = scanner.nextInt();
		scanner.nextLine();

		if (paperChoice < 1 || paperChoice > papersForSelectedExaminer.size()) {
			System.out.println("Invalid selection. Please enter a valid number.");
			return;
		}

		ExaminationPaper selectedPaper = papersForSelectedExaminer.get(paperChoice - 1);
		System.out.println("PAPER SELECTED DESCRIPTION: \n" + selectedPaper.toString());

		System.out.println("\tSelect Action for Selected Paper:");
		for (int i = 0; i < ExternalExaminerActions.values().length; i++) {
			System.out.println("\t" + (i + 1) + ". " + ExternalExaminerActions.values()[i]);
		}

		int actionChoice = scanner.nextInt();
		ExternalExaminerActions selectedAction = ExternalExaminerActions.values()[actionChoice - 1];
		scanner.nextLine();

		switch (selectedAction) {
		case ADD_COMMENT:
			handleCommentAction(selectedPaper, selectedExternalExaminer, selectedPaper.getPaperId());
			break;
		case APPROVE:
			handleApproveAction(selectedPaper, selectedExternalExaminer, selectedPaper.getPaperId());
			break;
		case REJECT:
			handleRejectAction(selectedPaper, selectedExternalExaminer, selectedPaper.getPaperId());
			break;
		default:
			System.out.println("Invalid action selected.");
			break;
		}

		saveExternalExaminerActionsToFile();
	}

	/**
     * Records a comment action for a specific examination paper.
     *
     * @param selectedPaper The examination paper the action is related to.
     * @param selectedExaminer The external examiner performing the action.
     * @param paperId The ID of the examination paper.
     */
	private void handleCommentAction(ExaminationPaper selectedPaper, ExternalExaminer selectedExaminer,
			String paperId) {
		System.out.println("Enter comment: ");
		String comment = scanner.nextLine();

		ExternalExaminerAction commentAction = new ExternalExaminerAction(selectedPaper.moduleCode(), selectedExaminer,
				comment, ExternalExaminerActions.ADD_COMMENT, paperId);

		recordActionForPaper(paperId, selectedPaper.getModuleCode(), commentAction);
		System.out.println("Comment action recorded successfully.");
	}

	/**
	 * Handles the approval action for a specific examination paper.
	 *
	 * @param selectedPaper The examination paper the approval action is related to.
	 * @param selectedExaminer The external examiner performing the approval.
	 * @param paperId The ID of the examination paper.
	 */
	private void handleApproveAction(ExaminationPaper selectedPaper, ExternalExaminer selectedExaminer,
			String paperId) {
		System.out.println("Enter approval comment: ");
		String approvalComment = scanner.nextLine();
		ExternalExaminerAction approveAction = new ExternalExaminerAction(selectedPaper.moduleCode(), selectedExaminer,
				approvalComment, ExternalExaminerActions.APPROVE, paperId);
		System.out.println(approveAction);
		recordActionForPaper(paperId, selectedPaper.getModuleCode(), approveAction);
		System.out.println("Approval action recorded successfully.");
	}

	/**
	 * Handles the rejection action for a specific examination paper.
	 *
	 * @param selectedPaper The examination paper the rejection action is related to.
	 * @param selectedExaminer The external examiner performing the rejection.
	 * @param paperId The ID of the examination paper.
	 */
	private void handleRejectAction(ExaminationPaper selectedPaper, ExternalExaminer selectedExaminer, String paperId) {
		System.out.println("Enter rejection comment: ");
		String rejectComment = scanner.nextLine();
		ExternalExaminerAction rejectAction = new ExternalExaminerAction(selectedPaper.moduleCode(), selectedExaminer,
				rejectComment, ExternalExaminerActions.REJECT, paperId);
		recordActionForPaper(paperId, selectedPaper.getModuleCode(), rejectAction);
		System.out.println("Rejection action recorded successfully.");
	}

	/**
     * Finds and returns a list of module codes linked to a specific external examiner.
     *
     * @param examiner The external examiner whose linked modules are to be found.
     * @return A list of module codes associated with the given external examiner.
     */
	public List<String> findModulesForExaminer(ExternalExaminer examiner) {
		List<String> moduleCodes = new ArrayList<>();
		List<ModuleInfo> allModules = ModuleInfoFileHandler.loadModuleInfo();

		for (ModuleInfo module : allModules) {
			if (module.getExternalExaminer().equals(examiner)) {
				moduleCodes.add(module.moduleCode());
			}
		}
		return moduleCodes;
	}

	public void loadExternalExaminersFromFile() {
		List<ExternalExaminer> loadedExaminers = FileUtil.loadFromFile(externalExaminerActionsDataFile);

		if (loadedExaminers != null) {
			for (ExternalExaminer examiner : loadedExaminers) {
				externalExaminers.put(examiner.getName(), examiner);
			}
			System.out.println("External examiners loaded successfully.");
		} else {
			System.out.println("No external examiners found in the file.");
		}
	}

	@Override
	public void listAllExternalExaminerActions() {
		List<ExternalExaminerAction> loadedActions = loadExternalExaminerActionsFromFile();
		// System.out.println(loadedActions);
		if (loadedActions != null && !loadedActions.isEmpty()) {
			System.out.println("List of External Examiner Actions:");
			for (ExternalExaminerAction action : loadedActions) {
				System.out.println(action);
			}
		} else {
			System.out.println("No external examiner actions found.");
		}
	}

	private List<ExternalExaminerAction> loadExternalExaminerActionsFromFile() {
		try {

			return FileUtil.loadFromFile(externalExaminerActionsDataFile);
		} catch (Exception e) {
			System.err.println("Error loading external examiner actions: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	private void saveExternalExaminerActionsToFile() {

		List<ExternalExaminerAction> allActions = new ArrayList<>();
		// System.out.println("*************************" + allActions);
		for (List<ExternalExaminerAction> actionsList : actionsByPaperId.values()) {
			allActions.addAll(actionsList);
		}
		FileUtil.saveToFile(externalExaminerActionsDataFile, allActions);
	}

	/**
	 * Records an action for a specific examination paper.
	 * This method adds the given action to the list of actions associated with a particular paper
	 * and updates the examination paper with this new action.
	 * If the paper is not found, an error message is displayed.
	 *
	 * @param paperId The ID of the examination paper for which the action is being recorded.
	 * @param moduleCode The code of the module to which the examination paper belongs.
	 * @param action The ExternalExaminerAction to be recorded for the paper.
	 */
	public void recordActionForPaper(String paperId, String moduleCode, ExternalExaminerAction action) {
		actionsByPaperId.putIfAbsent(paperId, new ArrayList<>());
		actionsByPaperId.get(paperId).add(action);
		// System.out.println("****************"+actionsByPaperId);

		String fileName = moduleCode + "_examination_papers.dat";
		// System.out.println(fileName);
		List<ExaminationPaper> papersList = FileUtil.loadFromFile(fileName);
		// System.out.println(papersList);

		if (papersList == null) {
			System.out.println("Error: No papers found for the module.");
			return;
		}

		for (ExaminationPaper paper : papersList) {
			if (paper.getPaperId().equals(paperId)) {
				List<ExternalExaminerAction> updatedActions = new ArrayList<>(paper.actions());
				updatedActions.add(action);
				ExaminationPaper updatedPaper = new ExaminationPaper(paper.paperId(), paper.moduleCode(),
						paper.allowLogTables(), paper.allowActuarialTables(), paper.allowStatisticalTables(),
						paper.allowGraphPaper(), paper.allowDictionaries(), paper.allowAttachedAnswerSheet(),
						paper.allowThermodynamicTables(), paper.allowNonProgrammableCalculators(),
						paper.allowRateTables(), paper.totalQuestions(), paper.requiredAnswers(), paper.questions(),
						updatedActions);
				papersList.set(papersList.indexOf(paper), updatedPaper);

				break;
			}
		}
		FileUtil.saveToFile(fileName, papersList);
		saveExternalExaminerActionsToFile();
	}


	/**
     * Lists all actions recorded for a specific examination paper.
     * Prompts the user to select a paper and then displays all actions associated with it.
     */
	@Override
	public void listActionsPerPaper() {
		List<ExternalExaminerAction> loadedActions = loadExternalExaminerActionsFromFile();
		// System.out.println(loadedActions);
		if (loadedActions == null || loadedActions.isEmpty()) {
			System.out.println("No external examiner actions found.");
			return;
		}

		Map<String, List<ExternalExaminerAction>> paperActionsMap = new HashMap<>();
		for (ExternalExaminerAction action : loadedActions) {
			String paperId = action.getPaperId();
			paperActionsMap.putIfAbsent(paperId, new ArrayList<>());
			paperActionsMap.get(paperId).add(action);
		}

		System.out.println("\t\nList of Available Papers:");
		Set<String> paperIds = paperActionsMap.keySet();
		List<String> paperIdList = new ArrayList<>(paperIds);
		for (int i = 0; i < paperIdList.size(); i++) {
			System.out.println("\t" + (i + 1) + ". " + paperIdList.get(i));
		}

		System.out.println("Select a paper by entering the number: ");
		int choice = scanner.nextInt();
		scanner.nextLine();
		if (choice >= 1 && choice <= paperIdList.size()) {
			String selectedPaperId = paperIdList.get(choice - 1);

			if (paperActionsMap.containsKey(selectedPaperId)) {
				List<ExternalExaminerAction> actionsForPaper = paperActionsMap.get(selectedPaperId);

				if (actionsForPaper != null && !actionsForPaper.isEmpty()) {
					System.out.println(
							"\nList of External Examiner Actions for Paper with ID: " + selectedPaperId + "\n");
					for (ExternalExaminerAction action : actionsForPaper) {
						System.out.println(action);
					}
				} else {
					System.out.println("No external examiner actions found for Paper with ID: " + selectedPaperId);
				}
			} else {
				System.out
						.println("Paper with ID: " + selectedPaperId + " does not have any external examiner actions.");
			}
		} else {
			System.out.println("Invalid selection. Please enter a valid number.");
		}
	}

	public List<ExternalExaminerAction> getActionsForPaper(String paperId) {
		return actionsByPaperId.getOrDefault(paperId, new ArrayList<>());
	}

	@Override
	public void approveAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rejectAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commentAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ExternalExaminerAction> getAllActions() {
		// TODO Auto-generated method stub
		return null;
	}
}