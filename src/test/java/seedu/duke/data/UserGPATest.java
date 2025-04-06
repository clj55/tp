package seedu.duke.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.duke.Duke.currentUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.command.AddUserModule;
import seedu.duke.command.Command;
import seedu.duke.command.GradeModule;
import seedu.duke.command.SuUserModule;

public class UserGPATest {
    @BeforeEach
    public void setUp() {
        currentUser.clearModules(); // Clear the moduleList

    }

    @Test
    public void calculateGPA() {
        String grade1 = "A";
        String moduleCode1 = "CG2028"; //2 MC
        int semester1 = 3;
        double gpa1 = 5.0;

        String grade2 = "D";
        String moduleCode2 = "CS1010"; //4 MC
        int semester2 = 3;

        double expected_combinedGPA = 2.33;

        Command addModule1 = new AddUserModule(currentUser, moduleCode1, semester1);
        addModule1.execute();
        assertTrue(currentUser.hasModule(moduleCode1));

        //Before Grading Test
        assertEquals(0.0, currentUser.getGPA());

        //After Grading Test
        Command gradeCommand1 = new GradeModule(currentUser, moduleCode1, grade1);
        gradeCommand1.execute();
        assertEquals(gpa1, currentUser.getGPA());

        //Multiple Grades Test
        Command addModule2 = new AddUserModule(currentUser, moduleCode2, semester2);
        addModule2.execute();
        assertTrue(currentUser.hasModule(moduleCode2));

        Command gradeCommand2 = new GradeModule(currentUser, moduleCode2, grade2);
        gradeCommand2.execute();
        assertEquals(expected_combinedGPA, currentUser.getGPA());
    }

    public void testSuGPA() {
        //SUed
        String moduleCode1 = "CG2028"; //2 MC
        int semester1 = 3;
        String grade1 = "B-";

        //Not SUed
        String grade2 = "D";
        String moduleCode2 = "CS1010"; //4 MC
        int semester2 = 3;

        double expectedCombinedGPA = 1.0;

        Command addModule1 = new AddUserModule(currentUser, moduleCode1, semester1);
        addModule1.execute();
        assertTrue(currentUser.hasModule(moduleCode1));

        Command suModule1 = new SuUserModule(currentUser, moduleCode1);
        suModule1.execute();

        //only SU
        assertEquals(0.0, currentUser.getGPA());

        //SU and grade 1 Mod
        Command gradeCommand1 = new GradeModule(currentUser, moduleCode1, grade1);
        gradeCommand1.execute();
        assertEquals(0.0, currentUser.getGPA());

        //1 SU Mod, 1 Mod with Grade
        Command addModule2 = new AddUserModule(currentUser, moduleCode2, semester2);
        addModule2.execute();
        assertTrue(currentUser.hasModule(moduleCode2));

        Command gradeCommand2 = new GradeModule(currentUser, moduleCode2, grade2);
        gradeCommand2.execute();
        assertEquals(expectedCombinedGPA, currentUser.getGPA());

    }

}
