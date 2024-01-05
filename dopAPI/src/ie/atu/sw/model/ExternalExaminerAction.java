package ie.atu.sw.model;

import java.io.Serializable;

import ie.atu.sw.enums.ExternalExaminerActions;

/**
 * Represents an action taken by an external examiner on an examination paper.
 * This class stores details about the action, including the module code, the
 * examiner, their comments, the type of action, and the associated paper ID.
 */

public class ExternalExaminerAction implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String moduleCode;
	private final ExternalExaminer examiner;
	private final String comment;
	private ExternalExaminerActions action;
	private final String paperId;

	/**
     * Constructs a new ExternalExaminerAction with specified details.
     *
     * @param moduleCode The code of the module related to the action.
     * @param examiner The external examiner performing the action.
     * @param comment A comment provided by the examiner.
     * @param action The type of action being recorded.
     * @param paperId The ID of the examination paper related to the action.
     */
	public ExternalExaminerAction(String moduleCode, ExternalExaminer examiner, String comment,
			ExternalExaminerActions action, String paperId) {
		super();
		this.moduleCode = moduleCode;
		this.examiner = examiner;
		this.comment = comment;
		this.action = action;
		this.paperId = paperId;
	}


	public String getPaperId() {
		return paperId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public ExternalExaminer getExaminer() {
		return examiner;
	}

	public String getComment() {
		return comment;
	}

	public ExternalExaminerActions getAction() {
		return action;
	}

	/**
     * Returns a string representation of this action.
     *
     * @return A formatted string detailing the action information.
     */
	@Override
	public String toString() {
		return "Action Details:\n" + "   Module Code: " + moduleCode + "\n" + "   Paper ID: " + paperId + "\n"
				+ "   Examiner: " + examiner.getName() + "\n" + "   Action: " + action + "\n" + "   Comment: "
				+ comment;
	}

}
