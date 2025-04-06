package seedu.duke.storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import seedu.duke.data.AndPrereq;
import seedu.duke.data.Mod;
import seedu.duke.data.ModPrereq;
import seedu.duke.data.OrPrereq;
import seedu.duke.data.Prereq;
import seedu.duke.errors.ModNotInDatabase;

public class ModStorage {
    private static final List<Mod> modules = new ArrayList<>();

    static {
        loadModulesFromFile();
    }

    private static void loadModulesFromFile() {
        try (InputStream inputStream = ModStorage.class.getClassLoader()
                .getResourceAsStream("moduledata.txt")) {
            if (inputStream == null) {
                System.err.println("File not found: moduledata.txt");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                    StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length > 10) {
                        Prereq prereqTree = parsePrereqTree(parts[11]);
                        modules.add(new Mod(parts[1].trim(), parts[3].trim(),
                                Integer.parseInt(parts[2].trim()), parts[0].trim(),
                                Double.parseDouble(parts[4].trim()), Double.parseDouble(parts[5].trim()),
                                Double.parseDouble(parts[6].trim()), Double.parseDouble(parts[7].trim()),
                                Double.parseDouble(parts[8].trim()), parts[9].trim(), parts[10].trim(), prereqTree));
                    } else {
                        System.err.println("Invalid line format: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load modules from file: " + e.getMessage());
        }
    }

    public static Mod findModuleByCode(String code) throws ModNotInDatabase {
        for (Mod mod : modules) {
            if (mod.getCode().equals(code)) {
                return mod;
            }
        }
        throw new ModNotInDatabase(code + " not found in database");
    }

    public static List<Mod> getModules() {
        return modules;
    }

    public static Prereq parsePrereqTree(String json) {
        json = json.trim();

        // No Prerequisites
        if (json.equals("{}")) {
            return null;
        }

        // Base case: Single mod code (string without braces)
        if (json.startsWith("\"") && json.endsWith("\"")) {
            return new ModPrereq(json.substring(1, json.length() - 1).split(":"));
        }

        // Remove outer braces
        assert json.startsWith("{") && json.endsWith("}");
        json = json.substring(1, json.length() - 1);

        // Extract the key ("and" or "or")
        assert json.contains(":");
        String[] keyValuePair = json.split(":", 2);

        assert keyValuePair.length == 2;
        String key = keyValuePair[0].equals("and") ? "and" : "or";
        String values = keyValuePair[1];

        // Remove outer brackets []
        assert values.startsWith("[") && values.endsWith("]");
        values = values.substring(1, values.length() - 1).trim();

        // Split sub-prerequisites (assumes valid JSON formatting)
        List<Prereq> subPrereqs = new ArrayList<>();
        int start = 0;
        int depth = 0;
        for (int i = 0; i < values.length(); i++) {
            char c = values.charAt(i);
            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;
            } else if (c == ',' && depth == 0) {
                subPrereqs.add(parsePrereqTree(values.substring(start, i).trim()));
                start = i + 1;
            }
        }

        if (start < values.length()) {
            subPrereqs.add(parsePrereqTree(values.substring(start).trim()));
        }

        return "and".equals(key) ? new AndPrereq(subPrereqs) : new OrPrereq(subPrereqs);
    }
}
