package ie.atu.sw.services;

import java.util.List;

import ie.atu.sw.model.ExternalExaminerAction;


/**
 * Interface for services handling actions by external examiners. Defines the
 * operations for recording, approving, rejecting, and commenting on actions, as
 * well as retrieving and listing these actions.
 */
public interface ExternalExaminerService {
	/**
	 * Records an action taken by an external examiner. The implementation should
	 * define how the action is recorded and stored.
	 */
	void recordAction();

	void approveAction();

	void rejectAction();

	/**
	 * Records a comment as an action. The implementation should allow adding
	 * comments to a specific action.
	 */
	void commentAction();

	/**
	 * Retrieves a list of all recorded actions by external examiners.
	 *
	 * @return A list of all ExternalExaminerActions.
	 */
	List<ExternalExaminerAction> getAllActions();

	/**
	 * Lists all actions taken by external examiners. This method should display the
	 * details of all recorded actions.
	 */
	void listAllExternalExaminerActions();

	/**
	 * Lists actions taken by external examiners on a specific paper. The
	 * implementation should allow viewing actions associated with a particular
	 * examination paper.
	 */
	void listActionsPerPaper();

}
