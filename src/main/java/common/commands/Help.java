package common.commands;

import common.Holder;

public class Help extends Command {
    public Help(boolean newbie, String login, String password) {
        super(newbie, login, password);
    }

    @Override
    public String execute(Holder cm) {
        return cm.help();
    }
}
