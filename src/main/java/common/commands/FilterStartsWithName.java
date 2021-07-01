package common.commands;

import common.Holder;
import common.User;
import javafx.util.Pair;

public class FilterStartsWithName extends Command {
    public FilterStartsWithName(User user, String strArg) {
        super(user);
        this.strArg = strArg;
    }

    @Override
    public Pair<String, Boolean> execute(Holder cm) {
        return cm.filterStartsWithName(strArg);
    }
}
