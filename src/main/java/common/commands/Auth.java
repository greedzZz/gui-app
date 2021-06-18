package common.commands;

import common.Holder;

public class Auth extends Command {
    public Auth(boolean newbie, String login, String password) {
        super(newbie, login, password);
    }

    @Override
    public String execute(Holder cm) {
        return null;
    }

}
