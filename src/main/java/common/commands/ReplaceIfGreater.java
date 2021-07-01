package common.commands;

import common.Holder;
import common.User;
import common.content.SpaceMarine;
import javafx.util.Pair;

public class ReplaceIfGreater extends Command {
    public ReplaceIfGreater(User user, Integer intArg, SpaceMarine smArg) {
        super(user);
        this.intArg = intArg;
        this.smArg = smArg;
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.replaceIfGreater(intArg, smArg, user.getLogin());
    }
}
