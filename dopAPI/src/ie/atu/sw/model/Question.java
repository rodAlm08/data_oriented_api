package ie.atu.sw.model;

import java.io.Serializable;
import java.util.List;

/**
 * A record representing a question within an examination paper. This record
 * holds the text of the question, a list of its parts, and the total
 * marks assigned to it.
 */
public record Question(String questionText, List<String> parts, int marks) implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Returns a string representation of the question, including its text, parts,
	 * and marks.
	 *
	 * @return A formatted string containing the question text and its parts.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Question Text: ").append(questionText).append("\n");
		for (int i = 0; i < parts.size(); i++) {
			sb.append("\t(").append(labelForPart(i + 1)).append(") ").append(parts.get(i)).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Converts a part number to its corresponding label (e.g., 1 to 'a', 2 to 'b',
	 * etc.).
	 *
	 * @param partNumber The number of the part.
	 * @return A string label for the part number.
	 */
	private static String labelForPart(int partNumber) {
		return switch (partNumber) {
		case 1 -> "a";
		case 2 -> "b";
		case 3 -> "c";
		case 4 -> "d";
		case 5 -> "e";
		default -> "";
		};
	}
}