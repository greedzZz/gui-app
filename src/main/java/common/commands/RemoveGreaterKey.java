package common.commands;

import common.Holder;

public class RemoveGreaterKey extends Command {
    public RemoveGreaterKey(boolean newbie, String login, String password, Integer intArg) {
        super(newbie, login, password);
        this.intArg = intArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.removeGreaterKey(intArg, user.getLogin());
    }
}
