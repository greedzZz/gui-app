package common.commands;

import common.Holder;

public class RemoveKey extends Command {
    public RemoveKey(boolean newbie, String login, String password, Integer intArg) {
        super(newbie, login, password);
        this.intArg = intArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.removeKey(intArg, user.getLogin());
    }
}
