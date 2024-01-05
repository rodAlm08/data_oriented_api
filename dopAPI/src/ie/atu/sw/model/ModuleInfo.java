package ie.atu.sw.model;

import java.io.Serializable;

import ie.atu.sw.services.Examiner;

/**
 * A record representing information about a module. This record holds various
 * attributes of a module, including code, title, registration numbers,
 * associated program information, and assigned internal and external examiners.
 */
public record ModuleInfo(String moduleCode, String moduleTitle, int registrations, String programCode,
		String programTitle, int year, String school, String department, Examiner internalExaminer,
		Examiner externalExaminer) implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Retrieves the module code.
	 *
	 * @return The code representing this module.
	 */
	public String moduleCode() {
		return moduleCode;
	}

	/**
	 * Retrieves the title of the module.
	 *
	 * @return The title of the module.
	 */
	public String moduleTitle() {
		return moduleTitle;
	}

	public String programCode() {
		return programCode;
	}

	public String programTitle() {
		return programTitle;
	}

	public String department() {
		return department;
	}

	public Examiner internalExaminer() {
		return internalExaminer;
	}

	public Examiner getExternalExaminer() {
		return externalExaminer;
	}

	/**
	 * Returns a string representation of the module's information.
	 *
	 * @return A formatted string containing detailed information about the module.
	 */
	@Override
	public String toString() {
		return "Module Info:" + "\n  Module Code: " + moduleCode + "\n  Module Title: " + moduleTitle
				+ "\n  Registrations: " + registrations + "\n  Program Code: " + programCode + "\n  Program Title: "
				+ programTitle + "\n  Year: " + year + "\n  School: " + school + "\n  Department: " + department + "\n"
				+ "\n  Internal Examiner: " + internalExaminer + "\n  External Examiner: " + externalExaminer + "\n"
				+ "\n-----------------------------------------------------------------------------\n";
	}

}