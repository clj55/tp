package seedu.duke.data;

import seedu.duke.errors.ModNotInDatabase;
import seedu.duke.storage.ModStorage;

public class Mod {
    private String name = null;
    private String description = null;
    private int numMC = 0;
    private String code = null;
    private double lectureHours = 0;
    private double tutHours = 0;
    private double labHours = 0;
    private double projHours = 0;
    private double prepHours = 0;
    private String preclusion = null;
    private String prerequisites = null;
    private Prereq prereqTree = null;

    public Mod(String name, String description, int numMC, String code,
               double lectureHours, double tutHours, double labHours, double projHours, double prepHours,
               String preclusion, String prerequisites, Prereq prereqTree) {
        this.name = name;
        this.description = description;
        this.numMC = numMC;
        this.code = code;
        this.lectureHours = lectureHours;
        this.tutHours = tutHours;
        this.labHours = labHours;
        this.projHours = projHours;
        this.prepHours = prepHours;
        this.preclusion = preclusion;
        this.prerequisites = prerequisites;
        this.prereqTree = prereqTree;
    }

    public Mod(String code, int numMC, String name) {
        this.code = code;
        this.numMC = numMC;
        this.name = name;
        this.description = "";
        this.lectureHours = 0;
        this.tutHours = 0;
        this.labHours = 0;
        this.projHours = 0;
        this.prepHours = 0;
        this.preclusion = "";
        this.prerequisites = "";
        this.prereqTree = null;
    }

    public Mod(String code) throws ModNotInDatabase {
        Mod foundMod = ModStorage.findModuleByCode(code.toUpperCase());
        this.code = foundMod.getCode();
        this.description = foundMod.getDescription();
        this.name = foundMod.getName();
        this.numMC = foundMod.getNumMC();
        this.lectureHours = foundMod.getLectureHours();
        this.tutHours = foundMod.getTutHours();
        this.labHours = foundMod.getLabHours();
        this.projHours = foundMod.getProjHours();
        this.prepHours = foundMod.getPrepHours();
        this.preclusion = foundMod.getPreclusion();
        this.prerequisites = foundMod.getPrerequisites();
        this.prereqTree = foundMod.getPrereqTree();

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumMC() {
        return numMC;
    }

    public String getCode() {
        return code;
    }

    public double getLectureHours() {
        return lectureHours;
    }

    public double getTutHours() {
        return tutHours;
    }

    public double getLabHours() {
        return labHours;
    }

    public double getProjHours() {
        return projHours;
    }

    public double getPrepHours() {
        return prepHours;
    }

    public String getPreclusion() {
        return preclusion;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public Prereq getPrereqTree() {
        return prereqTree;
    }

    public String toString() {
        return code + " | " + name + " | " + "(" + numMC + " MCs)";
    }
}
