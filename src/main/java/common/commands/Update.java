package common.commands;

import common.Holder;
import common.User;
import common.content.SpaceMarine;
import javafx.util.Pair;

public class Update extends Command {
    public Update(User user, Integer intArg, SpaceMarine smArg) {
        super(user);
        this.intArg = intArg;
        this.smArg = smArg;
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.update(intArg, smArg, user.getLogin());
    }
}
