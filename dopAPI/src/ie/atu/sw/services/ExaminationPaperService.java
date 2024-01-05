package ie.atu.sw.services;

import java.util.List;

import ie.atu.sw.model.ExaminationPaper;

/**
 * Interface for services handling examination papers. Defines the operations
 * for adding, retrieving, updating, and listing examination papers.
 */
public interface ExaminationPaperService {
	/**
	 * Adds a new examination paper. The implementation should handle the input and
	 * creation of a new ExaminationPaper object.
	 */
	void addExaminationPaper();

	/**
	 * Retrieves an examination paper based on the given module code.
	 *
	 * @param moduleCode The module code associated with the examination paper.
	 * @return The ExaminationPaper object associated with the given module code, or
	 *         null if not found.
	 */
	ExaminationPaper getExaminationPaper(String moduleCode);

	/**
	 * Updates the information of an existing examination paper.
	 *
	 * @param moduleCode The module code of the examination paper to be updated.
	 * @param paper      The new ExaminationPaper object with updated information.
	 */
	void updateExaminationPaper(String moduleCode, ExaminationPaper paper);

	/**
	 * Retrieves a list of all examination papers.
	 *
	 * @return A list of all ExaminationPaper objects.
	 */
	List<ExaminationPaper> getAllExaminationPapers();

	/**
	 * Lists all the examination papers. This method should display the details of
	 * all examination papers.
	 */
	void listAllExaminationPapers();
}
