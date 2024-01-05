package ie.atu.sw;

import java.util.List;
import java.util.Scanner;

import ie.atu.sw.model.ExaminationPaperServiceImpl;
import ie.atu.sw.model.ExaminerUtil;
import ie.atu.sw.model.ExaminerServiceImpl;
import ie.atu.sw.model.ModuleInfo;
import ie.atu.sw.model.ModuleInfoFileHandler;
import ie.atu.sw.model.ModuleServiceImpl;
import ie.atu.sw.services.ExaminationPaperService;
import ie.atu.sw.services.ExternalExaminerService;
import ie.atu.sw.services.ModuleService;

/**
 * Main runner class for the ATU System for Approving Examination Papers. This
 * class handles the user interface and interaction, allowing users to perform
 * actions related to examiners, modules, and examination papers.
 */
public class Runner {

	private final ModuleService moduleService;
	private final ExaminationPaperService paperService;
	private final ExternalExaminerService examinerService;
	private final Scanner scanner;

	/**
	 * Constructs a Runner instance with the specified services and scanner.
	 *
	 * @param moduleService   The service for managing modules.
	 * @param paperService    The service for managing examination papers.
	 * @param examinerService The service for managing external examiner actions.
	 * @param scanner         The scanner for reading user input.
	 */
	public Runner(ModuleService moduleService, ExaminationPaperService paperService,
			ExternalExaminerService examinerService, Scanner scanner) {
		this.moduleService = moduleService;
		this.paperService = paperService;
		this.examinerService = examinerService;
		this.scanner = scanner;
	}

	/**
	 * Starts the application, providing a menu for user interaction. The user can
	 * choose from various options to manage examiners, modules, and examination
	 * papers.
	 */
	public void start() {
		boolean running = true;
		List<ModuleInfo> moduleInfoList = ModuleInfoFileHandler.loadModuleInfo();

		while (running) {

			System.out.println("\n************************************************************");
			System.out.println("*      ATU - Dept. Computer Science & Applied Physics      *");
			System.out.println("*                                                          *");
			System.out.println("*        ATU SYSTEM FOR APPROVING EXAMINATION PAPERS       *");
			System.out.println("*                                                          *");
			System.out.println("************************************************************");

			System.out.println("1. Create Examiner");
			System.out.println("2. List All Examiners");
			System.out.println("3. Create Module");
			System.out.println("4. List All Modules");
			System.out.println("5. Add Examination Paper");
			System.out.println("6. List All Examination Papers");
			System.out.println("7. Record External Examiner Action");
			System.out.println("8. List Action per Examiner");
			System.out.println("9. List Action per Paper");
			System.out.println("10. Exit");
			System.out.print("Choose an option: ");

			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				ExaminerUtil.createExaminer();
				break;
			case 2:
				ExaminerUtil.displayAllExaminers();

				break;
			case 3:
				moduleService.createModule(moduleInfoList);

				break;
			case 4:
				moduleService.listAllModules(moduleInfoList);
				break;
			case 5:
				paperService.addExaminationPaper();

				break;
			case 6:
				paperService.listAllExaminationPapers();

				break;
			case 7:
				examinerService.recordAction();

				break;
			case 8:
				examinerService.listAllExternalExaminerActions();
				break;

			case 9:
				examinerService.listActionsPerPaper();
				break;
			case 10:
				ModuleInfoFileHandler.saveModuleInfo(moduleInfoList);
				System.out.println("\n************************************************************");
				System.out.println("*                                                          *");
				System.out.println("*                         GOOD BYE!                        *");
				System.out.println("*                                                          *");
				System.out.println("************************************************************");
				running = false;
				break;
			default:
				System.out.println("Invalid option. Please try again.");
			}
		}
	}

	/**
	 * The main method to start the application.
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		ModuleService moduleService = new ModuleServiceImpl();
		ExaminationPaperService paperService = new ExaminationPaperServiceImpl();
		ExternalExaminerService examinerService = new ExaminerServiceImpl();
		Scanner scanner = new Scanner(System.in);

		Runner runner = new Runner(moduleService, paperService, examinerService, scanner);
		runner.start();
	}
}
