package common.commands;

import common.Holder;

public class GroupCountingByCoordinates extends Command {
    public GroupCountingByCoordinates(boolean newbie, String login, String password) {
        super(newbie, login, password);
    }

    @Override
    public String execute(Holder cm) {
        return cm.groupCountingByCoordinates();
    }
}
