package common.commands;

import common.Holder;
import common.User;
import javafx.util.Pair;

public class Clear extends Command {
    public Clear(User user) {
        super(user);
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.clear(user.getLogin());
    }
}
