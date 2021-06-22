package common.commands;

import common.Holder;
import common.User;
import common.content.Chapter;
import common.content.SpaceMarine;

import java.io.Serializable;

public abstract class Command implements Serializable {
    protected String strArg;
    protected Integer intArg;
    protected SpaceMarine smArg;
    protected Chapter chapArg;
    protected User user;

    public Command(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public abstract String execute(Holder cm);
}
