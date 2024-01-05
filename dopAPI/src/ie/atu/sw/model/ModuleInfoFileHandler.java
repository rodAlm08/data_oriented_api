package ie.atu.sw.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling file operations related to ModuleInfo objects.
 * Provides functionality to serialize (save) and deserialize (load) ModuleInfo
 * objects to and from a file.
 */
public class ModuleInfoFileHandler implements Serializable {
	private static final String MODULE_INFO_FILE = "module_data.dat";
	private static final long serialVersionUID = 1L;

	/**
	 * Saves a list of ModuleInfo objects to a file. Serializes the provided list of
	 * ModuleInfo objects and writes them to a predefined file.
	 *
	 * @param moduleInfoList The list of ModuleInfo objects to be saved.
	 * @throws IOException If an I/O error occurs during writing to the file.
	 */
	public static void saveModuleInfo(List<ModuleInfo> moduleInfoList) {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(MODULE_INFO_FILE))) {
			outputStream.writeObject(moduleInfoList);
			System.out.println("\n\tAll files saved successfully.");
		} catch (IOException e) {
			System.err.println("Error saving module information: " + e.getMessage());
		}
	}

	/**
	 * Loads and returns a list of ModuleInfo objects from a file. Deserializes the
	 * ModuleInfo objects from a predefined file and returns them as a list.
	 *
	 * @return A list of ModuleInfo objects loaded from the file.
	 * @throws IOException            If an I/O error occurs during reading from the
	 *                                file.
	 * @throws ClassNotFoundException If the class of a serialized object cannot be
	 *                                found.
	 */
	public static List<ModuleInfo> loadModuleInfo() {
		List<ModuleInfo> moduleInfoList = new ArrayList<>();

		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(MODULE_INFO_FILE))) {
			moduleInfoList = (List<ModuleInfo>) inputStream.readObject();
			// System.out.println("Module information loaded successfully.");
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error loading module information: " + e.getMessage());
		}

		return moduleInfoList;
	}
}
