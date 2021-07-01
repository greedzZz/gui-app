package client.utility;

import common.Serializer;
import common.User;
import common.commands.*;
import common.content.Chapter;
import common.content.SpaceMarine;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class ScriptReader {
    private final ElementReader elementReader;
    private final ChapterReader chapterReader;
    private final LinkedList<String> otherScripts = new LinkedList<>();
    private final Serializer serializer;
    private Localizator localizator;

    public ScriptReader(Serializer serializer) {
        this.elementReader = new ElementReader();
        this.chapterReader = new ChapterReader();
        this.serializer = serializer;
    }

    public void readScript(String pathname, CommandSender commandSender, AnswerReceiver answerReceiver, User user) {
        try {
            if (pathname.matches("(/dev/)\\w*")) {
                throw new FileNotFoundException("The script file is not correct.");
            }
            File file = new File(pathname);
            Scanner scanFile = new Scanner(file);
            String command;
            SpaceMarine sm;
            int key;
            while (scanFile.hasNextLine()) {
                String[] input = scanFile.nextLine().trim().split(" ");
                command = input[0];
                switch (command) {
                    case "help":
                        Command help = new Help(user);
                        commandSender.send(serializer.serialize(help));
                        answerReceiver.receive();
                        break;
                    case "info":
                        Command info = new Info(user);
                        commandSender.send(serializer.serialize(info));
                        answerReceiver.receive();
                        break;
                    case "show":
                        Command show = new Show(user);
                        commandSender.send(serializer.serialize(show));
                        answerReceiver.receive();
                        break;
                    case "insert":
                        key = Integer.parseInt(input[1]);
                        if (key < 0) {
                            throw new NumberFormatException();
                        }
                        sm = elementReader.readElement(scanFile);
                        Command insert = new Insert(user, key, sm);
                        commandSender.send(serializer.serialize(insert));
                        answerReceiver.receive();
                        break;
                    case "update":
                        int id = Integer.parseInt(input[1]);
                        if (id < 0) {
                            throw new NumberFormatException();
                        }
                        sm = elementReader.readElement(scanFile);
                        Command update = new Update(user, id, sm);
                        commandSender.send(serializer.serialize(update));
                        answerReceiver.receive();
                        break;
                    case "remove_key":
                        key = Integer.parseInt(input[1]);
                        Command removeKey = new RemoveKey(user, key);
                        commandSender.send(serializer.serialize(removeKey));
                        answerReceiver.receive();
                        break;
                    case "clear":
                        Command clear = new Clear(user);
                        commandSender.send(serializer.serialize(clear));
                        answerReceiver.receive();
                        break;
                    case "execute_script":
                        File nextFile = new File(input[1]);
                        if (otherScripts.contains(nextFile.getAbsolutePath())) {
                            throw new IllegalArgumentException("Recursion detected. Further reading of the script is impossible.");
                        } else {
                            otherScripts.add(nextFile.getAbsolutePath());
                            readScript(input[1], commandSender, answerReceiver, user);
                        }
                        break;
                    case "remove_greater":
                        sm = elementReader.readElement(scanFile);
                        Command removeGreater = new RemoveGreater(user, sm);
                        commandSender.send(serializer.serialize(removeGreater));
                        answerReceiver.receive();
                        break;
                    case "replace_if_greater":
                        key = Integer.parseInt(input[1]);
                        sm = elementReader.readElement(scanFile);
                        Command replaceIfGreater = new ReplaceIfGreater(user, key, sm);
                        commandSender.send(serializer.serialize(replaceIfGreater));
                        answerReceiver.receive();
                        break;
                    case "remove_greater_key":
                        key = Integer.parseInt(input[1]);
                        Command removeGreaterKey = new RemoveGreaterKey(user, key);
                        commandSender.send(serializer.serialize(removeGreaterKey));
                        answerReceiver.receive();
                        break;
                    case "group_counting_by_coordinates":
                        Command groupCountingByCoordinates = new GroupCountingByCoordinates(user);
                        commandSender.send(serializer.serialize(groupCountingByCoordinates));
                        answerReceiver.receive();
                        break;
                    case "filter_by_chapter":
                        Chapter chapter = chapterReader.readChapter(scanFile);
                        Command filterByChapter = new FilterByChapter(user, chapter);
                        commandSender.send(serializer.serialize(filterByChapter));
                        answerReceiver.receive();
                        break;
                    case "filter_starts_with_name":
                        Command filterStartsWithName = new FilterStartsWithName(user, input[1]);
                        commandSender.send(serializer.serialize(filterStartsWithName));
                        answerReceiver.receive();
                        break;
                    default:
                        throw new IllegalArgumentException("The script file is not correct or there are no commands there. Further reading of the script is impossible.");
                }
            }
            DialogManager.createAlert(localizator.getKeyString("ExecuteScript"), localizator.getKeyString("ScriptSuccess"), Alert.AlertType.INFORMATION, false);
        } catch (RuntimeException e) {
            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("ScriptRunTime"), Alert.AlertType.ERROR, false);
        } catch (FileNotFoundException e) {
            DialogManager.createAlert(localizator.getKeyString("Error"), localizator.getKeyString("ScriptFile"), Alert.AlertType.ERROR, false);
        } catch (Exception e) {
            DialogManager.createAlert(localizator.getKeyString("Error"), e.getMessage(), Alert.AlertType.ERROR, false);
        } finally {
            File file = new File(pathname);
            otherScripts.remove(file.getAbsolutePath());
        }
    }

    public void clearScripts() {
        otherScripts.clear();
    }

    public void addScript(String script) {
        otherScripts.add(script);
    }

    public void setLocalizator(Localizator localizator) {
        this.localizator = localizator;
    }
}
