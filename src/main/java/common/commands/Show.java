package common.commands;

import common.Holder;
import common.User;
import javafx.util.Pair;

public class Show extends Command {
    public Show(User user) {
        super(user);
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return null;
    }

}
