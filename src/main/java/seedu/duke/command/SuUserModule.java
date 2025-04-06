package seedu.duke.command;

import seedu.duke.user.User;
import seedu.duke.data.UserMod;

public class SuUserModule implements Command {
    private final User user;
    private final String moduleCode;

    public SuUserModule(User user, String moduleCode) {
        this.user = user;
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute() {
        UserMod module = user.getModule(moduleCode);
        if (module == null) {
            System.out.println("Failed to SU " + moduleCode.toUpperCase() + ". It may not exist");
            return;
        }

        if (module.getGrade() == null){
            System.out.println("Failed to SU " + moduleCode.toUpperCase() + "." +
                    " It has not been initialised with a grade yet");
            return;
        }

        module.toggleSU();

        if (module.isSU()){
            System.out.println("Module " + moduleCode.toUpperCase() + " S/U status: true");
        } else {
            System.out.println("Module " + moduleCode.toUpperCase() + " S/U status: false");
        }
    }
}

