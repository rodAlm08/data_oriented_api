package ie.atu.sw.model;

import java.io.Serializable;

/**
 * A record representing the rules for an examination.
 * This record holds rules such as the minimum and maximum number of questions,
 * parts per question, and the total marks for an examination.
 */
public record Rules(int minQuestions, int maxQuestions, int minParts, int maxParts, int totalMarks)
		implements Serializable {
	public static final int DEFAULT_MAX_QUESTIONS = 6;
	public static final int DEFAULT_MIN_QUESTIONS = 2;
	public static final int DEFAULT_REQUIRED_ANSWERS = 4;
	public static final int DEFAULT_TOTAL_MARKS = 100;
	private static final long serialVersionUID = 1L;
}
