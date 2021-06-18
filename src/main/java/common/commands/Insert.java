package common.commands;

import common.Holder;
import common.content.SpaceMarine;

public class Insert extends Command {
    public Insert(boolean newbie, String login, String password, Integer intArg, SpaceMarine smArg) {
        super(newbie, login, password);
        this.intArg = intArg;
        this.smArg = smArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.insert(intArg, smArg, user.getLogin());
    }
}