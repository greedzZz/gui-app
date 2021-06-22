package common.commands;

import common.Holder;
import common.User;

public class GroupCountingByCoordinates extends Command {
    public GroupCountingByCoordinates(User user) {
        super(user);
    }

    @Override
    public String execute(Holder cm) {
        return cm.groupCountingByCoordinates();
    }
}
