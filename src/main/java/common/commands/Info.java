package common.commands;

import common.Holder;
import common.User;

public class Info extends Command {
    public Info(User user) {
        super(user);
    }

    @Override
    public String execute(Holder cm) {
        return cm.info();
    }
}
