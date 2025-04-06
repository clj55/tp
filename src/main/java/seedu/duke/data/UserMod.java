package seedu.duke.data;

import seedu.duke.errors.ModNotInDatabase;

public class UserMod extends Mod {
    private Grade grade;
    private boolean su;
    private final boolean isCustom;


    public UserMod(String name, String description, int numMC, String code, double lectureHours,
                   double tutHours,
                   double labHours, double projHours, double prepHours,
                   String preclusion, String prerequisites, Prereq prereqTree) {
        super(name, description, numMC, code, lectureHours, tutHours, labHours, projHours, prepHours,
                preclusion, prerequisites, prereqTree);
        this.grade = null;
        this.su = false;
        this.isCustom = false;
    }

    public UserMod(String code, int numMC, String name) { // for custom modules
        super(code, numMC, name);
        this.grade = null;
        this.su = false;
        this.isCustom = true;
    }

    public UserMod(String code, Grade grade, boolean su) throws ModNotInDatabase {
        super(code);
        this.grade = grade;
        this.su = su;
        this.isCustom = false;
    }

    public UserMod(String code) throws ModNotInDatabase {
        super(code);
        this.grade = null;
        this.su = false;
        this.isCustom = false;
    }


    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public boolean isSU() {
        return su;
    }

    public void setSU(boolean su) {
        this.su = su;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void print() {
        if (grade != null) {
            System.out.println(super.toString() + " | Grade: " + grade.getLabel() + " | SU: " + su + " |");

        } else {
            System.out.println(super.toString() + " | Grade: " + "null" + " | SU: " + su + " |");
        }
    }

    public void toggleSU() {
        this.su = !this.su;
    }
}

