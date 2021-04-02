package Pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import Database.*;
import Objects.*;
import Color.*;
import Run.*;
import SignIn.*;

public class Explore {

    public static void action(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\t\t***** Explore *****"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Search user\n2. See popular tweets\n3. Exit explore"
                + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 1 && choice <= 3)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\nWrong command!\nTry again"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\t\t***** Explore *****"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Search user\n2. See popular tweets\n3. Exit explore"
                    + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }                       // Checking for correct command
        switch (choice){
            case 1:{
                search(viewer);
                break;
            }
            case 2:{
                popularTweets(viewer);
                break;
            }
            case 3:{
                Commands.actions(viewer);
                break;
            }
        }
    }                                    // Done

    public static void search(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\t\t***** Search *****"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Search by name\n2. Search by lastname\n"
                + "3. Search by username\n4. Exit search" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 1 && choice <= 4)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\nWrong command!\nTry again"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\t\t***** Search *****" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Search by name\n2. Search by lastname\n"
                    + "3. Search by username\n4. Exit search" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }                       // Checking for correct command
        switch (choice){
            /**
             * if (mode == 1) --------> Search by name
             * if (mode == 2) --------> Search by lastname
             * if (mode == 3) --------> Search by username
             */
            case 1:{
                searchByMode(user, 1);
                break;
            }               // Mode 1 --------> Search by name
            case 2:{
                searchByMode(user, 2);
                break;
            }               // Mode 2 --------> Search by lastname
            case 3:{
                searchByMode(user, 3);
                break;
            }               // Mode 3 --------> Search by username
            case 4:{
                action(user);
                break;
            }               // Return --------> Explore
        }
    }                                    // Done

    public static void popularTweets(User user){
        Scanner scanner = new Scanner(System.in);
        String[][] tweetIdAndNOL = null;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        if (Explore.getPopularTweets() == null){
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\nThere is nothing to show!"
                    + ConsoleColors.RESET);
            Commands.delay(3000);
            action(user);
        }
        else {
            tweetIdAndNOL = Explore.getPopularTweets();
        }

        int length = 0;
        for (int i = 0; i < 15; i++){
            if (tweetIdAndNOL[0][i] != null){
                length++;
            }
        }

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\t\t***** Popular tweets *****\n"
                + ConsoleColors.RESET);

        boolean flag = true;
        for (int i = 0; i < Math.min(length, 10); i++){
            User tweetOwner = getData.getUser(Integer.parseInt(tweetIdAndNOL[7][i]));
            getData.getBlockedUsers(tweetOwner);
            if (!tweetOwner.blockedUsers.contains(user.id)){
                String[] splitText = tweetIdAndNOL[4][i].split(" ");
                String partOfText;
                if (splitText.length >= 4){
                    partOfText = splitText[0] + " " + splitText[1] + " " + splitText[2]
                            + " " + splitText[3] + " ...";
                }
                else {
                    partOfText = tweetIdAndNOL[4][i];
                }
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i+1) + ". " + tweetIdAndNOL[5][i] + " "
                        + tweetIdAndNOL[6][i] + " :\t" + partOfText + ConsoleColors.RESET
                        + ConsoleColors.PURPLE_BOLD_BRIGHT + "\n\t\t\t\t\t(" + tweetIdAndNOL[1][i]
                        + " likes\t,\t" + tweetIdAndNOL[2][i] + " retweets)" + ConsoleColors.RESET);
                flag = false;
            }
        }                           // Showing popular tweets

        if (flag){
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n\n\nThere is nothing to show!"
                    + ConsoleColors.RESET);
            Commands.delay(3000);
            action(user);
        }

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n0. Exit popular tweets" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= Math.min(10, length))){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\nWrong command!\nTry again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }                 // Checking for valid command
        if (choice == 0){
            action(user);
        }
        else {
            Tweet chosenTweet = getData.getTweet(Integer.parseInt(tweetIdAndNOL[0][choice - 1]));
            chosenTweet.show(user);
            popularTweets(user);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n" + ConsoleColors.RESET);/////////////
        }
    }                             // Done

    public static void searchByMode(User user, int mode){
        String search = null, searchMode = null;
        Scanner scanner = new Scanner(System.in);
        switch (mode){
            case 1:{
                searchMode = "name";
                break;
            }
            case 2:{
                searchMode = "lastname";
                break;
            }
            case 3:{
                searchMode = "username";
                break;
            }
        }
        ArrayList<User> foundUsers = new ArrayList<>();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Please enter the "
                + searchMode + " : " + ConsoleColors.RESET);
        search = scanner.nextLine();
        String[] temp = getData.Search(mode, search).split(" ");
        String[] test = {""};
        if (Arrays.equals(temp, test)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\nNo result found for '"
                    + search + "'!\nTry again" + ConsoleColors.RESET);
            Commands.delay(1500);
            search(user);
        }
        else {
            for (int i = 0; i < temp.length; i++){
                User tempUser = getData.getUser(Integer.parseInt(temp[i]));
                getData.getBlockedUsers(tempUser);
                if (!tempUser.blockedUsers.contains(user.id)){
                    foundUsers.add(tempUser);
                }
            }
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n" + foundUsers.size()
                    + " users found!\n\n\n" + ConsoleColors.RESET);
            for (int i = 0; i < foundUsers.size(); i++){
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i + 1) + ". " + foundUsers.get(i).name
                        + " " + foundUsers.get(i).lastName + ConsoleColors.RESET);
            }                        // Showing users
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n0. Search again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
            int choice = scanner.nextInt();
            while (!(choice >= 0 && choice <= foundUsers.size())){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\nWrong command!\nTry again"
                        +ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
            if (choice == 0){
                search(user);
            }
            else {
                getData.getFollowers(foundUsers.get(choice - 1));
                getData.getFollowings(foundUsers.get(choice - 1));
                foundUsers.get(choice - 1).showProfile(user);
            }
        }
        Explore.search(user);
    }                    // Done

    public static String[][] getPopularTweets(){
        String[][] tweetIdAndNOL = new String[8][15];
        int length = 0, counter = 0;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            //System.out.println("Connected to the server");                 // Should be substituted by logging
            /************************************ Getting likes and retweets ************************************/
            String sql1 = "SELECT * FROM tweets";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            while (resultSet1.next()){
                String id       = resultSet1.getString("id");
                String userId   = resultSet1.getString("user_id");
                String likes    = resultSet1.getString("likes");
                String retweets = resultSet1.getString("retweets");
                String text     = resultSet1.getString("text");
                /********************************* Checking for public accounts *********************************/
                String sql2 = "SELECT * FROM users WHERE user_id = " + userId;
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(sql2);
                while (resultSet2.next()){
                    boolean isActive = resultSet2.getBoolean("is_active");
                    boolean isPublic = resultSet2.getBoolean("is_public");
                    String name      = resultSet2.getString("name");
                    String lastname  = resultSet2.getString("lastname");
                    if (isPublic && isActive){
                        tweetIdAndNOL[0][counter] = id;
                        tweetIdAndNOL[1][counter] = likes;
                        tweetIdAndNOL[2][counter] = retweets;
                        tweetIdAndNOL[3][counter] = likes + retweets;
                        tweetIdAndNOL[4][counter] = text;
                        tweetIdAndNOL[5][counter] = name;
                        tweetIdAndNOL[6][counter] = lastname;
                        tweetIdAndNOL[7][counter] = userId;
                        counter++;
                        length++;
                    }
                }
            }
            connection.close();
        } catch (SQLException e){
            //System.out.println("Error in connecting to server");               // Should be substituted by logging
            e.printStackTrace();
        }
        /********************************* Sorting tweets by likes and retweets *********************************/
        for (int i = 0; i < length; i++){
            for (int j = i; j < length; j++){
                if (Integer.parseInt(tweetIdAndNOL[3][i]) < Integer.parseInt(tweetIdAndNOL[3][j])){
                    String id       = tweetIdAndNOL[0][i];
                    String likes    = tweetIdAndNOL[1][i];
                    String retweets = tweetIdAndNOL[2][i];
                    String sum      = tweetIdAndNOL[3][i];
                    String text     = tweetIdAndNOL[4][i];
                    String name     = tweetIdAndNOL[5][i];
                    String lastname = tweetIdAndNOL[6][i];

                    tweetIdAndNOL[0][i] = tweetIdAndNOL[0][j];
                    tweetIdAndNOL[1][i] = tweetIdAndNOL[1][j];
                    tweetIdAndNOL[2][i] = tweetIdAndNOL[2][j];
                    tweetIdAndNOL[3][i] = tweetIdAndNOL[3][j];
                    tweetIdAndNOL[4][i] = tweetIdAndNOL[4][j];
                    tweetIdAndNOL[5][i] = tweetIdAndNOL[5][j];
                    tweetIdAndNOL[6][i] = tweetIdAndNOL[6][j];

                    tweetIdAndNOL[0][j] = id;
                    tweetIdAndNOL[1][j] = likes;
                    tweetIdAndNOL[2][j] = retweets;
                    tweetIdAndNOL[3][j] = sum;
                    tweetIdAndNOL[4][j] = text;
                    tweetIdAndNOL[5][j] = name;
                    tweetIdAndNOL[6][j] = lastname;
                }
            }
        }
        if (counter == 0){
            return null;
        }
        else {
            return tweetIdAndNOL;
        }
    }
}

