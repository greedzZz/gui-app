package common.commands;

import common.Holder;

public class Clear extends Command {
    public Clear(boolean newbie, String login, String password) {
        super(newbie, login, password);
    }

    @Override
    public String execute(Holder cm) {
        return cm.clear(user.getLogin());
    }
}
