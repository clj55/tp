package seedu.duke.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.duke.data.Grade;
import seedu.duke.data.Prereq;
import seedu.duke.data.UserMod;

public class User {
    private String name;
    private EducationLevel education;
    private double gpa;
    private int currentSemester;
    private Map<Integer, ArrayList<UserMod>> semesterModules;

    public User() {
        // Default constructor initializes with empty values
        this.name = "";
        this.education = null;
        this.gpa = 0.0;
        this.currentSemester = 1;
        this.semesterModules = new HashMap<>();

    }

    public User(String name, EducationLevel education) {
        this.name = name;
        this.education = education;
        this.gpa = 0.0;
        this.currentSemester = 1;
        this.semesterModules = new HashMap<>();
    }

    public Map<Integer, ArrayList<UserMod>> getSemesterModules() {
        return semesterModules;
    }

    public void setSemesterModules(Map<Integer, ArrayList<UserMod>> semesterModules) {
        this.semesterModules = semesterModules;
    }

    public double getGPA() {
        updateGPA();
        return gpa;
    }

    public int getTotalMCs() {
        int totalMCs = 0;
        for (ArrayList<UserMod> mods : semesterModules.values()) {
            for (UserMod mod : mods) {
                int modMC = mod.getNumMC();
                totalMCs += modMC;
            }
        }
        return totalMCs;
    }

    public int getGradedMCs() {
        int totalMCs = 0;
        for (ArrayList<UserMod> mods : semesterModules.values()) {
            for (UserMod mod : mods) {
                if (mod.getGrade() != null && mod.isSU() == false) {
                    int modMC = mod.getNumMC();
                    totalMCs += modMC;
                }
            }
        }
        return totalMCs;
    }

    public void updateGPA() {
        int totalMCs = getGradedMCs();
        if (totalMCs == 0) {
            this.gpa = 0.0;
            return;
        }

        double totalGradePoints = 0;
        for (ArrayList<UserMod> mods : semesterModules.values()) {
            for (UserMod mod : mods) {
                Grade moduleGrade = mod.getGrade();
                int modMC = mod.getNumMC();

                if (moduleGrade != null && !mod.isSU()) {
                    totalGradePoints += moduleGrade.getGradePoint() * modMC;
                }
            }
        }

        //calculates GPA to 2dp
        this.gpa = Math.floor(totalGradePoints / totalMCs * 100) / 100;
    }


    public boolean hasModule(String code) {
        return getModule(code) != null;
    }

    public void clearModules() {
        semesterModules.clear();
        updateGPA(); // Reset GPA after clearing modules
    }

    public UserMod getModule(String code) {
        for (ArrayList<UserMod> mods : semesterModules.values()) {
            for (UserMod mod : mods) {
                if (mod.getCode() != null && mod.getCode().equalsIgnoreCase(code)) {
                    return mod;
                }
            }
        }
        return null;
    }


    public void checkAllPrereqs() {
        int semester = 0;
        StringBuilder missingMods = new StringBuilder();
        for (ArrayList<UserMod> mods : semesterModules.values()) {
            for (UserMod mod : mods) {
                if (!fulfillsModPrereq(mod, semester)) {
                    missingMods.append(mod.getCode()).append(", ");
                }
            }
            semester++;
        }
        if (missingMods.length() == 0) {
            return;
        }
        assert missingMods.charAt(missingMods.length() - 1) == ' '
                && missingMods.charAt(missingMods.length() - 2) == ',';

        missingMods.deleteCharAt(missingMods.length() - 2); //remove last comma
        missingMods.append("missing prerequisites");
        System.out.println(missingMods);
    }

    public boolean fulfillsModPrereq(UserMod mod, int semester) {
        Prereq prereqTree = mod.getPrereqTree();
        if (prereqTree == null) {
            return true;
        }
        return prereqTree.fulfillsPrereq(getAllModulesTilSemester(semester));
    }


    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EducationLevel getEducation() {
        return education;
    }

    public void setEducation(EducationLevel education) {
        this.education = education;
    }

    public ArrayList<UserMod> getAllModules() {
        return semesterModules.values()
                .stream()
                .flatMap(ArrayList::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<UserMod> getAllModulesTilSemester(int semester) {
        return semesterModules.values()
                .stream()
                .limit(semester)
                .flatMap(ArrayList::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

