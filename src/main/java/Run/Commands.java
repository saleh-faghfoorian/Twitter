package Run;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import Color.*;
import Database.*;
import Logging.MyLogger;
import Objects.*;
import Pages.*;
import SignIn.*;

public class Commands {

    public static void actions(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\t\t***** Menu *****\n"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Personal page");
        System.out.println("2. Timeline");
        System.out.println("3. Explore");
        System.out.println("4. Chats");
        System.out.println("5. Settings");
        System.out.println("6. Log out\n" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        if (user.followers.isEmpty() && user.followings.isEmpty()){
            getData.getFullData(user);
        }
        int choice = scanner.nextInt();
        while (!(choice >= 1 && choice <= 6)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            delay(3000);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\t\t***** Menu *****\n"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Personal page");
            System.out.println("2. Timeline");
            System.out.println("3. Explore");
            System.out.println("4. Chats");
            System.out.println("5. Settings");
            System.out.println("6. Log out\n" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }                           // Checking for correct command
        switch (choice){
            case 1:{
                PersonalPage.action(user);
                break;
            }
            case 2:{
                Timeline.action(user);
                break;
            }
            case 3:{
                Explore.action(user);
                break;
            }
            case 4:{
                Chat.action(user);
                break;
            }
            case 5:{
                Settings.action(user);
                break;
            }
            case 6:{
                logOut(user);
                break;
            }
        }
    }                                 // Done

    public static void logOut(User user){
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t\t\t***** Exit :( *****"
                + "\n\n\n\n\n\n\n\n\n\n\n1. Log in with another account" + "\n2. Exit twitter" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        while (!(choice >= 1 && choice <= 2)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            delay(1500);
            System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n1. Sign in with another account"
                    + "\n2. Exit twitter" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        user.lastSeen = LocalDateTime.now();
        Submit.submitLastSeen(user.id, user.lastSeen);
        MyLogger.getLogger().log("\t\tInfo\t\tCommands\t\tUser " + user.id + " logged out");
        if (choice == 1){
            User user1 = LogIn.login();
            actions(user1);
        }
        else {
            System.exit(0);
        }
    }                                  // Done

    public static void delay(int delayTime){
        try
        {
            Thread.sleep(delayTime);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }                               // Done

}
