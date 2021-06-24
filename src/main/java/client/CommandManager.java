package client;

import client.utility.AnswerReceiver;
import client.utility.CommandSender;
import client.utility.Scrambler;
import client.utility.ScriptReader;
import common.Reply;
import common.Serializer;
import common.User;
import common.commands.*;
import common.content.Chapter;
import common.content.SpaceMarine;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class CommandManager {
    private final ScriptReader scriptReader;
    private final Serializer serializer;
    private final CommandSender commandSender;
    private final AnswerReceiver answerReceiver;
    private final Scrambler scrambler;
    private User user;

    public CommandManager(SocketAddress address, DatagramSocket socket) {
        this.serializer = new Serializer();
        this.scrambler = new Scrambler();
        this.commandSender = new CommandSender(address, socket);
        this.answerReceiver = new AnswerReceiver(socket, serializer);
        this.scriptReader = new ScriptReader(serializer);
    }

    public Reply authorize(boolean newbie, String login, String password) {
        try {
            commandSender.send(serializer.serialize(new Show(new User(newbie, login, scrambler.getPassword(password)))));
            user = new User(newbie, login, scrambler.getPassword(password));
            return answerReceiver.receive();
        } catch (IOException e) {
            return new Reply(null, false, "Unfortunately, the server is currently unavailable.");
        } catch (ClassNotFoundException e) {
            return new Reply(null, false, e.getMessage());
        }
    }

    public Reply processCommand(CommandType type, String argument, SpaceMarine spaceMarine, Chapter chapter) throws SocketTimeoutException {
        Reply result = new Reply(null, false, null);
        try {
            switch (type) {
                case HELP:
                    Command help = new Help(user);
                    commandSender.send(serializer.serialize(help));
                    result = answerReceiver.receive();
                    break;
                case INFO:
                    Command info = new Info(user);
                    commandSender.send(serializer.serialize(info));
                    result = answerReceiver.receive();
                    break;
                case SHOW:
                    Command show = new Show(user);
                    commandSender.send(serializer.serialize(show));
                    result = answerReceiver.receive();
                    break;
                case INSERT:
                    Command insert = new Insert(user, Integer.parseInt(argument), spaceMarine);
                    commandSender.send(serializer.serialize(insert));
                    result = answerReceiver.receive();
                    break;
                case UPDATE:
                    Command update = new Update(user, Integer.parseInt(argument), spaceMarine);
                    commandSender.send(serializer.serialize(update));
                    result = answerReceiver.receive();
                    break;
                case REMOVE_KEY:
                    Command removeKey = new RemoveKey(user, Integer.parseInt(argument));
                    commandSender.send(serializer.serialize(removeKey));
                    result = answerReceiver.receive();
                    break;
                case CLEAR:
                    Command clear = new Clear(user);
                    commandSender.send(serializer.serialize(clear));
                    result = answerReceiver.receive();
                    break;
                case EXECUTE_SCRIPT:
                    scriptReader.addScript(argument);
                    scriptReader.readScript(argument, commandSender, answerReceiver, user);
                    scriptReader.clearScripts();
                    break;
                case REMOVE_GREATER:
                    Command removeGreater = new RemoveGreater(user, spaceMarine);
                    commandSender.send(serializer.serialize(removeGreater));
                    result = answerReceiver.receive();
                    break;
                case REPLACE_IF_GREATER:
                    Command replaceIfGreater = new ReplaceIfGreater(user, Integer.parseInt(argument), spaceMarine);
                    commandSender.send(serializer.serialize(replaceIfGreater));
                    result = answerReceiver.receive();
                    break;
                case REMOVE_GREATER_KEY:
                    Command removeGreaterKey = new RemoveGreaterKey(user, Integer.parseInt(argument));
                    commandSender.send(serializer.serialize(removeGreaterKey));
                    result = answerReceiver.receive();
                    break;
                case GROUP_COUNTING_BY_COORDINATES:
                    Command groupCountingByCoordinates = new GroupCountingByCoordinates(user);
                    commandSender.send(serializer.serialize(groupCountingByCoordinates));
                    result = answerReceiver.receive();
                    break;
                case FILTER_BY_CHAPTER:
                    Command filterByChapter = new FilterByChapter(user, chapter);
                    commandSender.send(serializer.serialize(filterByChapter));
                    result = answerReceiver.receive();
                    break;
                case FILTER_STARTS_WITH_NAME:
                    Command filterStartsWithName = new FilterStartsWithName(user, argument);
                    commandSender.send(serializer.serialize(filterStartsWithName));
                    result = answerReceiver.receive();
                    break;
            }
        } catch (SocketTimeoutException e) {
            throw new SocketTimeoutException();
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
            result = new Reply(null, false, e.getMessage());
        }
        return result;
    }
}
