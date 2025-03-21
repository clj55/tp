package seedu.duke.command;
import seedu.duke.data.Mod;

public class DetailModules implements Command {
    private final String modCode;

    public DetailModules(String modCode) {
        this.modCode = modCode;
    }

    public void textWrapDescription(String description) { // prints description to fit into the output window
        String[] words = description.split(" ");
        int wrapLength = 80;

        StringBuilder currentLine = new StringBuilder();
        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > wrapLength) {
                System.out.println(currentLine);
                currentLine = new StringBuilder(word);
                currentLine.append(" ");
            } else {
                currentLine.append(word);
                currentLine.append(" ");
            }
        }
        if (!currentLine.toString().isEmpty()) {
            System.out.println(currentLine);
        }
    }

    public void execute() {
        Mod module = new Mod(this.modCode);
        if ( module.getCode() != null ) {
            System.out.println(module);
            textWrapDescription(module.getDescription());
        } else {
            System.out.println("Unable to retrieve module details.");
        }
    }
}
