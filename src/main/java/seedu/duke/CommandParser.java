package seedu.duke;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.command.Command;
import seedu.duke.command.ListModules;
import seedu.duke.command.DetailModules;
import seedu.duke.command.AddUserModule;
import seedu.duke.command.DeleteUserModule;
import seedu.duke.command.SuUserModule;
import seedu.duke.command.GetUserGPA;
import seedu.duke.command.GradeModule;
import seedu.duke.command.Help;
import seedu.duke.command.ViewGradRequirements;
import seedu.duke.command.RecommendedSchedule;
import seedu.duke.command.Specialisation;
import seedu.duke.command.Workload;
import seedu.duke.command.AddCustomModule;
import seedu.duke.errors.ErrorHandler;


import static seedu.duke.Duke.currentUser;

public class CommandParser {

    private static final Logger logger = Logger.getLogger(CommandParser.class.getName());

    static {
        logger.setLevel(Level.OFF);
    }

    // This method will parse and separate into required sections
    public String[] parseCommand(String userInput) {
        String[] words = userInput.split(" ", 5);
        return removeWhitespace(words);
    }

    // remove any extra spaces around each split
    public String[] removeWhitespace(String[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = input[i].trim();
        }
        return input;
    }


    // This method calls the appropriate command
    public boolean callCommand(String[] words) throws ArrayIndexOutOfBoundsException, NumberFormatException {
        String command = words[0];
        Command cmdObject = null;
        int semester;
        switch (command) {
        case "/view":
            if (words.length > 2) {
                logger.warning("View command has too many arguments.");
                System.out.println("Error: The '/view' command accepts at most one argument (semester number).");
            } else if (words.length == 2) {
                try {
                    semester = Integer.parseInt(words[1]);
                    if (semester < 1 || semester > 8) {
                        logger.warning("Invalid semester number: " + semester);
                        System.out.println("Error: Semester must be a number between 1 and 8.");
                    } else {
                        logger.info("Executing ListModules command to view modules in semester " + semester);
                        cmdObject = new ListModules(currentUser, words[1]);
                    }
                } catch (NumberFormatException e) {
                    logger.warning("Invalid semester format in View command.");
                    System.out.println("Error: Semester must be a number between 1 and 8.");
                }
            } else {
                logger.info("Executing ListModules command to view all modules.");
                cmdObject = new ListModules(currentUser);
            }
            break;

        case "/detail":
            logger.info("Executing DetailModules command with module code: " + words[1]);
            cmdObject = new DetailModules(words[1]);
            break;

        case "/add":
            semester = Integer.parseInt(words[2]);
            logger.info("Executing AddUserModule command with module code: " + words[1] +
                    ", semester: " + semester);
            cmdObject = new AddUserModule(currentUser, words[1], semester);
            break;

        case "/addCustom":
            semester = Integer.parseInt(words[2]);
            try {
                int creditNum = Integer.parseInt(words[3]);
                logger.info("Executing AddCustomModule command with module code: " + words[1] +
                        ", semester: " + semester);
                cmdObject = new AddCustomModule(currentUser, words[1], semester, creditNum, words[4]);
            } catch (NumberFormatException e) {
                ErrorHandler.integerInputError("creditNum", command);
            }
            break;

        case "/delete":
            logger.info("Executing RemoveUserModule command with module code: " + words[1]);
            cmdObject = new DeleteUserModule(currentUser, words[1]);
            break;

        case "/su":
            logger.info("Executing su command with module code: " + words[1]);
            cmdObject = new SuUserModule(currentUser, words[1]);
            break;

        case "/gpa":
            logger.info("Executing getting GPA.");
            cmdObject = new GetUserGPA(currentUser);
            break;

        case "/grade":
            logger.info("Executing grade module");
            cmdObject = new GradeModule(currentUser, words[1], words[2]);
            break;


        case "/help":
            logger.info("Displaying help file.");

            if (words.length == 2) {
                cmdObject = new Help(words[1]);
            } else {
                cmdObject = new Help();
            }
            break;

        case "/grad":
            if (words.length > 1) {
                ErrorHandler.excessInputError(command);
            } else {
                logger.info("Executing ViewGradRequirements command.");
                cmdObject = new ViewGradRequirements(currentUser);
            }
            break;

        case "/schedule":
            if (words.length > 1) {
                ErrorHandler.excessInputError(command);
            } else {
                logger.info("Executing RecommendedSchedule command.");
                cmdObject = new RecommendedSchedule();
            }
            break;

        case "/spec":
            if (words.length > 1) {
                ErrorHandler.excessInputError(command);
            } else {
                logger.info("Displaying Specialisations.");
                cmdObject = new Specialisation();
            }
            break;

        case "/workload":
            if (words.length > 2) {
                logger.warning("Workload command has too many arguments.");
                System.out.println("Error: The '/workload' command accepts at most one argument (semester number).");
            } else if (words.length == 2) {
                semester = Integer.parseInt(words[1]);
                logger.info("Executing Workload command to view modules in semester " + semester);
                cmdObject = new Workload(currentUser, semester);
            } else {
                logger.info("Executing Workload command to view all modules.");
                cmdObject = new Workload(currentUser);
            }
            break;

        case "/exit":
            logger.info("User exited program.");
            Ui.exitMessage();
            return false;

        case "": // empty command line
            logger.warning("User entered an empty command.");
            Ui.printEmptyCommandError();
            return true;

        default:
            logger.warning("Unknown command: " + command);
            Ui.printUnknownCommandError();
        }

        if (cmdObject != null) {
            cmdObject.execute();
        }

        return true;

    }
}

