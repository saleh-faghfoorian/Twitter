package SignIn;

import java.sql.*;
import java.util.Scanner;
import Color.*;
import Database.*;
import Logging.MyLogger;
import Objects.*;
import Pages.*;
import Run.*;

public class LogIn {
    public static User login(){
        User user = null;
        int userId = -1;
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\t\t******* Login *******\n\n" + ConsoleColors.RESET);
        while (true){
            /***************************************** Getting username *****************************************/
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\nPlease enter your username : ");
            username = scanner.nextLine();
            boolean flag = true;
            String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
            String userName = "postgres";
            String passWord = "saleh791378";
            try {
                Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
                MyLogger.getLogger().log("\t\tInfo\t\tLogIn\t\tConnected to database");
                String sql = "SELECT * FROM users";
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(sql);
                while (results.next()){
                    String temp = results.getString("username");
                    if (temp.equals(username)){
                        userId = results.getInt("user_id");
                        flag = false;
                        break;
                    }
                }
                while (flag){
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\nThere is no account with this "
                            + "username!\nTry again" + ConsoleColors.RESET);
                    System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter the username : " + ConsoleColors.RESET);
                    username = scanner.nextLine();
                    String sql1 = "SELECT * FROM users";
                    Statement statement1 = connection.createStatement();
                    ResultSet results1 = statement1.executeQuery(sql1);
                    while (results1.next()){
                        String temp = results1.getString("username");
                        if (temp.equals(username)){
                            userId = results1.getInt("user_id");
                            flag = false;
                            break;
                        }
                    }
                }
                MyLogger.getLogger().log("\t\tInfo\t\tLogIn\t\tusername of the user checked");
                connection.close();
            } catch (SQLException e){
                MyLogger.getLogger().log("\t\tError\t\tLogIn\t\tError in connecting to database");
                e.printStackTrace();
            }

            user = getData.getUser(userId);                                          // Creating object of user
            if (!user.isActive){
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.print(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\nyour account is deactivated!"
                        + "\nDo you want to activate it? (y/n) : " + ConsoleColors.RESET);
                String text = "";
                text = scanner.nextLine();
                while (!text.equals("y") && !text.equals("n")){
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again"
                            + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "Do you want to activate your account? (y/n) : "
                            + ConsoleColors.RESET);
                    text = scanner.nextLine();
                }
                if (text.equals("y")){
                    MyLogger.getLogger().log("\t\tInfo\t\tLogIn\t\tUser " + user.id + " activated account");
                    user.isActive = true;
                    try {
                        Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
                        MyLogger.getLogger().log("\t\tInfo\t\tLogIn\t\tConnected to database");
                        String sql1 = "UPDATE users SET is_active = 'true' WHERE user_id = " + user.id;
                        Statement statement1 = connection.createStatement();
                        int rows1 = statement1.executeUpdate(sql1);
                        if (rows1 > 0){
                            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Your account activated successfully!"
                                    + ConsoleColors.RESET);
                            Commands.delay(2000);
                        }
                        MyLogger.getLogger().log("\t\tInfo\t\tLogIn\t\tAccount of user " + user.id + " activated");
                        connection.close();
                    } catch (SQLException e){
                        MyLogger.getLogger().log("\t\tError\t\tLogIn\t\tError in connecting to database");
                        e.printStackTrace();
                    }
                }
                else {
                    MyLogger.getLogger().log("\t\tInfo\t\tLogIn\t\tUser " + user.id + " logged out");
                    System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Wish to see you again!"
                            + ConsoleColors.RESET);
                    Commands.delay(2000);
                    MyLogger.getLogger().log("\t\tInfo\t\tLogIn\t\tProject ended");
                    System.exit(0);
                }
            }
            /***************************************** Getting password *****************************************/
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\nPlease enter your password : "
                    + ConsoleColors.RESET);
            password = scanner.nextLine();
            while (!password.equals(user.password)){
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\nThe password you've entered "
                        +"is incorrect!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your password : " + ConsoleColors.RESET);
                password = scanner.nextLine();
            }
            MyLogger.getLogger().log("\t\tInfo\t\tLogIn\t\tUser " + user.id + " logged in");
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\nHi " + user.name
                    + "!\nWelcome back to twitter :)" + ConsoleColors.RESET);
            Commands.delay(2000);
            break;
        }
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        return user;
    }                                              // Done
}
