package ie.atu.sw.enums;

import java.io.Serializable;

/**
 * Enumeration representing the different actions that an external examiner can
 * take on an examination paper. This enum is used to categorize and record the
 * types of actions an examiner might perform.
 */
public enum ExternalExaminerActions implements Serializable {
	/**
	 * Action indicating that a comment has been added to an examination paper.
	 */
	ADD_COMMENT,
	/**
	 * Action indicating that an examination paper has been approved.
	 */
	APPROVE,
	/**
	 * Action indicating that an examination paper has been rejected.
	 */
	REJECT
}
