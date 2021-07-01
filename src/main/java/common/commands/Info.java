package common.commands;

import common.Holder;
import common.User;
import javafx.util.Pair;

public class Info extends Command {
    public Info(User user) {
        super(user);
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.info();
    }
}
