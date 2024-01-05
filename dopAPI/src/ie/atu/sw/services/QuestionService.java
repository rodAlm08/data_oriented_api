package ie.atu.sw.services;

import java.util.List;

import ie.atu.sw.model.Question;

/**
 * Interface for services handling questions. Defines the operations for adding,
 * retrieving, updating, and listing questions.
 */
public interface QuestionService {
	/**
	 * Adds a new question. The implementation should define how the question is
	 * created and stored.
	 *
	 * @return The newly created Question object.
	 */
	Question addQuestion();

	/**
	 * Retrieves a question based on its unique identifier.
	 *
	 * @param questionId The unique identifier of the question to retrieve.
	 * @return The Question object corresponding to the provided ID, or null if not
	 *         found.
	 */
	Question getQuestion(String questionId);

	/**
	 * Updates the information of an existing question.
	 *
	 * @param questionId The identifier of the question to be updated.
	 * @param question   The updated Question object.
	 */
	void updateQuestion(String questionId, Question question);

	/**
	 * Retrieves a list of all questions.
	 *
	 * @return A list of all Question objects.
	 */
	List<Question> getAllQuestions();

	/**
	 * Lists all the questions currently stored. This method should display the
	 * details of all questions.
	 */
	void listAllQuestions();

}
