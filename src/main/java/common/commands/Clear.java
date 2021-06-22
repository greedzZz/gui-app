package common.commands;

import common.Holder;
import common.User;

public class Clear extends Command {
    public Clear(User user) {
        super(user);
    }

    @Override
    public String execute(Holder cm) {
        return cm.clear(user.getLogin());
    }
}
