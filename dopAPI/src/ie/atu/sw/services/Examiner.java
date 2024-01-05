package ie.atu.sw.services;

import ie.atu.sw.model.ExaminerType;

/**
 * Interface representing an examiner. Defines the basic properties and methods
 * that an examiner should have, including their name, type, and associated
 * department.
 */
public interface Examiner {
	/**
	 * Retrieves the name of the examiner.
	 *
	 * @return The name of the examiner.
	 */
	String getName();

	/**
	 * Retrieves the type of the examiner. This can be either internal or external,
	 * as defined in ExaminerType.
	 *
	 * @return The ExaminerType representing the type of the examiner.
	 */
	ExaminerType getType();

	/**
	 * Retrieves the department associated with the examiner.
	 *
	 * @return The name of the department to which the examiner belongs.
	 */
	String getDepartment();
}
