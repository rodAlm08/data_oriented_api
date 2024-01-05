package ie.atu.sw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ie.atu.sw.services.QuestionService;

/**
 * Implementation of the QuestionService interface. This class provides
 * functionality to manage questions, including adding, updating, retrieving,
 * and listing questions. Interacts with the user through the command line for
 * input and displays information accordingly.
 */
public class QuestionServiceImpl implements QuestionService, Serializable {
	private static final long serialVersionUID = 1L;

	private final Scanner scanner = new Scanner(System.in);
	private final Map<String, Question> questions = new HashMap<>();

	/**
	 * Adds a new question based on user inputs. Prompts the user for the question
	 * text, its parts, and the marks allocated to it.
	 *
	 * @return The newly created Question object.
	 */
	@Override
	public Question addQuestion() {
		System.out.println("Enter Question Text: ");
		String questionText = scanner.nextLine();
		List<String> parts = gatherParts();
		int marks = gatherMarks();

		// String questionId = UUID.randomUUID().toString();
		Question question = new Question(questionText, parts, marks);
		return question;
	}

	/**
	 * Gathers parts of a question from user input.
	 *
	 * @return A list of parts for the question.
	 */
	private List<String> gatherParts() {
		List<String> parts = new ArrayList<>();

		System.out.println("Enter the number of parts for this question: ");
		int numParts = scanner.nextInt();
		scanner.nextLine();

		for (int i = 1; i <= numParts; i++) {
			System.out.println("Enter Part " + i + ": ");
			String part = scanner.nextLine();
			parts.add(part);
		}

		return parts;
	}

	/**
	 * Gathers the marks allocated for a question from user input.
	 *
	 * @return The number of marks for the question.
	 */
	private int gatherMarks() {
		int marks = 0;
		System.out.println("Enter the number of marks for this question: ");
		marks = scanner.nextInt();
		scanner.nextLine();

		return marks;
	}

	/**
	 * Retrieves a question by its ID.
	 *
	 * @param questionId The ID of the question to retrieve.
	 * @return The Question object corresponding to the provided ID.
	 */
	@Override
	public Question getQuestion(String questionId) {
		return questions.get(questionId);
	}

	/**
	 * Updates a question identified by its ID.
	 *
	 * @param questionId The ID of the question to be updated.
	 * @param question   The updated Question object.
	 */
	@Override
	public void updateQuestion(String questionId, Question question) {
		questions.put(questionId, question);
	}

	/**
	 * Retrieves all questions stored.
	 *
	 * @return A list of all Question objects.
	 */
	@Override
	public List<Question> getAllQuestions() {
		return new ArrayList<>(questions.values());
	}

	/**
	 * Displays all questions currently stored. Lists each question with its
	 * details.
	 */
	@Override
	public void listAllQuestions() {
		System.out.println("List of Questions:");
		for (Question question : questions.values()) {
			System.out.println(question);
		}
	}
}
