package common.commands;

import common.Holder;
import common.User;
import common.content.SpaceMarine;

public class RemoveGreater extends Command {
    public RemoveGreater(User user, SpaceMarine smArg) {
        super(user);
        this.smArg = smArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.removeGreater(smArg, user.getLogin());
    }
}
