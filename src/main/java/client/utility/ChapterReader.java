package client.utility;

import common.content.Chapter;

import java.util.Scanner;

public class ChapterReader {

    public Chapter readChapter(Scanner sc) throws IllegalArgumentException {
        String[] fields = new String[2];
        String argument;
        argument = sc.nextLine().trim();
        if (argument.equals("")) {
            throw new IllegalArgumentException("Chapter name cannot be empty word.");
        }
        fields[0] = argument;

        argument = sc.nextLine().trim();
        if (argument.equals("")) {
            throw new IllegalArgumentException("Chapter world cannot be empty word.");
        }
        fields[1] = argument;
        return new Chapter(fields[0], fields[1]);
    }
}
