package common.commands;

import common.Holder;
import common.User;

public class RemoveKey extends Command {
    public RemoveKey(User user, Integer intArg) {
        super(user);
        this.intArg = intArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.removeKey(intArg, user.getLogin());
    }
}
