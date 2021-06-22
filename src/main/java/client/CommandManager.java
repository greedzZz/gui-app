package client;

import client.utility.*;
import common.Reply;
import common.Serializer;
import common.commands.*;
import common.content.Chapter;
import common.content.SpaceMarine;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class CommandManager {
//    private final ElementReader elementReader;
//    private final ChapterReader chapterReader;
//    private final ScriptReader scriptReader;
    private final Serializer serializer;
//    private final Authenticator authenticator;
//    private final ClientAsker asker;
    private final CommandSender commandSender;
    private final AnswerReceiver answerReceiver;

    public CommandManager(SocketAddress address, DatagramSocket socket) {
//        this.elementReader = new ElementReader();
//        this.chapterReader = new ChapterReader();
        this.serializer = new Serializer();
//        this.authenticator = new Authenticator();
//        this.asker = new ClientAsker();
        this.commandSender = new CommandSender(address, socket);
        this.answerReceiver = new AnswerReceiver(socket, serializer);
//        this.scriptReader = new ScriptReader(elementReader, chapterReader, serializer);
    }

    public Reply authorize(boolean newbie, String login, String password) {
        try {
            commandSender.send(serializer.serialize(new Auth(newbie, login, Scrambler.getPassword(password))));
            return answerReceiver.receive();
        } catch (IOException e) {
            return new Reply(null, false, "Unfortunately, the server is currently unavailable.");
        } catch (ClassNotFoundException e) {
            return new Reply(null, false, e.getMessage());
        }
    }

    public Reply processCommand(CommandType type, String argument, boolean newbie, String login, String password) throws SocketTimeoutException {
        Reply result = new Reply(null, false, null);
        try {
            switch (type) {
                case HELP:
                    Command help = new Help(newbie, login, password);
                    commandSender.send(serializer.serialize(help));
                    result = answerReceiver.receive();
                break;
                case INFO:
                    Command info = new Info(newbie, login, password);
                    commandSender.send(serializer.serialize(info));
                    result = answerReceiver.receive();
                break;
//                case SHOW:
//                    Command show = new Show(newbie, login, password);
//                    commandSender.send(serializer.serialize(show));
//                    result = answerReceiver.receive();
//                break;
                case INSERT:
                    break;
                case UPDATE:
                    break;
                case REMOVE_KEY:
                    break;
                case CLEAR:
                    Command clear = new Clear(newbie, login, password);
                    commandSender.send(serializer.serialize(clear));
                    result = answerReceiver.receive();
                    break;
                case EXECUTE_SCRIPT:
                    break;
                case EXIT:
                    System.exit(0);
                    break;
                case REMOVE_GREATER:
                    break;
                case REPLACE_IF_GREATER:
                    break;
                case REMOVE_GREATER_KEY:
                    break;
                case GROUP_COUNTING_BY_COORDINATES:
                    Command groupCountingByCoordinates = new GroupCountingByCoordinates(newbie, login, password);
                    commandSender.send(serializer.serialize(groupCountingByCoordinates));
                    System.out.println(answerReceiver.receive());
                    break;
                case FILTER_BY_CHAPTER:
                    break;
                case FILTER_STARTS_WITH_NAME:
                    break;
            }
        } catch (SocketTimeoutException e) {
            throw new SocketTimeoutException();
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
            result = new Reply(null, false, e.getMessage());
        }
        return result;
    }

//    @Deprecated
//    public void readInput() throws SocketTimeoutException {
//        Scanner scanner = new Scanner(System.in);
//        boolean newbie;
//        String login;
//        String password;
//        while (true) {
//            try {
//                newbie = authenticator.readNewbie(scanner);
//                login = authenticator.readLogin(scanner);
//                password = authenticator.readPassword(scanner);
//                commandSender.send(serializer.serialize(new Auth(newbie, login, password)));
//                String answer = answerReceiver.receive();
//                if (answer.equals("Authorization was successful.")) {
//                    System.out.println(answer);
//                    break;
//                } else {
//                    System.out.println(answer);
//                    if (asker.ask("Want to try again?") <= 0) {
//                        throw new IllegalArgumentException();
//                    }
//                }
//            } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
//                scanner.close();
//                System.out.println("The program is finished.");
//                System.exit(0);
//            }
//        }
//        System.out.println("Please, enter a command. (Enter \"help\" to get information about available commands)");
//        String command = "";
//        while (scanner.hasNextLine() && !command.equals("exit")) {
//            String[] input = scanner.nextLine().trim().split(" ");
//            command = input[0];
//            try {
//                switch (command) {
//                    case "help":
//                        Command help = new Help(newbie, login, password);
//                        commandSender.send(serializer.serialize(help));
//                        System.out.println(answerReceiver.receive());
//                        break;
//                    case "info":
//                        Command info = new Info(newbie, login, password);
//                        commandSender.send(serializer.serialize(info));
//                        System.out.println(answerReceiver.receive());
//                        break;
//                    case "show":
//                        Command show = new Show(newbie, login, password);
//                        commandSender.send(serializer.serialize(show));
//                        System.out.println(answerReceiver.receive());
//                        break;
//                    case "insert":
//                        try {
//                            int key = Integer.parseInt(input[1]);
//                            if (key < 0) {
//                                throw new NumberFormatException();
//                            }
//                            SpaceMarine sm = elementReader.readElement(scanner);
//                            Command insert = new Insert(newbie, login, password, key, sm);
//                            commandSender.send(serializer.serialize(insert));
//                            System.out.println(answerReceiver.receive());
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            System.out.println("To execute this command, you must enter the required argument.");
//                        } catch (NumberFormatException e) {
//                            System.out.println("Key value must be integer. Greater than 0.");
//                        }
//                        break;
//                    case "update":
//                        try {
//                            int id = Integer.parseInt(input[1]);
//                            if (id < 0) {
//                                throw new NumberFormatException();
//                            }
//                            SpaceMarine sm = elementReader.readElement(scanner);
//                            Command update = new Update(newbie, login, password, id, sm);
//                            commandSender.send(serializer.serialize(update));
//                            System.out.println(answerReceiver.receive());
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            System.out.println("To execute this command, you must enter the required argument.");
//                        } catch (NumberFormatException e) {
//                            System.out.println("ID value must be integer. Greater than 0.");
//                        }
//                        break;
//                    case "remove_key":
//                        try {
//                            Integer key = Integer.parseInt(input[1]);
//                            Command removeKey = new RemoveKey(newbie, login, password, key);
//                            commandSender.send(serializer.serialize(removeKey));
//                            System.out.println(answerReceiver.receive());
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            System.out.println("To execute this command, you must enter the required argument.");
//                        } catch (NumberFormatException e) {
//                            System.out.println("The input argument is not an integer.");
//                        }
//                        break;
//                    case "clear":
//                        Command clear = new Clear(newbie, login, password);
//                        commandSender.send(serializer.serialize(clear));
//                        System.out.println(answerReceiver.receive());
//                        break;
//                    case "execute_script":
//                        try {
//                            File file = new File(input[1]);
//                            scriptReader.addScript(file.getAbsolutePath());
//                            System.out.println("Trying to start execution of the script.\n");
//                            scriptReader.readScript(input[1], commandSender, answerReceiver, newbie, login, password);
//                            scriptReader.clearScripts();
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            System.out.println("To execute this command, you must enter the required argument.");
//                        }
//                        break;
//                    case "exit":
//                        scanner.close();
//                        System.out.println("The program is finished.\n");
//                        System.exit(0);
//                        break;
//                    case "remove_greater": {
//                        SpaceMarine sm = elementReader.readElement(scanner);
//                        Command removeGreater = new RemoveGreater(newbie, login, password, sm);
//                        commandSender.send(serializer.serialize(removeGreater));
//                        System.out.println(answerReceiver.receive());
//                    }
//                    break;
//                    case "replace_if_greater":
//                        try {
//                            Integer key = Integer.parseInt(input[1]);
//                            SpaceMarine sm = elementReader.readElement(scanner);
//                            Command replaceIfGreater = new ReplaceIfGreater(newbie, login, password, key, sm);
//                            commandSender.send(serializer.serialize(replaceIfGreater));
//                            System.out.println(answerReceiver.receive());
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            System.out.println("To execute this command, you must enter the required argument.");
//                        } catch (NumberFormatException e) {
//                            System.out.println("The input argument is not an integer.");
//                        }
//                        break;
//                    case "remove_greater_key":
//                        try {
//                            Integer key = Integer.parseInt(input[1]);
//                            Command removeGreaterKey = new RemoveGreaterKey(newbie, login, password, key);
//                            commandSender.send(serializer.serialize(removeGreaterKey));
//                            System.out.println(answerReceiver.receive());
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            System.out.println("To execute this command, you must enter the required argument.");
//                        } catch (NumberFormatException e) {
//                            System.out.println("The input argument is not an integer.");
//                        }
//                        break;
//                    case "group_counting_by_coordinates":
//                        Command groupCountingByCoordinates = new GroupCountingByCoordinates(newbie, login, password);
//                        commandSender.send(serializer.serialize(groupCountingByCoordinates));
//                        System.out.println(answerReceiver.receive());
//                        break;
//                    case "filter_by_chapter":
//                        Chapter chapter = chapterReader.readChapter(scanner);
//                        Command filterByChapter = new FilterByChapter(newbie, login, password, chapter);
//                        commandSender.send(serializer.serialize(filterByChapter));
//                        System.out.println(answerReceiver.receive());
//                        break;
//                    case "filter_starts_with_name":
//                        try {
//                            Command filterStartsWithName = new FilterStartsWithName(newbie, login, password, input[1]);
//                            commandSender.send(serializer.serialize(filterStartsWithName));
//                            System.out.println(answerReceiver.receive());
//                        } catch (ArrayIndexOutOfBoundsException e) {
//                            System.out.println("To execute this command, you must enter the required argument.");
//                        }
//                        break;
//                    default:
//                        throw new IllegalArgumentException("Incorrect command input. Enter \"help\" to get information about available commands.");
//                }
//
//            } catch (SocketTimeoutException e) {
//                throw new SocketTimeoutException();
//            } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        scanner.close();
//        System.out.println("The program is finished.");
//        System.exit(0);
//    }
}
