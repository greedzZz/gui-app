package common;

import common.content.Chapter;
import common.content.SpaceMarine;
import javafx.util.Pair;

public interface Holder {
    Pair<String, Boolean> help();
    Pair<String, Boolean> info();
    Pair<String, Boolean> insert(Integer key, SpaceMarine sm, String login);
    Pair<String, Boolean> update(Integer id, SpaceMarine sm, String login);
    Pair<String, Boolean> removeKey(Integer key, String login);
    Pair<String, Boolean> clear(String login);
    Pair<String, Boolean> removeGreater(SpaceMarine sm, String login);
    Pair<String, Boolean> replaceIfGreater(Integer key, SpaceMarine sm, String login);
    Pair<String, Boolean> removeGreaterKey(Integer key, String login);
    Pair<String, Boolean> groupCountingByCoordinates();
    Pair<String, Boolean> filterByChapter(Chapter chapter);
    Pair<String, Boolean> filterStartsWithName(String name);
}
