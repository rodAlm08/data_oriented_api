package ie.atu.sw.model;

import java.io.Serializable;

import ie.atu.sw.services.Examiner;

/**
 * A record representing an internal examiner. This record holds information
 * about an examiner who is usually part of the institution's staff, including their
 * name, type, department, school, email, and CRN (Course Registration Number).
 */
public record InternalExaminer(String name, ExaminerType type, String department, String school, String email, int crn)
		implements Examiner, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public ExaminerType getType() {

		return type;
	}


	@Override
	public String getName() {

		return name;
	}

	/**
	 * Retrieves the department associated with the internal examiner.
	 *
	 * @return The department of the examiner.
	 */
	@Override
	public String getDepartment() {

		return department;
	}

	/**
	 * Returns a string representation of the internal examiner's details.
	 *
	 * @return A formatted string containing the examiner's information.
	 */
	@Override
	public String toString() {
		return "Internal Examiner Details:" + "\n    Name: " + name + "\n    Type: " + type + "\n    Department: "
				+ department + "\n    School: " + school + "\n    Email: " + email + "\n    CRN: " + crn + "\n";
	}

}