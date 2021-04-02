package Pages;

import java.sql.*;
import java.util.Scanner;
import Color.*;
import Database.*;
import Logging.MyLogger;
import Objects.*;
import Run.*;
import SignIn.*;

public class PersonalPage {

    public static void action(User viewer){
        boolean flag = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t"
                + "***** Personal Page *****\n" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. New tweet");
        System.out.println("2. Show tweets");
        System.out.println("3. Edit personal page");
        System.out.println("4. Lists (followers, followings, black list, personal lists)");
        System.out.println("5. Info");
        System.out.println("6. Notifications");
        System.out.println("7. Follow requests you've sent");
        System.out.println("8. Saved messages");
        System.out.println("0. Exit Personal page\n" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= 8)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            Commands.delay(3000);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t"
                    + "***** Personal Page *****\n" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. New tweet");
            System.out.println("2. Show tweets");
            System.out.println("3. Edit personal page");
            System.out.println("4. Lists (followers, followings, black list, personal lists)");
            System.out.println("5. Info");
            System.out.println("6. Notifications");
            System.out.println("7. Follow requests you've sent");
            System.out.println("8. Saved messages");
            System.out.println("0. Exit Personal page\n" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }                                   // Checking for correct command
        switch (choice){
            case 1:{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                viewer.newTweet();
                break;
            }
            case 2:{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                viewer.showTweets(viewer);
                PersonalPage.action(viewer);
                break;
            }
            case 3:{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                editPersonalPage(viewer);
                break;
            }
            case 4:{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                lists(viewer);
                break;
            }
            case 5:{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                info(viewer);
                break;
            }
            case 6:{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                notifications(viewer);
                break;
            }
            case 7:{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                seeFollowRequests(viewer);
                break;
            }
            case 8:{
                viewer.savedMessages.action(viewer);
                break;
            }
            case 0:{
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                Commands.actions(viewer);
                break;
            }
        }

    }

    public static void editPersonalPage(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\t\t***** Edit information *****"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Edit name");
        System.out.println("2. Edit last name\n3. Change username\n4. Change E-mail address\n5. Edit phone number");
        System.out.println("6. Edit bio\n\n0. Exit editing" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= 6)){
            MyLogger.getLogger().log("\t\tInfo\t\tPersonalPage\t\tInvalid command in Edit information");
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command\nTry again" + ConsoleColors.RESET);
            Commands.delay(1500);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Edit name");
            System.out.println("2. Edit last name\n3. Change username\n4. Change E-mail address\n5. Edit phone number");
            System.out.println("6. Edit bio\n\n0. Exit editing" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }                           // Checking for correct command
        switch (choice){
            case 1:{
                EditInformation.editName(user);
                break;
            }
            case 2:{
                EditInformation.editLastName(user);
                break;
            }
            case 3:{
                EditInformation.editUsername(user);
                break;
            }
            case 4:{
                EditInformation.editEmailAddress(user);
                break;
            }
            case 5:{
                EditInformation.editPhoneNumber(user);
                break;
            }
            case 6:{
                EditInformation.editBio(user);
                break;
            }
        }
        PersonalPage.action(user);
    }                              // Done

    public static void notifications(User user){
        getData.getNotifications(user);
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\t\t\t\t***** Notifications *****"
                + ConsoleColors.RESET);
        if (user.notifications == null || user.notifications.isEmpty()){
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\nThere is no new notification to show!"
                    + ConsoleColors.RESET);
        }
        else {
            for (int i = 0; i < user.notifications.size(); i++){
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + user.notifications.get(i)
                        + ConsoleColors.RESET);
            }
            DeleteData.deleteNotifications(user.id);
            user.notifications = null;
        }
        if (!user.isPublic){
            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\n1. Follow requests\n0. Return"
                    + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD + "Choose one item : " + ConsoleColors.RESET);
            String command = scanner.nextLine();
            while ( !(command.equals("1") || command.equals("0")) ){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD + "Choose one item : " + ConsoleColors.RESET);
                command = scanner.nextLine();
            }
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            if (command.equals("1")){
                if (user.requestedUsers.isEmpty()){
                    System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "There is no follow request"
                            + ConsoleColors.RESET);
                }
                else {
                    for (int i = 0; i < user.requestedUsers.size(); i++){
                        User temp = getData.getUser(user.requestedUsers.get(i));
                        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + (i+1) + ". " + temp.name + " "
                                + temp.lastName + " requested to follow you.");
                    }
                    System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n0. Return" + ConsoleColors.RESET);
                    System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : "
                            + ConsoleColors.RESET);
                    int command1 = scanner.nextInt();
                    while (!(command1 >= 0 && command1 <= user.requestedUsers.size())){
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                        System.out.print(ConsoleColors.YELLOW_BOLD + "Choose one item : " + ConsoleColors.RESET);
                        command1 = scanner.nextInt();
                    }
                    if (command1 != 0){
                        User temp = getData.getUser(user.requestedUsers.get(command1 - 1));
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                                + temp.name + " " + temp.lastName + ConsoleColors.BLUE_BOLD_BRIGHT + "\n1. Go"
                                + "to user page\n2. Accept" + "\n3. Reject\n4. Block\n0. Return\n" + ConsoleColors.RESET
                                + ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                        int command2 = scanner.nextInt();
                        while (!(command2 >= 0 && command2 <= 4)){
                            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                            System.out.print(ConsoleColors.YELLOW_BOLD + "Choose one item : " + ConsoleColors.RESET);
                            command2 = scanner.nextInt();
                        }
                        if (command2 == 1){
                            getData.getFullData(temp);
                            temp.showProfile(user);
                        }
                        if (command2 == 2){
                            getData.getFullData(temp);
                            temp.accept(user);
                        }
                        else if (command2 == 3){
                            getData.getFullData(temp);
                            temp.reject(user);
                        }
                        else if (command2 == 4){
                            getData.getFullData(temp);
                            temp.block(user);
                        }
                    }
                }
            }
        }
        Commands.delay(3000);
        action(user);
    }                                 // Constructing

    public static void info(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\t\t***** Info *****\n\n"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Name : " + user.name);
        System.out.println("Last name : " + user.lastName);
        System.out.println("Username : " + user.userName);
        System.out.println("Email address : " + user.emailAddress + ConsoleColors.RESET);
        if (user.dateOfBirth != null){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Date of birth : " + user.dateOfBirth
                    + ConsoleColors.RESET);
        }
        else {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You haven't submitted your date of birth yet! "
                    + ConsoleColors.RESET);
        }
        if (user.phoneNumber != null){
            System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "Phone number : " + user.phoneNumber
                    + ConsoleColors.RESET);
        }
        else {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You haven't submitted your phone number yet! "
                    + ConsoleColors.RESET);
        }
        if (user.bio != null){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Bio : " + user.bio + ConsoleColors.RESET);
        }
        else {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You haven't submitted your bio yet! "
                    + ConsoleColors.RESET);
        }
        if (user.lastSeen != null){
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Last seen : " + user.lastSeen
                    + ConsoleColors.RESET);
        }
        if (user.isPublic){
            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Your account is public" + ConsoleColors.RESET);
        }
        else {
            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Your account is Private" + ConsoleColors.RESET);
        }
        System.out.println("\n1.Exit info : " + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (choice != 1){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command\nTry again" + ConsoleColors.RESET);
            Commands.delay(1500);
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "1.Exit info : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        PersonalPage.action(user);
    }                                          // Done

    public static void lists(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\t\t***** Lists *****\n\n"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Followers" + "\n2. Followings" + "\n3. Black list"
                + "\n4. Personal lists\n0. Return to personal page" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " );
        int choice = scanner.nextInt();
        int choice1;
        while (!(choice >= 0 && choice <= 4)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong Command!\nTry again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\t\t***** Lists *****\n\n"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Followers" + "\n2. Followings" + "\n3. Black list"
                    + "\n4. Personal lists\n0. Return to personal page" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " );
            choice = scanner.nextInt();
        }
        switch (choice){
            case 1:{
                if (user.followers.isEmpty()){
                    System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\n\n\n0 follower" + ConsoleColors.RESET);
                }
                else {
                    user.showList(user, 1);
                }
                returnToList(user);
                break;
            }
            case 2:{
                if (user.followings.isEmpty()){
                    System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\n\n\n0 following" + ConsoleColors.RESET);
                }
                else {
                    user.showList(user, 2);
                }
                returnToList(user);
                break;
            }
            case 3:{
                if (user.blockedUsers.isEmpty()){
                    System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\n\n\n0 blocked" + ConsoleColors.RESET);
                }
                else {
                    user.showList(user, 3);
                }
                returnToList(user);
                break;
            }
            case 0:{
                action(user);
                break;
            }
            case 4:{
                List.action(user);
                break;
            }
        }
    }                                         // Done

    public static void seeFollowRequests(User viewer){
        Scanner scanner = new Scanner(System.in);
        if (viewer.requested.isEmpty()){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You have sent 0 follow requests."
                    + ConsoleColors.RESET);
            Commands.delay(2000);
            PersonalPage.action(viewer);
        }
        else {
            for (int i = 0; i < viewer.requested.size(); i++){
                User requestedToFollow = getData.getUser(viewer.requested.get(i));
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You have sent a follow request to "
                        + requestedToFollow.name + " " + requestedToFollow.lastName + ConsoleColors.RESET);
            }
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\n1. Delete follow request\n0. Return"
                    + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : "
                    + ConsoleColors.RESET);
            String choice1 = scanner.nextLine();
            while (!(choice1.equals("1") || choice1.equals("0"))){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\n1. Delete follow request\n0. Return"
                        + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : "
                        + ConsoleColors.RESET);
                choice1 = scanner.nextLine();
            }
            if (choice1.equals("1")){
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                for (int i = 0; i < viewer.requested.size(); i++){
                    User requestedToFollow = getData.getUser(viewer.requested.get(i));
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + (i+1) + ". You have sent a follow request to "
                            + requestedToFollow.name + " " + requestedToFollow.lastName + ConsoleColors.RESET);
                }
                int choice2 = scanner.nextInt();
                while (!(choice2 >= 1 && choice2 <= viewer.requested.size())){
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again"
                            + ConsoleColors.RESET);
                    System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : "
                            + ConsoleColors.RESET);
                    choice2 = scanner.nextInt();
                }

                /***** Deleting follow request *****/
                String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
                String userName = "postgres";
                String passWord = "saleh791378";
                try {
                    Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
                    MyLogger.getLogger().log("\t\tInfo\t\tPersonalPage\t\tConnected to database");
                    String sql = "DELETE FROM follow_request WHERE follower_id = " + viewer.id
                            + " AND following_id = " + viewer.requested.get(choice2 - 1);
                    Statement statement = connection.createStatement();
                    int rows = statement.executeUpdate(sql);
                    if (rows <= 0){
                        MyLogger.getLogger().log("\t\tDebug\t\tPersonalPage\t\tDeleting follow requests failed");
                    }
                } catch (SQLException e){
                    MyLogger.getLogger().log("\t\tError\t\tPersonalPage\t\tError in connecting to database");
                    e.printStackTrace();
                }
                /***** Deleting follow request *****/

                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Follow request deleted!" + ConsoleColors.RESET);
            }
            else {
                action(viewer);
            }
        }
    }

    public static void returnToList(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n1. Return to list" + ConsoleColors.RESET);
        int temp = scanner.nextInt();
        while (temp != 1){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nWrong command!\nTry again"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Return to list" + ConsoleColors.RESET);
            temp = scanner.nextInt();
        }
        lists(user);
    }                                  // Done
}

