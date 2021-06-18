package common.commands;

import common.Holder;

public class FilterStartsWithName extends Command {
    public FilterStartsWithName(boolean newbie, String login, String password, String strArg) {
        super(newbie, login, password);
        this.strArg = strArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.filterStartsWithName(strArg);
    }
}
