package cli;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class JsonInputManager {

    /**
     * Reads a JSON configuration file and converts it to a TicketConfig object.
     *
     * @param filePath the path to the JSON configuration file
     * @return the populated TicketConfig object, or null if an error occurs
     */

    public static TicketConfig readJsonConfig(String filePath) {
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson(); // Gson is used to convert JSON to Java objects
            TicketConfig config = gson.fromJson(reader, TicketConfig.class);

            if (config == null) {
                System.out.println("Config object is null after JSON parsing.");
            } else {
                System.out.println("Config object successfully created.");
            }
            // Validate the configuration
            if (config != null) {
                config.validateConfig(); // Validate the extracted data
            }

            return config; // Return the populated TicketConfig object
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (com.google.gson.JsonSyntaxException e) {
            System.out.println("Error parsing JSON: Invalid syntax.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid configuration: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        return null; // Return null if there's an error
    }
}

