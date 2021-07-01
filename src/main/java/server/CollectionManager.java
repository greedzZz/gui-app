package server;

import common.Holder;
import common.content.Chapter;
import common.content.SpaceMarine;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CollectionManager implements Holder {
    private final TreeMap<Integer, SpaceMarine> treeMap;
    private final Date date;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private final ReentrantLock lock;

    public CollectionManager() {
        this.date = new Date();
        this.treeMap = new TreeMap<>();
        this.lock = new ReentrantLock();
    }

    public Pair<String, Boolean> help() {
        return new Pair<>(null, true);
    }

    public Pair<String, Boolean> info() {
        return new Pair<>(treeMap.getClass() + "_" + date + "_" + treeMap.size(), true);
    }

    public Pair<String, Boolean> insert(Integer key, SpaceMarine sm, String login) {
        lock.lock();
        try {
            sm.setID(key);
            sm.setCreationDate();
            sm.setOwner(login);
            String sqlCommand;
            if (treeMap.containsKey(key)) {
                if (checkUser(key, login)) {
                    updateDataBase(sm, key, login);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    put(sm);
                    return new Pair<>(null, true);
                } else {
                    return new Pair<>(null, false);
                }
            } else {
                sqlCommand = "INSERT INTO marines VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
                preparedStatement = connection.prepareStatement(sqlCommand);

                preparedStatement.setString(1, login);
                preparedStatement.setInt(2, key);
                preparedStatement.setString(3, sm.getName());
                preparedStatement.setInt(4, sm.getCoordinateX());
                preparedStatement.setInt(5, sm.getCoordinateY());
                preparedStatement.setString(6, sm.getCreationDate());

                if (sm.getHealth() == null) {
                    preparedStatement.setNull(7, Types.INTEGER);
                } else {
                    preparedStatement.setInt(7, sm.getHealth());
                }

                if (sm.getCategory() == null) {
                    preparedStatement.setNull(8, Types.VARCHAR);
                } else {
                    preparedStatement.setString(8, sm.getCategory().toString());
                }

                if (sm.getWeaponType() == null) {
                    preparedStatement.setNull(9, Types.VARCHAR);
                } else {
                    preparedStatement.setString(9, sm.getWeaponType().toString());
                }

                if (sm.getMeleeWeapon() == null) {
                    preparedStatement.setNull(10, Types.VARCHAR);
                } else {
                    preparedStatement.setString(10, sm.getMeleeWeapon().toString());
                }

                preparedStatement.setString(11, sm.getChapterName());
                preparedStatement.setString(12, sm.getChapterWorld());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                put(sm);
                return new Pair<>(null, true);
            }
        } catch (SQLException e) {
            return new Pair<>(null, false);
        } finally {
            lock.unlock();
        }
    }

    public Pair<String, Boolean> update(Integer id, SpaceMarine sm, String login) {
        lock.lock();
        try {
            if (!treeMap.containsKey(id)) {
                throw new Exception("There is no element with such id in the collection.");
            } else {
                sm.setID(id);
                sm.setCreationDate();
                sm.setOwner(login);
                if (checkUser(id, login)) {
                    updateDataBase(sm, id, login);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    put(sm);
                    return new Pair<>(null, true);
                } else {
                    return new Pair<>(null, false);
                }
            }
        } catch (Exception s) {
            return new Pair<>(null, false);
        } finally {
            lock.unlock();
        }
    }

    public Pair<String, Boolean> removeKey(Integer key, String login) {
        lock.lock();
        try {
            if (!treeMap.containsKey(key)) {
                throw new Exception("There is no such argument in the collection.");
            } else {
                if (checkUser(key, login)) {
                    String sqlCommand = "DELETE FROM marines WHERE id=?;";
                    preparedStatement = connection.prepareStatement(sqlCommand);
                    preparedStatement.setInt(1, key);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    treeMap.remove(key);
                    return new Pair<>(null, true);
                } else {
                    return new Pair<>(null, false);
                }
            }
        } catch (Exception e) {
            return new Pair<>(null, false);
        } finally {
            lock.unlock();
        }
    }

    public Pair<String, Boolean> clear(String login) {
        lock.lock();
        try {
            connection.setAutoCommit(false);
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                Iterator<SpaceMarine> iteratorDB = treeMap.values().iterator();
                String sqlCommand;
                while (iteratorDB.hasNext()) {
                    SpaceMarine next = iteratorDB.next();
                    if (checkUser(next.getID(), login)) {
                        sqlCommand = "DELETE FROM marines WHERE id=?;";
                        preparedStatement = connection.prepareStatement(sqlCommand);
                        preparedStatement.setInt(1, next.getID());
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    }
                }
                connection.commit();
                treeMap.values().removeIf(next -> next.getOwner().equals(login));
                return new Pair<>(null, true);
            }
        } catch (Exception e) {
            return new Pair<>(null, false);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
            lock.unlock();
        }
    }

    public Pair<String, Boolean> removeGreater(SpaceMarine sm, String login) {
        lock.lock();
        try {
            connection.setAutoCommit(false);
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                Iterator<SpaceMarine> iterator = treeMap.values().iterator();
                String sqlCommand;
                while (iterator.hasNext()) {
                    SpaceMarine next = iterator.next();
                    if (sm.compareTo(next) < 0) {
                        if (checkUser(next.getID(), login)) {
                            sqlCommand = "DELETE FROM marines WHERE id=?;";
                            preparedStatement = connection.prepareStatement(sqlCommand);
                            preparedStatement.setInt(1, next.getID());
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                        }
                    }
                }
                connection.commit();
                iterator = treeMap.values().iterator();
                while (iterator.hasNext()) {
                    SpaceMarine next = iterator.next();
                    if (sm.compareTo(next) < 0) {
                        if (next.getOwner().equals(login)) {
                            iterator.remove();
                        }
                    }
                }
                return new Pair<>(null, true);
            }
        } catch (Exception e) {
            return new Pair<>(null, false);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
            lock.unlock();
        }
    }

    public Pair<String, Boolean> replaceIfGreater(Integer key, SpaceMarine sm, String login) {
        lock.lock();
        try {
            if (!treeMap.containsKey(key)) {
                throw new Exception("There is no such argument in the collection.");
            } else {
                if (sm.compareTo(treeMap.get(key)) > 0) {
                    try {
                        sm.setID(key);
                        sm.setCreationDate();
                        sm.setOwner(login);
                        if (checkUser(key, login)) {
                            updateDataBase(sm, key, login);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                            treeMap.put(sm.getID(), sm);
                            return new Pair<>(null, true);
                        } else {
                            return new Pair<>(null, false);
                        }
                    } catch (SQLException s) {
                        return new Pair<>(null, false);
                    }
                } else {
                    return new Pair<>(null, false);
                }
            }
        } catch (Exception e) {
            return new Pair<>(null, false);
        } finally {
            lock.unlock();
        }
    }

    public Pair<String, Boolean> removeGreaterKey(Integer key, String login) {
        lock.lock();
        try {
            connection.setAutoCommit(false);
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                Iterator<SpaceMarine> iterator = treeMap.values().iterator();
                String sqlCommand;
                while (iterator.hasNext()) {
                    SpaceMarine next = iterator.next();
                    int currentKey = next.getID();
                    if (currentKey > key) {
                        if (checkUser(next.getID(), login)) {
                            sqlCommand = "DELETE FROM marines WHERE id=?;";
                            preparedStatement = connection.prepareStatement(sqlCommand);
                            preparedStatement.setInt(1, next.getID());
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                        }
                    }
                }
                connection.commit();
                iterator = treeMap.values().iterator();
                while (iterator.hasNext()) {
                    SpaceMarine next = iterator.next();
                    int currentKey = next.getID();
                    if (currentKey > key) {
                        if (next.getOwner().equals(login)) {
                            iterator.remove();
                        }
                    }
                }
                return new Pair<>(null, true);
            }
        } catch (Exception e) {
            return new Pair<>(null, false);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
            lock.unlock();
        }
    }

    public Pair<String, Boolean> groupCountingByCoordinates() {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            } else {
                long first = treeMap.values().stream()
                        .filter(sm -> sm.getCoordinateX() >= 0)
                        .filter(sm -> sm.getCoordinateY() >= 0)
                        .count();
                long second = treeMap.values().stream()
                        .filter(sm -> sm.getCoordinateX() < 0)
                        .filter(sm -> sm.getCoordinateY() >= 0)
                        .count();
                long third = treeMap.values().stream()
                        .filter(sm -> sm.getCoordinateX() < 0)
                        .filter(sm -> sm.getCoordinateY() < 0)
                        .count();
                long fourth = treeMap.values().stream()
                        .filter(sm -> sm.getCoordinateX() >= 0)
                        .filter(sm -> sm.getCoordinateY() < 0)
                        .count();
                return new Pair<>(first + " " + second + " " + third + " " + fourth, true);
            }
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<String, Boolean> filterByChapter(Chapter chapter) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            }
            String chapterName = chapter.getName();
            String chapterWorld = chapter.getWorld();
            String marines = treeMap.values().stream()
                    .filter(sm -> sm.getChapterName().equals(chapterName) && sm.getChapterWorld().equals(chapterWorld))
                    .map(sm -> sm.getID() + "\n")
                    .collect(Collectors.joining());
            if (marines.length() > 1) {
                return new Pair<>(marines, true);
            } else {
                return new Pair<>("", true);
            }
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    public Pair<String, Boolean> filterStartsWithName(String name) {
        try {
            if (treeMap.isEmpty()) {
                throw new Exception("The collection is empty.");
            }
            String marines = treeMap.values().stream()
                    .filter(sm -> sm.getName().startsWith(name))
                    .map(sm -> sm.getID() + "\n")
                    .collect(Collectors.joining());
            if (marines.length() > 1) {
                return new Pair<>(marines, true);
            } else {
                return new Pair<>("", true);
            }
        } catch (Exception e) {
            return new Pair<>(null, false);
        }
    }

    private void updateDataBase(SpaceMarine sm, Integer key, String login) throws SQLException {
        String sqlCommand = "UPDATE marines SET owner=?, name=?, coordinate_x=?, coordinate_y=?," +
                " creation_date=?, health=?, astartes_category=?," +
                " weapon=?, melee_weapon=?, chapter_name=?, chapter_world=? WHERE id=?;";
        preparedStatement = connection.prepareStatement(sqlCommand);

        preparedStatement.setString(1, login);
        preparedStatement.setString(2, sm.getName());
        preparedStatement.setInt(3, sm.getCoordinateX());
        preparedStatement.setInt(4, sm.getCoordinateY());
        preparedStatement.setString(5, sm.getCreationDate());

        if (sm.getHealth() == null) {
            preparedStatement.setNull(6, Types.INTEGER);
        } else {
            preparedStatement.setInt(6, sm.getHealth());
        }

        if (sm.getCategory() == null) {
            preparedStatement.setNull(7, Types.VARCHAR);
        } else {
            preparedStatement.setString(7, sm.getCategory().toString());
        }

        if (sm.getWeaponType() == null) {
            preparedStatement.setNull(8, Types.VARCHAR);
        } else {
            preparedStatement.setString(8, sm.getWeaponType().toString());
        }

        if (sm.getMeleeWeapon() == null) {
            preparedStatement.setNull(9, Types.VARCHAR);
        } else {
            preparedStatement.setString(9, sm.getMeleeWeapon().toString());
        }

        preparedStatement.setString(10, sm.getChapterName());
        preparedStatement.setString(11, sm.getChapterWorld());
        preparedStatement.setInt(12, key);
    }

    private boolean checkUser(Integer key, String login) throws SQLException {
        String sqlCommand = "SELECT id FROM marines WHERE id=? AND owner=?;";
        preparedStatement = connection.prepareStatement(sqlCommand);
        preparedStatement.setInt(1, key);
        preparedStatement.setString(2, login);
        return preparedStatement.executeQuery().next();
    }

    public void put(SpaceMarine sm) {
        treeMap.put(sm.getID(), sm);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public TreeMap<Integer, SpaceMarine> getTreeMap() {
        return treeMap;
    }

}
