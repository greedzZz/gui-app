package common.commands;

import common.Holder;
import common.User;
import javafx.util.Pair;

public class RemoveGreaterKey extends Command {
    public RemoveGreaterKey(User user, Integer intArg) {
        super(user);
        this.intArg = intArg;
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.removeGreaterKey(intArg, user.getLogin());
    }
}
