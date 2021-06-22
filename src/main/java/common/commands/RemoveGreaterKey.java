package common.commands;

import common.Holder;
import common.User;

public class RemoveGreaterKey extends Command {
    public RemoveGreaterKey(User user, Integer intArg) {
        super(user);
        this.intArg = intArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.removeGreaterKey(intArg, user.getLogin());
    }
}
