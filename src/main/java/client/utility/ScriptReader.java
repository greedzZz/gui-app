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

    public ScriptReader(ElementReader elementReader, ChapterReader chapterReader, Serializer serializer) {
        this.elementReader = elementReader;
        this.chapterReader = chapterReader;
        this.serializer = serializer;
    }

    public void readScript(String pathname, CommandSender commandSender, AnswerReceiver answerReceiver, User user) {
        try {
            if (pathname.matches("(/dev/)\\w*")) {
                throw new IllegalArgumentException("The script file is not correct.");
            }
            File file = new File(pathname);
            Scanner scanFile = new Scanner(file);
            String command;
            StringBuilder builder = new StringBuilder();
            boolean isIncorrect = false;
            while (!isIncorrect && scanFile.hasNextLine()) {
                String[] input = scanFile.nextLine().trim().split(" ");
                command = input[0];
                try {
                    switch (command) {
                        case "help":
                            Command help = new Help(user);
                            commandSender.send(serializer.serialize(help));
                            builder.append(answerReceiver.receive().getMessage());
                            break;
                        case "info":
                            Command info = new Info(user);
                            commandSender.send(serializer.serialize(info));
                            builder.append(answerReceiver.receive().getMessage());
                            break;
                        case "show":
                            Command show = new Show(user);
                            commandSender.send(serializer.serialize(show));
                            builder.append(answerReceiver.receive().getMessage());
                            break;
                        case "insert":
                            try {
                                int key = Integer.parseInt(input[1]);
                                if (key < 0) {
                                    throw new NumberFormatException();
                                }
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command insert = new Insert(user, key, sm);
                                commandSender.send(serializer.serialize(insert));
                                builder.append(answerReceiver.receive().getMessage());
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }
                            break;
                        case "update":
                            try {
                                int id = Integer.parseInt(input[1]);
                                if (id < 0) {
                                    throw new NumberFormatException();
                                }
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command update = new Update(user, id, sm);
                                commandSender.send(serializer.serialize(update));
                                builder.append(answerReceiver.receive().getMessage());
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }
                            break;
                        case "remove_key":
                            try {
                                Integer key = Integer.parseInt(input[1]);
                                Command removeKey = new RemoveKey(user, key);
                                commandSender.send(serializer.serialize(removeKey));
                                builder.append(answerReceiver.receive().getMessage());
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }
                            break;
                        case "clear":
                            Command clear = new Clear(user);
                            commandSender.send(serializer.serialize(clear));
                            builder.append(answerReceiver.receive().getMessage());
                            break;
                        case "execute_script":
                            try {
                                File nextFile = new File(input[1]);
                                if (otherScripts.contains(nextFile.getAbsolutePath())) {
                                    throw new IllegalArgumentException("Recursion detected. Further reading of the script is impossible.");
                                } else {
                                    otherScripts.add(nextFile.getAbsolutePath());
                                    readScript(input[1], commandSender, answerReceiver, user);
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }
                            break;
                        case "remove_greater":
                            try {
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command removeGreater = new RemoveGreater(user, sm);
                                commandSender.send(serializer.serialize(removeGreater));
                                builder.append(answerReceiver.receive().getMessage());
                            } catch (IllegalArgumentException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }

                            break;
                        case "replace_if_greater":
                            try {
                                Integer key = Integer.parseInt(input[1]);
                                SpaceMarine sm = elementReader.readElement(scanFile);
                                Command replaceIfGreater = new ReplaceIfGreater(user, key, sm);
                                commandSender.send(serializer.serialize(replaceIfGreater));
                                builder.append(answerReceiver.receive().getMessage());
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }
                            break;
                        case "remove_greater_key":
                            try {
                                Integer key = Integer.parseInt(input[1]);
                                Command removeGreaterKey = new RemoveGreaterKey(user, key);
                                commandSender.send(serializer.serialize(removeGreaterKey));
                                builder.append(answerReceiver.receive().getMessage());
                            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }
                            break;
                        case "group_counting_by_coordinates":
                            Command groupCountingByCoordinates = new GroupCountingByCoordinates(user);
                            commandSender.send(serializer.serialize(groupCountingByCoordinates));
                            builder.append(answerReceiver.receive().getMessage());
                            break;
                        case "filter_by_chapter":
                            try {
                                Chapter chapter = chapterReader.readChapter(scanFile);
                                Command filterByChapter = new FilterByChapter(user, chapter);
                                commandSender.send(serializer.serialize(filterByChapter));
                                builder.append(answerReceiver.receive().getMessage());
                            } catch (IllegalArgumentException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }
                            break;
                        case "filter_starts_with_name":
                            try {
                                Command filterStartsWithName = new FilterStartsWithName(user, input[1]);
                                commandSender.send(serializer.serialize(filterStartsWithName));
                                builder.append(answerReceiver.receive().getMessage());
                            } catch (ArrayIndexOutOfBoundsException e) {
                                builder.append("The script file is not correct. Further reading of the script is impossible.");
                                isIncorrect = true;
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("The script file is not correct or there are no commands there. Further reading of the script is impossible.");
                    }
                } catch (IllegalArgumentException e) {
                    builder.append(e.getMessage());
                    isIncorrect = true;
                }
            }
            DialogManager.createAlert("Execute script", builder.toString(), Alert.AlertType.INFORMATION, true);
        } catch (FileNotFoundException e) {
            DialogManager.createAlert("Error", "File with the specified pathname does not exist or there is no read permission for this file.", Alert.AlertType.ERROR, false);
        } catch (Exception e) {
            DialogManager.createAlert("Error", e.getMessage(), Alert.AlertType.ERROR, false);
        } finally {
            File file = new File(pathname);
            otherScripts.remove(file.getAbsolutePath());
        }
    }

    public void clearScripts() {
        otherScripts.clear();
    }

    public void addScript(String s) {
        otherScripts.add(s);
    }
}
