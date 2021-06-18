package common.commands;

import common.Holder;
import common.content.Chapter;

public class FilterByChapter extends Command {
    public FilterByChapter(boolean newbie, String login, String password, Chapter chapArg) {
        super(newbie, login, password);
        this.chapArg = chapArg;
    }

    @Override
    public String execute(Holder cm) {
        return cm.filterByChapter(chapArg);
    }
}
