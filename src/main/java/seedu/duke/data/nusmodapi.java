package seedu.duke.data;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.net.HttpURLConnection;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class nusmodapi {

    private static URL url;
    private static HttpURLConnection con = null;
    private static final Path path = Paths.get("src/main/resources/moduledata.txt");

    public static void main(String[] args) throws IOException {
        getPrereqs();
    }

    public static void getPrereqs() throws IOException {

        List<Mod> all_mods = MasterModuleList.getModules();
//        List<Mod> all_mods = new ArrayList<>();
//        all_mods.add(new Mod("CG1111A"));
        int i = 0;
        for (Mod mod : all_mods) {
            String default_url = "https://api.nusmods.com/v2/2024-2025/modules/{moduleCode}.json";
            String mod_url = default_url.replace("{moduleCode}", mod.getCode());
//            String mod_url = default_url.replace("{moduleCode}", "CS3230");
            System.out.println(mod_url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(mod_url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("Response: " + response.body());
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode full_response = objectMapper.readTree(response.body());
                    JsonNode prereqTree = full_response.get("prereqTree");
                    System.out.println(prereqTree.toString());
//                    Prereq parsedPrereq = parsePrereqTree(prereqTree);
//                    System.out.println(parsedPrereq);
//                    System.out.println("printed prereqtree");
//                    appendtoline(i, prereqTree.toString());
                } else {
                    System.out.println("HTTP Error: " + response.statusCode());
                }
            } catch (NullPointerException e) {
                System.out.println("No prereqTree in mod");
//                appendtoline(i, "{}");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    public static Prereq parsePrereqTree(JsonNode prereqTree) {
        if (prereqTree.isTextual()) {
            // Base case: Single mod code
            return new ModPrereq(prereqTree.asText().split(":"));
        } else if (prereqTree.isObject()) {
            // Iterate through the object fields (expecting "and" or "or")
            Iterator<Map.Entry<String, JsonNode>> fields = prereqTree.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String fieldName = field.getKey();
                JsonNode fieldValue = field.getValue();

                List<Prereq> subPrereqs = new ArrayList<>();
                for (JsonNode child : fieldValue) {
                    subPrereqs.add(parsePrereqTree(child));
                }

                if ("and".equals(fieldName)) {
                    return new AndPrereq(subPrereqs);
                } else if ("or".equals(fieldName)) {
                    return new OrPrereq(subPrereqs);
                }
            }
        }

        return null;
    }

    public static List<String> getFileLines() throws IOException {
        return Files.readAllLines(path);
    }

    public static void writeFile(List<String> fileLines) throws IOException {
        Files.write(path, fileLines, StandardCharsets.UTF_8);
    }

    public static void appendtoline(int lineIndex, String text) throws IOException {
        List<String> fileLines = getFileLines();
        String line = fileLines.get(lineIndex) + "|" + text;
        fileLines.set(lineIndex, line);
        writeFile(fileLines);
    }

}
