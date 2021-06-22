package common.commands;

import common.Holder;
import common.User;

public class Auth extends Command {
    public Auth(User user) {
        super(user);
    }

    @Override
    public String execute(Holder cm) {
        return null;
    }

}
