package Pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import Color.ConsoleColors;
import Database.DeleteData;
import Database.Submit;
import Logging.MyLogger;
import Objects.*;
import Run.Application;
import Run.Commands;
import SignIn.SignUp;

public class Settings {

    public static void action(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\t\t\t\t\t***** Settings *****\n"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Account Privacy\n2. Last seen mode"
                + "\n3. Deactivate account\n4. Change password\n5. Delete account\n\n0. Return"
                + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= 5)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again"
                    + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        switch (choice){
            case 0:{
                Commands.actions(user);
                break;
            }
            case 1:{
                privacy(user);
                break;
            }
            case 2:{
                lastSeenMode(user);
                break;
            }
            case 3:{
                deactivate(user);
                break;
            }
            case 4:{
                changePassword(user);
                break;
            }
            case 5:{
                deleteAccount(user);
                break;
            }
        }
        action(user);
    }

    protected static void privacy(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        if (user.isPublic){
            System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT + "Your account is public\n"
                    + "Do you want to change account to private? (y/n) : " + ConsoleColors.RESET);
        } else {
            System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT + "Your account is private\n"
                    + "Do you want to change account to public? (y/n) : " + ConsoleColors.RESET);
        }
        String command = scanner.nextLine();
        while (!command.equals("n") && !command.equals("y")){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "do you want to change your"
                    + " privacy mode? (y/n)" + ConsoleColors.RESET);
            command = scanner.nextLine();
        }
        if (command.equals("y")){
            user.isPublic = !user.isPublic;
            if (user.isPublic){
                MyLogger.getLogger().log("\t\tInfo\t\tSettings\t\tPage of user " + user.id + " is now public");
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                        + "Your account is public now" + ConsoleColors.RESET);
            }
            else {
                MyLogger.getLogger().log("\t\tInfo\t\tSettings\t\tPage of user " + user.id + " is now private");
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                        + "Your account is private now" + ConsoleColors.RESET);
            }
            Commands.delay(1500);
            String privacy;
            Submit.changePrivacy(user.id, user.isPublic);
        }
    }

    protected static void lastSeenMode(User user){
        Scanner scanner = new Scanner(System.in);
        int lastSeenMode;
        String text = "";
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        switch (user.lastSeenMode){
            case 1:{
                text = "Everybody";
                break;
            }
            case 2:{
                text = "Nobody";
                break;
            }
            case 3:{
                text = "Just your followers";
                break;
            }
        }
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + text +" can see your last seen.\n\n"
                + "Do you want to change it? (y/n) : " + ConsoleColors.RESET);
        String temp = scanner.nextLine();
        while (!temp.equals("n") && !temp.equals("y")){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again\n"
                    + ConsoleColors.RESET + ConsoleColors.GREEN_BOLD_BRIGHT + "Do you want"
                    + " to change your last seen mode? (y/n) : " + ConsoleColors.RESET);
            temp = scanner.nextLine();
        }
        if (temp.equals("y")){
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Everybody\n2. Nobody"
                    + "\n3. Just your followers" + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT
                    + "\nChoose one item : " + ConsoleColors.RESET);
            lastSeenMode = scanner.nextInt();
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Your last seen mode changed!" + ConsoleColors.RESET);
            Commands.delay(1500);
            user.lastSeenMode = lastSeenMode;
            Submit.changeLastSeenMode(user.id, lastSeenMode);
        }
    }

    protected static void deactivate(User user){
        Scanner scanner = new Scanner(System.in);
        boolean isActive;
        String temp = "";
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Your account is active now"
                + "\nDo you want to deactivate it? (y/n) : ");
        while (!temp.equals("n") && !temp.equals("y")){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again\n"
                    + ConsoleColors.RESET + ConsoleColors.GREEN_BOLD_BRIGHT + "Do you want to deactivate it? (y/n) : "
                    + ConsoleColors.RESET);
            temp = scanner.nextLine();
        }
        if (temp.equals("y")){
            user.isActive = false;
            MyLogger.getLogger().log("\t\tInfo\t\tSettings\t\tAccount of user " + user.id + " deactivated");
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Your account deactivated!" + ConsoleColors.RESET);
            Submit.submitDeactivation(user.id);
            Commands.delay(1500);
            System.exit(0);
        }
    }

    public static void changePassword(User user){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your old password : " + ConsoleColors.RESET);
        String oldPassword = scanner.nextLine();
        while (!oldPassword.equals(user.password)){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong password!\nTry again");
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your old password : "
                    + ConsoleColors.RESET);
            oldPassword = scanner.nextLine();
        }
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your new password : " + ConsoleColors.RESET);
        String password = SignUp.getPassword();
        user.password = password;
        Submit.changePassword(user.id, password);
    }

    protected static void deleteAccount(User user){
        Scanner scanner = new Scanner(System.in);
        String temp = "";
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.RED_BOLD_BRIGHT + "If you delete your account, all of your"
                + " information will be deleted and they wouldn't be achievable anymore!\nDo you want"
                + " to continue? (y/n) : ");
        temp = scanner.nextLine();
        while (!temp.equals("n") && !temp.equals("y")){
            System.out.print(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again"
                    + ConsoleColors.RESET + ConsoleColors.GREEN_BOLD_BRIGHT + "Do you want"
                    + " to delete your account? (y/n)" + ConsoleColors.RESET);
            temp = scanner.nextLine();
        }
        if (temp.equals("y")){
            MyLogger.getLogger().log("\t\tInfo\t\tSettings\t\tAccount of user " + user.id + " deleted");
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\nYour account is being deleted!"
                    + "\nDon't stop the program" + ConsoleColors.RESET);
            Commands.delay(2000);
            DeleteData.deleteAccount(user.id);
            System.exit(0);
        }
    }

}
