package seedu.duke.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.duke.data.Mod;
import seedu.duke.errors.ModNotInDatabase;

public class GradModuleList {

    public static final List<Mod> YEAR1SEM1MODULES;
    public static final List<Mod> YEAR1SEM2MODULES;
    public static final List<Mod> YEAR2SEM1MODULES;
    public static final List<Mod> YEAR2SEM2MODULES;
    public static final List<Mod> YEAR3SEM1MODULES;
    public static final List<Mod> YEAR3SEM2MODULES;
    public static final List<Mod> YEAR4SEM1MODULES;
    public static final List<Mod> YEAR4SEM2MODULES = new ArrayList<>(Arrays.asList(
            generateUE(), generateUE(), generateUE(), generateUE(), generateUE()
    ));

    static {
        try {
            YEAR1SEM1MODULES = new ArrayList<>(Arrays.asList(
                    ModStorage.findModuleByCode("CG1111A"),
                    ModStorage.findModuleByCode("CS1010"),
                    ModStorage.findModuleByCode("EG1311"),
                    ModStorage.findModuleByCode("MA1511"),
                    ModStorage.findModuleByCode("MA1512"),
                    new Mod("English for Academic Purposes",
                            "ES1103 serves as a bridging course for" +
                            " students who have taken the university’s" +
                            " Qualifying English Test and are deemed to require" +
                            " additional language support for the academic context." +
                            " It aims to equip students with the" +
                            " knowledge of the academic genre and" +
                            " the ability to apply such knowledge in academic" +
                            " communication. The course adopts a" +
                            " reading-into-writing approach using themed readings" +
                            " as springboard texts for students’" +
                            " writing and provides opportunities for analysing and" +
                            " internalising ways of organising academic texts." +
                            " Students will acquire essential academic" +
                            " skills required to cope with the rigour" +
                            " of academic writing at a tertiary level.",
                            4, "ES1103", 0, 4, 0, 3, 3,
                            "If undertaking an Undergraduate Degree THEN (( EP ENGLISH LANGUAGE" +
                            " PROFICIENCY TEST at a grade of at least N)" +
                            " AND must not have completed 1 of AR1000, BE1000," +
                            " EA1101, EG1471, ES1101, ES1102, ES1301, ET1000, ID1000, NK1001)",
                            "If undertaking" +
                            " an Undergraduate Degree THEN ( must have completed" +
                            " EP ENGLISH LANGUAGE PROFICIENCY TEST at a" +
                            " grade of at least Y OR must have completed " +
                            "ES1000 at a grade of at least D)", null)
            ));

            YEAR1SEM2MODULES = new ArrayList<>(Arrays.asList(
                    ModStorage.findModuleByCode("CG2111A"),
                    ModStorage.findModuleByCode("DTK1234"),
                    ModStorage.findModuleByCode("MA1508E"),
                    ModStorage.findModuleByCode("PF1101"),
                    ModStorage.findModuleByCode("GEA1000")
            ));

            YEAR2SEM1MODULES = new ArrayList<>(Arrays.asList(
                    ModStorage.findModuleByCode("CS1231"),
                    ModStorage.findModuleByCode("CS2040C"),
                    ModStorage.findModuleByCode("IE2141"),
                    ModStorage.findModuleByCode("ES2631"),
                    new Mod("Cultures and Connections", "", 4, "GEC1XXX",
                            0, 0, 0, 0, 0,
                            "Unconfirmed preclusions", "Unconfirmed prerequisites",
                            null)
            ));

            YEAR2SEM2MODULES = new ArrayList<>(Arrays.asList(
                    ModStorage.findModuleByCode("CG2023"),
                    ModStorage.findModuleByCode("CS2113"),
                    ModStorage.findModuleByCode("EE2211"),
                    ModStorage.findModuleByCode("EE2026"),
                    ModStorage.findModuleByCode("CDE2501")
            ));

            YEAR3SEM1MODULES = new ArrayList<>(Arrays.asList(
                    ModStorage.findModuleByCode("EG2401A"),
                    ModStorage.findModuleByCode("EG3611A"),
                    ModStorage.findModuleByCode("CP3880"),
                    generateUE()
            ));

            YEAR3SEM2MODULES = new ArrayList<>(Arrays.asList(
                    ModStorage.findModuleByCode("CG2027"),
                    ModStorage.findModuleByCode("CG2028"),
                    ModStorage.findModuleByCode("CG2271"),
                    ModStorage.findModuleByCode("CDE2000"),
                    ModStorage.findModuleByCode("ST2334")
            ));

            YEAR4SEM1MODULES = new ArrayList<>(Arrays.asList(
                    ModStorage.findModuleByCode("CG4002"),
                    ModStorage.findModuleByCode("EE4204"),
                    new Mod("Communities and Engagement", "", 4, "GEN1XXX",
                            0, 0, 0, 0, 0,
                            "Unconfirmed preclusions", "Unconfirmed prerequisites",
                            null),
                    generateUE()
            ));
        } catch (ModNotInDatabase e) {
            System.out.println("Fail to load Grad Module List");
            throw new RuntimeException(e);
        }
    }

    private static Mod generateUE() {
        return new Mod("Unrestricted Elective", "", 4, "UE",
                0, 0, 0, 0, 0,
                "Unconfirmed preclusions", "Unconfirmed prerequisites", null);
    }

}
