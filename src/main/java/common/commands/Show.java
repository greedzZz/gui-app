package common.commands;

import common.Holder;
import common.User;

public class Show extends Command {
    public Show(User user) {
        super(user);
    }

    @Override
    public String execute(Holder cm) {
        return null;
    }

}
