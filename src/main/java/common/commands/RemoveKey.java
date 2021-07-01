package common.commands;

import common.Holder;
import common.User;
import javafx.util.Pair;

public class RemoveKey extends Command {
    public RemoveKey(User user, Integer intArg) {
        super(user);
        this.intArg = intArg;
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.removeKey(intArg, user.getLogin());
    }
}
