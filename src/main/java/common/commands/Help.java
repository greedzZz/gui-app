package common.commands;

import common.Holder;
import common.User;
import javafx.util.Pair;

public class Help extends Command {
    public Help(User user) {
        super(user);
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.help();
    }
}
