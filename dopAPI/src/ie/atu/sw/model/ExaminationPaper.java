package ie.atu.sw.model;

import java.io.Serializable;
import java.util.List;

/**
 * Represents an examination paper, detailing properties and actions taken by external examiners.
 * This record holds all the attributes about an examination paper, such ID, module code,
 * allowances for various materials during the exam, and the total number of questions.
 * It also tracks a list of questions and actions performed by external examiners.
 *
 * @param paperId                       Unique identifier for the examination paper.
 * @param moduleCode                    Code of the module to which the examination paper belongs.
 * @param allowLogTables                Indicates whether log tables are allowed in the exam.
 * @param allowActuarialTables          Indicates whether actuarial tables are allowed.
 * @param allowStatisticalTables        Indicates whether statistical tables are allowed.
 * @param allowGraphPaper               Indicates whether graph paper is allowed.
 * @param allowDictionaries             Indicates whether dictionaries are allowed.
 * @param allowAttachedAnswerSheet      Indicates whether attached answer sheets are allowed.
 * @param allowThermodynamicTables      Indicates whether thermodynamic tables are allowed.
 * @param allowNonProgrammableCalculators Indicates whether non-programmable calculators are allowed.
 * @param allowRateTables               Indicates whether rate tables are allowed.
 * @param totalQuestions                Total number of questions in the examination paper.
 * @param requiredAnswers               Number of answers required.
 * @param questions                     List of questions included in the examination paper.
 * @param actions                       List of actions taken by external examiners on this examination paper.
 */

public record ExaminationPaper(
    String paperId,
    String moduleCode,
    String allowLogTables,
    String allowActuarialTables,
    String allowStatisticalTables,
    String allowGraphPaper,
    String allowDictionaries,
    String allowAttachedAnswerSheet,
    String allowThermodynamicTables,
    String allowNonProgrammableCalculators,
    String allowRateTables,
    int totalQuestions,
    int requiredAnswers,
    List<Question> questions,
    List<ExternalExaminerAction> actions
) implements Serializable {
    private static final long serialVersionUID = 1L;



	public String getPaperId() {
		return paperId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExaminationPaper:\n");
        sb.append("\tpaperId='").append(paperId).append("',\n");
        sb.append("\tmoduleCode='").append(moduleCode).append("',\n");
        sb.append("\tallowLogTables='").append(allowLogTables).append("',\n");
        sb.append("\tallowActuarialTables='").append(allowActuarialTables).append("',\n");
        sb.append("\tallowStatisticalTables='").append(allowStatisticalTables).append("',\n");
        sb.append("\tallowGraphPaper='").append(allowGraphPaper).append("',\n");
        sb.append("\tallowDictionaries='").append(allowDictionaries).append("',\n");
        sb.append("\tallowAttachedAnswerSheet='").append(allowAttachedAnswerSheet).append("',\n");
        sb.append("\tallowThermodynamicTables='").append(allowThermodynamicTables).append("',\n");
        sb.append("\tallowNonProgrammableCalculators='").append(allowNonProgrammableCalculators).append("',\n");
        sb.append("\tallowRateTables='").append(allowRateTables).append("',\n");
        sb.append("\ttotalQuestions=").append(totalQuestions).append(",\n");
        sb.append("\trequiredAnswers=").append(requiredAnswers).append(",\n");
        sb.append("\tquestions=[\n");
        for (int i = 0; i < questions.size(); i++) {
            sb.append("\t").append(i + 1).append(". ").append(questions.get(i).toString()).append("\n");
        }

        sb.append("\t");
        for (ExternalExaminerAction action : actions) {
            sb.append("\t\t").append(action.toString()).append(",\n");
        }
        sb.append("}");

        return sb.toString();
    }


}