package ie.atu.sw.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Utility class for file operations,for saving to and loading
 * from files. This class provides static methods to serialize and deserialize
 * data to and from files.
 */
public class FileUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Saves a list of objects to a file. This method serializes a list of objects
	 * and writes them to the specified file.
	 *
	 * @param fileName The name of the file to save the data to.
	 * @param data     The list of objects to be saved.
	 * @throws IOException If an I/O error occurs while writing to the file.
	 */
	public static void saveToFile(String fileName, List<?> data) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
			outputStream.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads and returns a list of objects from a file. This method deserializes a
	 * list of objects from the specified file.
	 *
	 * @param <T>      The type of objects in the returned list.
	 * @param fileName The name of the file to load the data from.
	 * @return A list of deserialized objects of type T, or null if an error occurs.
	 * @throws IOException            If an I/O error occurs while reading from the
	 *                                file.
	 * @throws ClassNotFoundException If the class of a serialized object cannot be
	 *                                found.
	 */
	public static <T> List<T> loadFromFile(String fileName) {
		// System.out.println(fileName);
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
			return (List<T>) inputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
