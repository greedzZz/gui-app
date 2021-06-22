package common.commands;

import common.Holder;
import common.User;
import common.content.SpaceMarine;

public class Insert extends Command {
    public Insert(User user, Integer intArg, SpaceMarine smArg) {
        super(user);
        this.intArg = intArg;
        this.smArg = smArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.insert(intArg, smArg, user.getLogin());
    }
}
