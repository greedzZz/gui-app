package client.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Scanner;

public class Scrambler {

//    @Deprecated
//    public boolean readNewbie(Scanner scanner) throws IllegalArgumentException {
//        System.out.println("Want to register a new account? (\"yes\"/\"no\")");
//        String answer;
//        while (scanner.hasNextLine()) {
//            answer = scanner.nextLine();
//            if (answer.equals("yes")) {
//                return true;
//            } else if (answer.equals("no")) {
//                return false;
//            }
//            System.out.println("Please enter \"yes\" or \"no\".");
//        }
//        throw new IllegalArgumentException("Further reading of the commands is impossible.");
//    }
//
//    @Deprecated
//    public String readLogin(Scanner scanner) throws IllegalArgumentException {
//        System.out.println("Please enter login:");
//        String answer;
//        while (scanner.hasNextLine()) {
//            answer = scanner.nextLine().trim();
//            if (!answer.equals("")) {
//                return answer;
//            }
//            System.out.println("Login must not be empty word.");
//        }
//        throw new IllegalArgumentException("Further reading of the commands is impossible.");
//    }
//
//    @Deprecated
//    public String readPassword(Scanner scanner) throws IllegalArgumentException{
//        System.out.println("Please enter password:");
//        String answer;
//        while (scanner.hasNextLine()) {
//            answer = scanner.nextLine();
//            if (!answer.equals("")) {
//                return getHexString(Objects.requireNonNull(getHash(answer)));
//            }
//            System.out.println("Password must not be empty word.");
//        }
//        throw new IllegalArgumentException("Further reading of the commands is impossible.");
//    }

    public static byte[] getHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String pepper = "#7b№X2&m";
            return digest.digest((pepper + password).getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String getHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        String s;
        for (byte b : bytes) {
            s = Integer.toHexString(b);
            try {
                builder.append(s.substring(s.length() - 2));
            } catch (IndexOutOfBoundsException e) {
                builder.append("0").append(s);
            }
        }
        return builder.toString();
    }

    public static String getPassword(String password) {
        return getHexString(getHash(password));
    }

}
