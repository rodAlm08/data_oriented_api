package ie.atu.sw.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import ie.atu.sw.services.ExaminationPaperService;

/**
 * Implements the ExaminationPaperService, providing functionality for managing examination papers.
 * This class includes methods for adding, retrieving, updating, and listing examination papers.
 * It also interacts with the user through the command line for inputs and displays information accordingly.
 */

public class ExaminationPaperServiceImpl implements ExaminationPaperService, Serializable {
	private static final long serialVersionUID = 1L;

	private final Scanner scanner = new Scanner(System.in);
	private final Map<String, ExaminationPaper> papers = new HashMap<>();

	/**
     * Adds a new examination paper based on user inputs.
     * It prompts the user for various details about the examination paper, including module selection,
     * allowances for different items, and questions.
     */
	@Override
	public void addExaminationPaper() {
		int reqAnswers = Rules.DEFAULT_REQUIRED_ANSWERS;
		String paperId = UUID.randomUUID().toString();
		List<ModuleInfo> moduleInfoList = ModuleInfoFileHandler.loadModuleInfo();

		if (moduleInfoList.isEmpty()) {
			System.out.println("No modules available.");
			return;
		}

		ModuleInfo selectedModule = selectModule(moduleInfoList);
		if (selectedModule == null) {
			System.out.println("Invalid module selection.");
			return;
		}

		String allowLogTables = getUserChoice("Allow Log Tables");
		String allowActuarialTables = getUserChoice("Allow Actuarial Tables");
		String allowStatisticalTables = getUserChoice("Allow Statistical Tables");
		String allowGraphPaper = getUserChoice("Allow Graph Paper");
		String allowDictionaries = getUserChoice("Allow Dictionaries");
		String allowAttachedAnswerSheet = getUserChoice("Allow Attached Answer Sheet");
		String allowThermodynamicTables = getUserChoice("Allow Thermodynamic Tables");
		String allowCalculators = getUserChoice("Allow Non-Programmable Calculators");
		String allowRateTables = getUserChoice("Allow Rate Tables");

		List<Question> questions = new ArrayList<>();
		int totalMarksAllocated = 0;

		while (totalMarksAllocated < Rules.DEFAULT_TOTAL_MARKS && questions.size() < Rules.DEFAULT_MAX_QUESTIONS) {
			int remainingMarks = Rules.DEFAULT_TOTAL_MARKS - totalMarksAllocated;
			System.out.println(
					"Total Marks Allocated so far: " + totalMarksAllocated + ". Remaining Marks: " + remainingMarks);

			int marksForQuestion = getMarksForQuestion(remainingMarks);
			Question newQuestion = addNewQuestion(marksForQuestion);

			if (newQuestion != null) {
				questions.add(newQuestion);
				totalMarksAllocated += marksForQuestion;
			}

			if (questions.size() >= Rules.DEFAULT_MAX_QUESTIONS || totalMarksAllocated >= Rules.DEFAULT_TOTAL_MARKS) {
				System.out.println("\n\tMaximum number of questions or total marks reached.");
				break;
			}
		}

		if (totalMarksAllocated != Rules.DEFAULT_TOTAL_MARKS) {
			System.out.println("Error: Total marks for all questions must add up to " + Rules.DEFAULT_TOTAL_MARKS);
			return;
		}

		ExaminationPaper paper = new ExaminationPaper(paperId, selectedModule.moduleCode(), allowLogTables,
				allowActuarialTables, allowStatisticalTables, allowGraphPaper, allowDictionaries,
				allowAttachedAnswerSheet, allowThermodynamicTables, allowCalculators, allowRateTables, questions.size(),
				reqAnswers, questions, new ArrayList<>());

		papers.put(paperId, paper);
		saveExaminationPaperToFile(selectedModule.moduleCode(), paper);
		System.out.println("\t\nExamination paper added successfully.");
	}

	private int getMarksForQuestion(int remainingMarks) {
		int marksForQuestion;
		do {
			System.out.println("Enter total marks for this question (Remaining marks: " + remainingMarks + "): ");
			marksForQuestion = scanner.nextInt();
			scanner.nextLine(); // Consume the newline

			if (marksForQuestion <= 0 || marksForQuestion > remainingMarks) {
				System.out.println("Invalid marks. Please enter a value between 1 and " + remainingMarks);
			}
		} while (marksForQuestion <= 0 || marksForQuestion > remainingMarks);

		return marksForQuestion;
	}

	 /**
     * Prompts the user to select a module from the provided list and returns the selected ModuleInfo.
     * If the list is empty or an invalid selection is made, returns null.
     *
     * @param moduleInfoList List of ModuleInfo objects to select from.
     * @return The selected ModuleInfo object, or null if no valid selection is made.
     */
	public ModuleInfo selectModule(List<ModuleInfo> moduleInfoList) {
		if (moduleInfoList.isEmpty()) {
			System.out.println("No modules available.");
			return null;
		}

		while (true) {

			System.out.println("\n\tList of Existing Modules:");
			for (int i = 0; i < moduleInfoList.size(); i++) {
				System.out.println("\t" + (i + 1) + ". " + moduleInfoList.get(i).moduleCode());
			}

			System.out.println("Select a Module to Add a Examination Paper (enter the number): ");

			try {
				int moduleChoice = scanner.nextInt();
				scanner.nextLine();

				if (moduleChoice >= 1 && moduleChoice <= moduleInfoList.size()) {
					return moduleInfoList.get(moduleChoice - 1);
				} else {
					System.out.println(
							"Invalid selection. Please enter a number between 1 and " + moduleInfoList.size() + ".");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.nextLine();
			}
		}
	}

	 /**
     * Prompts the user with the given question and returns 'Yes' or 'No' based on the input.
     * Only accepts 'Y' or 'N' as valid inputs and keeps prompting until valid input is received.
     *
     * @param prompt The question to be asked from the user.
     * @return 'Yes' if the user inputs 'Y', 'No' if the user inputs 'N'.
     */
	private String getUserChoice(String prompt) {
		System.out.println(prompt + " (Y/N): ");
		String input = scanner.nextLine().trim();
		return "Y".equalsIgnoreCase(input) ? "Yes" : "No";
	}

	private void saveExaminationPaperToFile(String moduleCode, ExaminationPaper paper) {
		String fileName = moduleCode + "_examination_papers.dat";
		List<ExaminationPaper> papersList;

		try {
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
				papersList = new ArrayList<>();
			} else {
				papersList = FileUtil.loadFromFile(fileName);
				if (papersList == null) {
					papersList = new ArrayList<>();
				}
			}
		} catch (Exception e) {
			papersList = new ArrayList<>();
		}

		papersList.add(paper);

		FileUtil.saveToFile(fileName, papersList);
	}

	@Override
	public ExaminationPaper getExaminationPaper(String moduleCode) {
		return papers.get(moduleCode);
	}

	@Override
	public void updateExaminationPaper(String moduleCode, ExaminationPaper paper) {
		papers.put(moduleCode, paper);
	}

	@Override
	public List<ExaminationPaper> getAllExaminationPapers() {
		return new ArrayList<>(papers.values());
	}

	@Override
	public void listAllExaminationPapers() {
		while (true) {
			System.out.println(
					"\nEnter the module code to view its examination papers (or type 'exit' to go back to the main menu):");
			String input = scanner.nextLine().trim();

			if (input.equalsIgnoreCase("exit")) {
				break;
			}

			String moduleCode = input;
			String fileName = moduleCode + "_examination_papers.dat";

			File file = new File(fileName);
			if (!file.exists()) {
				System.out.println("\tNo examination papers found for module " + moduleCode
						+ ". Choose Option 5 to Add Examination Paper." + "\n");
				continue;
			}

			try {
				List<ExaminationPaper> papersList = FileUtil.loadFromFile(fileName);
				if (papersList != null && !papersList.isEmpty()) {
					for (ExaminationPaper paper : papersList) {
						System.out.println(paper);
					}
				} else {
					System.out.println("\tNo examination papers available for module " + moduleCode + "\n");
				}
			} catch (Exception e) {
				System.err.println("An error occurred while loading examination papers: " + e.getMessage());
			}
		}
	}

	private Question addNewQuestion(int totalMarksForQuestion) {
		String questionText = getQuestionTextFromUser();
		List<String> parts = new ArrayList<>();
		int partCounter = 1;
		int totalAssignedMarks = 0;

		while (totalAssignedMarks < totalMarksForQuestion && partCounter <= 5) {
			System.out.println("Enter Part " + labelForPart(partCounter) + " of the question:");
			String part = scanner.nextLine();

			if (part.isEmpty() && partCounter == 1) {
				System.out.println("At least one part is required for each question. Please enter Part (a).");
				continue;
			}

			if (!part.isEmpty()) {
				int marksForPart = getMarksForPart(totalMarksForQuestion - totalAssignedMarks);
				parts.add(part + " (" + marksForPart + " Marks)");
				totalAssignedMarks += marksForPart;
			} else {
				break;
			}

			partCounter++;
		}

		if (parts.isEmpty()) {
			System.out.println("Question not added. At least one part is required.");
			return null;
		}

		return new Question(questionText, parts, totalMarksForQuestion);
	}

	private String labelForPart(int partNumber) {
		return switch (partNumber) {
		case 1 -> "a";
		case 2 -> "b";
		case 3 -> "c";
		case 4 -> "d";
		case 5 -> "e";
		default -> "";
		};
	}

	private String getQuestionTextFromUser() {
		System.out.println("Enter the question text: ");
		return scanner.nextLine();
	}

	private int getMarksForPart(int remainingMarks) {
		int marksForPart;
		do {
			System.out.println("Enter marks for this part (Remaining marks: " + remainingMarks + "):");
			marksForPart = scanner.nextInt();
			scanner.nextLine();
			if (marksForPart < 0 || marksForPart > remainingMarks) {
				System.out.println("Invalid marks. Please enter a value between 0 and " + remainingMarks);
			}
		} while (marksForPart < 0 || marksForPart > remainingMarks);

		return marksForPart;
	}
}
