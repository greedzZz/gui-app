package common.commands;

import common.Holder;
import common.User;

public class FilterStartsWithName extends Command {
    public FilterStartsWithName(User user, String strArg) {
        super(user);
        this.strArg = strArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.filterStartsWithName(strArg);
    }
}
