package ie.atu.sw.model;

import java.io.Serializable;

import ie.atu.sw.services.Examiner;

/**
 * A record representing an external examiner. Holds information about an
 * examiner who usually is not a part of the institution's staff but collaborates for
 * academic purposes. This record includes details such as the examiner's name,
 * type, department, and institution.
 */
public record ExternalExaminer(String name, ExaminerType type, String department, String institution)
		implements Examiner, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String getName() {

		return name;
	}


	@Override
	public ExaminerType getType() {

		return type;
	}

	/**
	 * Retrieves the department associated with the external examiner.
	 *
	 * @return The department of the examiner.
	 */
	@Override
	public String getDepartment() {

		return department;
	}

	/**
	 * Returns a string representation of the external examiner's details.
	 *
	 * @return A formatted string containing the examiner's information.
	 */
	@Override
	public String toString() {
		return "External Examiner Details:" + "\n    Name: " + name + "\n    Type: " + type + "\n    Department: "
				+ department + "\n    Institution: " + institution + "\n";
	}

}