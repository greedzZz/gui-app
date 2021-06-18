package common.commands;

import common.Holder;
import common.content.SpaceMarine;

public class RemoveGreater extends Command {
    public RemoveGreater(boolean newbie, String login, String password, SpaceMarine smArg) {
        super(newbie, login, password);
        this.smArg = smArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.removeGreater(smArg, user.getLogin());
    }
}
