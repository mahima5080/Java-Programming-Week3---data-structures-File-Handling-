import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyCounter {
    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        String outputFilePath = "word_frequency_output.txt";

        // Step 1: Pre-create input.txt with sample content if it does not exist
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFilePath))) {
            writer.write("Java is an object oriented programming language.\n");
            writer.write("Java collections framework provides ArrayList, HashMap, and HashSet.\n");
            writer.write("File handling in Java uses FileReader and FileWriter.\n");
        } catch (IOException e) {
            System.out.println("Error initializing input file: " + e.getMessage());
        }

        Map<String, Integer> wordCountMap = new HashMap<>();

        // Step 2: Read file and count word frequency
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Remove punctuation and convert to lowercase
                String[] words = line.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");
                for (String word : words) {
                    if (!word.trim().isEmpty()) {
                        wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                    }
                }
            }
            System.out.println("✅ File read successfully. Processing word frequencies...");
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }

        // Step 3: Write output summary to another file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("====================================\n");
            writer.write("      WORD FREQUENCY SUMMARY        \n");
            writer.write("====================================\n");
            for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
                writer.write(String.format("%-20s : %d\n", entry.getKey(), entry.getValue()));
            }
            writer.write("====================================\n");
            System.out.println("✅ Word frequency results successfully written to '" + outputFilePath + "'!");
        } catch (IOException e) {
            System.out.println("❌ Error writing output file: " + e.getMessage());
        }
    }
}