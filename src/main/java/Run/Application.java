package Run;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.spi.AbstractResourceBundleProvider;

import Color.*;
import Database.*;
import Logging.MyLogger;
import Objects.*;
import Pages.*;
import SignIn.*;

public class Application {

    public static void main(String[] args) throws IOException {
        runner();
    }

    public static void runner(){
        User user = null;
        boolean flag = true;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        MyLogger.getLogger().log("\t\tInfo\t\tApplication\t\tProject started");
        while (flag){
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\t\t\t\t\t***** Twitter *****\n\n\n\n\n\n\n\n\n"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Do you have an account? (y/n)" + ConsoleColors.RESET);
            Scanner scanner = new Scanner(System.in);
            String initial = scanner.nextLine();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
            if (initial.equals("y")){
                user = LogIn.login();
                flag = false;
            }                                                                           // Login
            else if (initial.equals("n")){
                user = SignUp.action();
                flag = false;
            }                                                                           // Sign up
            else {
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\t\tInvalid command!\n\nTry again" + ConsoleColors.RESET);
                flag = true;
            }                                     // Invalid input
        }                                                                               // Login or Sign up a user
        Commands.actions(user);
    }       // Done
}


