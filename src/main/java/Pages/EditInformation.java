package Pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import Color.*;
import Database.*;
import Logging.MyLogger;
import Objects.*;
import SignIn.*;
import Run.*;

public class EditInformation {

    public static void editName(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.BLUE + "\n\n\n\n\n\n\n\n\n\nPlease enter your name : " + ConsoleColors.RESET);
        String name = scanner.nextLine();
        user.name = name;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tConnected to database");

            String sql = "UPDATE users SET name = '" + name + "' WHERE user_id = "
                    + user.id;

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tName of user " + user.id + " updated");
                Commands.delay(1500);
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tEditInformation\t\tError in connecting to database");
            e.printStackTrace();
        }
    }                                                   // Done

    public static void editLastName(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.BLUE + "\n\n\n\n\n\n\n\n\n\nPlease enter your lastname : " + ConsoleColors.RESET);
        String lastName = scanner.nextLine();
        user.lastName = lastName;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tConnected to database");


            String sql = "UPDATE users SET lastname = '" + lastName + "' WHERE user_id = "
                    + user.id;

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tLastname of user " + user.id + " updated");
                Commands.delay(1500);
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tEditInformation\t\tError in connecting to database");
            e.printStackTrace();
        }
    }                                               // Done

    public static void editUsername(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.BLUE + "\n\n\n\n\n\n\n\n\n\nPlease enter your username : " + ConsoleColors.RESET);
        String username = SignUp.getUsername();
        user.userName = username;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tConnected to database");

            String sql = "UPDATE users SET username = '" + username + "' WHERE user_id = "
                    + user.id;

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tUsername of user " + user.id + " updated");
                Commands.delay(1500);
            }

            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tEditInformation\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void editPhoneNumber(User user){

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\nPlease enter your phone number : "
                + ConsoleColors.RESET);
        String phoneNumber = scanner.nextLine();
        while (!SignUp.validPhoneNumber(phoneNumber)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong phone number!\nTry again"
                    + ConsoleColors.RESET);
            Commands.delay(1500);
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your phone number : "
                    + ConsoleColors.RESET);
            phoneNumber = scanner.nextLine();
        }
        user.phoneNumber = phoneNumber;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tConnected to database");

            String sql = "UPDATE users SET phone_number = '" + phoneNumber + "' WHERE user_id = "
                    + user.id;

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tPhone number of user " + user.id + " updated");
                System.out.println(ConsoleColors.GREEN + "Phone number of the user updated!" + ConsoleColors.RESET);
                Commands.delay(1500);
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tEditInformation\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void editBio(User user){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\nPlease enter your bio : "
                + ConsoleColors.RESET);

        String bio = scanner.nextLine();
        user.bio = bio;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tConnected to database");

            String sql = "UPDATE users SET bio = '" + bio + "' WHERE user_id = "
                    + user.id;

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tBio of user " + user.id + " updated");
                Commands.delay(1500);
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tEditInformation\t\tError in connecting to database");
            e.printStackTrace();
        }


    }

    public static void editEmailAddress(User user){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        String emailAddress = SignUp.getEmailAddress();
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tConnected to database");
            String sql = "UPDATE users SET email_address = '" + emailAddress + "' WHERE user_id = "
                    + user.id;

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tEditInformation\t\tE-mail address of user " + user.id + " updated");
                System.out.println(ConsoleColors.GREEN + "E-mail address of the user updated!" + ConsoleColors.RESET);
                Commands.delay(1500);
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tEditInformation\t\tError in connecting to database");
            e.printStackTrace();
        }

    }

}

