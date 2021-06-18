package common.commands;

import common.Holder;

public class Info extends Command {
    public Info(boolean newbie, String login, String password) {
        super(newbie, login, password);
    }

    @Override
    public String execute(Holder cm) {
        return cm.info();
    }
}
