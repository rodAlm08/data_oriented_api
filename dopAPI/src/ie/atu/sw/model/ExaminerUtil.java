package ie.atu.sw.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ie.atu.sw.services.Examiner;

/**
 * Utility class for managing examiners. Provides functionalities to create,
 * save, load, and display examiners. Interacts with users through the command
 * line to gather input and display information.
 */
public class ExaminerUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Scanner scanner = new Scanner(System.in);
	private static final String examinerDataFile = "examiner_data.dat";

	/**
	 * Creates a new examiner based on user input. Prompts the user for details such
	 * as name, department, and type. Depending on the type, it delegates to either
	 * internal or external examiner creation.
	 *
	 * @return The newly created Examiner object.
	 */
	public static Examiner createExaminer() {

		System.out.println("Enter Examiner Name: ");
		String name = scanner.nextLine();
		System.out.println("Enter Department: ");
		String department = scanner.nextLine();
		System.out.println("Select Examiner Type:");
		System.out.println("1. Internal");
		System.out.println("2. External");
		int typeChoice = scanner.nextInt();
		scanner.nextLine();
		ExaminerType type = (typeChoice == 1) ? ExaminerType.INTERNAL : ExaminerType.EXTERNAL;

		if (type == ExaminerType.INTERNAL) {
			return createInternalExaminer(name, type, department);
		} else {
			return createExternalExaminer(name, type, department);
		}

	}

	/**
	 * Creates and returns an InternalExaminer based on user inputs. Prompts the
	 * user for additional details specific to internal examiners.
	 *
	 * @param name       The name of the internal examiner.
	 * @param type       The type of the examiner.
	 * @param department The department of the examiner.
	 * @return The newly created InternalExaminer object.
	 */
	private static InternalExaminer createInternalExaminer(String name, ExaminerType type, String department) {

		System.out.println("Enter School: ");
		String school = scanner.nextLine();

		System.out.println("Enter Email: ");
		String email = scanner.nextLine();

		int crn = 0;
		boolean validInput = false;

		while (!validInput) {
			System.out.println("Enter CRN (only numbers): ");
			if (scanner.hasNextInt()) {
				crn = scanner.nextInt();
				validInput = true;
			} else {
				System.out.println("Invalid input. Please enter a number.");
			}
			scanner.nextLine();
		}

		InternalExaminer internalExaminer = new InternalExaminer(name, type, department, school, email, crn);
		saveExaminer(internalExaminer);
		return internalExaminer;
	}

	 /**
     * Creates and returns an ExternalExaminer based on user inputs.
     * Prompts the user for additional details specific to external examiners.
     *
     * @param name The name of the external examiner.
     * @param type The type of the examiner.
     * @param department The department of the examiner.
     * @return The newly created ExternalExaminer object.
     */
	private static ExternalExaminer createExternalExaminer(String name, ExaminerType type, String department) {

		System.out.println("Enter Institution: ");
		String institution = scanner.nextLine();
		ExternalExaminer externalExaminer = new ExternalExaminer(name, type, department, institution);
		saveExaminer(externalExaminer);
		return externalExaminer;
	}

	/**
     * Saves a given examiner to a file.
     * Serializes the examiner object and writes it to the specified data file.
     *
     * @param examiner The Examiner object to be saved.
     */
	private static void saveExaminer(Examiner examiner) {
		List<Examiner> examinerList = loadExaminers();
		if (examinerList == null) {
			examinerList = new ArrayList<>();
		}
		examinerList.add(examiner);
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(examinerDataFile))) {
			outputStream.writeObject(examinerList);
			System.out.println("Examiner saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error saving examiner.");
		}
	}

	/**
     * Loads and returns a list of examiners from a file.
     * Deserialises the examiner objects from the specified data file.
     *
     * @return A list of Examiner objects loaded from the file.
     */
	public static List<Examiner> loadExaminers() {
		File file = new File(examinerDataFile);
		if (!file.exists()) {
			return new ArrayList<>();
		}

		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
			List<Examiner> examinerList = (List<Examiner>) inputStream.readObject();
			// System.out.println("Examiners loaded successfully.");
			return examinerList;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error loading examiners: " + e.getMessage());
			return null;
		}
	}

	/**
     * Displays all examiners in the system.
     * Retrieves the list of examiners and prints their details to the console.
     */
	public static void displayAllExaminers() {
		List<Examiner> examinerList = loadExaminers();

		if (examinerList != null && !examinerList.isEmpty()) {
			System.out.println("\tList of Examiners:");
			for (Examiner examiner : examinerList) {
				System.out.println(examiner);
			}
		} else {
			System.out.println("\tNo examiners found.\n");
		}
	}

}
