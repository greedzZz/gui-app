package common;

import common.content.Chapter;
import common.content.SpaceMarine;

public interface Holder {
    String help();
    String info();
    String insert(Integer key, SpaceMarine sm, String login);
    String update(Integer id, SpaceMarine sm, String login);
    String removeKey(Integer key, String login);
    String clear(String login);
    String removeGreater(SpaceMarine sm, String login);
    String replaceIfGreater(Integer key, SpaceMarine sm, String login);
    String removeGreaterKey(Integer key, String login);
    String groupCountingByCoordinates();
    String filterByChapter(Chapter chapter);
    String filterStartsWithName(String name);
}
