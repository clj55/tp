package seedu.duke.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.duke.Duke.currentUser;


public class AddCustomModuleTest {

    @BeforeEach
    public void setUp() {
        currentUser.clearModules(); // Clear the moduleList
    }

    @Test
    public void testAddCustomModule() {
        int testSemester1 = 3;
        int testSemester2 = 8;
        int testSemester3 = 2;
        int testMC1 = 4;
        int testMC2 = 6;
        int testMC3 = 2;
        String testModule1 = "PL1101E";
        String testModule2 = "FAS1101";
        String testModule3 = "HS2902";
        String testName1 = "Intro to Psychology";
        String testName2 = "Fundamentals of Writing";
        String testName3 = "Do Play Play";

        Command testCommand1 = new AddCustomModule(currentUser, testModule1, testSemester1, testMC1, testName1);
        testCommand1.execute();
        Command testCommand2 = new AddCustomModule(currentUser, testModule2, testSemester2, testMC2, testName2);
        testCommand2.execute();
        Command testCommand3 = new AddCustomModule(currentUser, testModule3, testSemester3, testMC3, testName3);
        testCommand3.execute();

        assertTrue(currentUser.hasModule(testModule1));
        assertTrue(currentUser.hasModule(testModule2));
        assertTrue(currentUser.hasModule(testModule3));
    }

    @Test public void testAddCustomModulePrint() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Command test = new AddCustomModule(currentUser, "PL1101E", 3, 4,"intro to psych");
        test.execute();
        Command test1 = new AddCustomModule(currentUser, "PL1101E", 3, 4,"intro");
        test1.execute();

        System.setOut(System.out);
        String output = out.toString().trim();
        assertTrue(output.contains("Failed to add module PL1101E. It already exists."));
    }

    @Test public void testAddCustomModuleFails() {
        int testSemester1 = 0;
        int testSemester2 = 10;
        int testMC1 = 4;
        int testMC2 = 6;
        String testModule1 = "PL3103";
        String testModule2 = "GEI1000";
        String testName1 = "Intro to Psychology";
        String testName2 = "Cell-based proteins";



        Command testCommand1 = new AddCustomModule(currentUser, testModule1, testSemester1, testMC1, testName1);
        testCommand1.execute();
        Command testCommand2 = new AddCustomModule(currentUser, testModule2, testSemester2, testMC2, testName2);
        testCommand2.execute();

        assertFalse(currentUser.hasModule(testModule1));
        assertFalse(currentUser.hasModule(testModule2));
        assertTrue(currentUser.getAllModules().isEmpty());
    }
}
