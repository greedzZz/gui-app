package common.commands;

import common.Holder;
import common.User;

public class Help extends Command {
    public Help(User user) {
        super(user);
    }

    @Override
    public String execute(Holder cm) {
        return cm.help();
    }
}
