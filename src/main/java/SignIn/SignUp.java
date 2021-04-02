package SignIn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import Color.*;
import Database.*;
import Logging.MyLogger;
import Objects.*;
import Pages.*;
import Run.*;

public class SignUp {

    public static User action(){
        Scanner scanner = new Scanner(System.in);
        String name, lastname, emailAddress, username, password;
        User user;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\t\t\t\t******* Sign up *******\n\n\n\n\n\n\n\n"
                + ConsoleColors.RESET);

        /********************************************* Getting name *********************************************/
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your name : ");
        name = scanner.nextLine();

        /******************************************* Getting lastname *******************************************/
        System.out.print("\nPlease enter your last name : " + ConsoleColors.RESET);
        lastname     = scanner.nextLine();

        /******************************************** Getting E-mail ********************************************/
        emailAddress = getEmailAddress();

        /******************************************* Getting username *******************************************/
        username     = getUsername();

        /******************************************* Getting password *******************************************/
        password     = getPassword();

        /**************************************** Getting date of birth *****************************************/
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\nDate of birth (Optional)\n1. Enter the date of birth" +
                " \n2. Skip" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice1 = scanner.nextInt();
        int year = 0, month = 0, day = 0;
        boolean hasDateOfBirth = false;
        while (!(choice1 >= 1 && choice1 <= 2)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\nDate of birth (Optional)\n1. Enter the date of "
                    + "birth\n" + "2. Skip" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice1 = scanner.nextInt();
        }                       // Checking for correct command
        if (choice1 == 1){
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Year : ");
            year = scanner.nextInt();
            System.out.print("Month : ");
            month = scanner.nextInt();
            System.out.print("Day : ");
            day = scanner.nextInt();
            hasDateOfBirth = true;
        }                                             // Getting date of birth
        /*********************************** Getting phone number (optional) ************************************/
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\nPhone number (optional)"
                + "\n1. Enter your phone number\n2. Skip" + ConsoleColors.RESET);
        String phoneNumber = "";
        int choice2 = scanner.nextInt();
        while (!(choice2 >= 1 && choice2 <= 2)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "Phone number (optional)\n1. Enter your phone number"
                    + "\n2. Skip" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice2 = scanner.nextInt();
        }                       // Checking for correct command
        Scanner scanner1 = new Scanner(System.in);
        if (choice2 == 1){
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your phone number : "
                    + ConsoleColors.RESET);
            phoneNumber = scanner1.nextLine();
            while (!validPhoneNumber(phoneNumber)){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong phone number!\nTry again"
                        + ConsoleColors.RESET);
                Commands.delay(1500);
                System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your phone number : "
                        + ConsoleColors.RESET);
                phoneNumber = scanner1.nextLine();
            }
        }                                             // Getting valid phone number
        /********************************************** Getting bio *********************************************/
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n" + "Biography (optional)"
                + "\n1. Enter your bio\n2. Skip" + ConsoleColors.RESET);
        int choice3 = scanner.nextInt();
        while (!(choice3 >= 1 && choice3 <= 2)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Phone number (optional)\n1. Enter your phone number"
                    + "\n2. Skip" + ConsoleColors.RESET);
            choice3 = scanner.nextInt();
        }                       // Checking for correct command
        String bio = "";
        if (choice3 == 1){
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter your bio : " + ConsoleColors.RESET);
            bio = scanner1.nextLine();
        }                                             // Getting bio
        /************************************************ Rules *************************************************/
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\n\n\n\n\n\n\n \t\t ******* Rules *******\n\n");
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT+ "1. All of your information will be saved in the database"
                + " but we provide security for them.\n" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "2. Tweets including spam, dangerous organization,"
                + " child abuse, bullying or scam can be reported and if more than\n 50 people report the"
                + " same issue for a tweet, we will check it out and delete it if needed.\n" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "3. Bullying somebody is one of the types of report.\n"
                + "But since one of the aims of creation of this application is to provide chances for single people!,"
                + "if you are single\nand you want to follow a user but you are blocked, don't worry!"
                + " Just tell the issue to the owner :)\n" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "4. Unfortunately sometimes you hate somebody, but you"
                + " have to follow him and see his fu*king tweets :/\nBut don't worry! Just mute it and enjoy.\n"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\nI've read all rules and accept all of them. (y/n)"
                + ConsoleColors.RESET);
        String y;
        y = scanner1.nextLine();
        while (!y.equals("y")){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Sorry!\nAll users have to obey the rules!"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "I've read all rules and accept all of them. (y/n)"
                    + ConsoleColors.RESET);
            y = scanner1.nextLine();
        }                                       // Checking for correct command
        /********************************************* Creating user ********************************************/
        user  = new User(name, lastname, emailAddress, username, password, true);
        if (choice1 == 1){
            user.dateOfBirth = LocalDate.of(year, month, day);
        }                                            // Setting date of birth
        if (choice2 == 1){
            user.phoneNumber = phoneNumber;
        }                                            // Setting phone number
        if (choice3 == 1){
            user.bio = bio;
        }                                            // Setting bio
        MyLogger.getLogger().log("\t\tInfo\t\tSignUp\t\tUser " + user.id + " registered");
        /************************************* Submitting user into database ************************************/
        int userId;
        if (hasDateOfBirth){
            userId = Submit.submitUser(user, true);
        }
        else {
            userId = Submit.submitUser(user, false);
        }
        user.id    = userId;
        user.lastSeen = LocalDateTime.now();
        user.isPublic = true;
        user.lastSeenMode = 1;

        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\t\t ****** You've signed up successfully! "
                + "******\n");
        System.out.println("\t\t ******* Welcome to twitter *******\n\n" + ConsoleColors.RESET);
        Commands.delay(2000);
        return user;
    }

    public static String getEmailAddress(){
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\nPlease enter your email address : " + ConsoleColors.RESET);
        Scanner scanner = new Scanner(System.in);
        String emailAddress;
        while (true){
            emailAddress = scanner.nextLine();
            if (!validEmailAddress(emailAddress)){
                System.out.print(ConsoleColors.RED_BOLD_BRIGHT + "\nThe email address you've entered is not valid!"
                        + ConsoleColors.RESET);
                System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\nPlease enter a valid email address : "
                        + ConsoleColors.RESET);
            }
            else if (emailExists(emailAddress)){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\nUnfortunately There is an account for"
                        + " this email address!" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter a new email address : "
                        + ConsoleColors.RESET);
            }
            else {
                break;
            }
        }
        return emailAddress;
    }

    public static String getUsername(){
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\nPlease enter your username : " + ConsoleColors.RESET);
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        ArrayList<String> usernames = getData.getUsernames();
        while (usernames.contains(username)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\nThe username you've entered has been"
                    + " reserved!\nTry again" + ConsoleColors.RESET);
            Commands.delay(1500);
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\nPlease enter the username again : "
                    + ConsoleColors.RESET);
            username = scanner.nextLine();
        }
        usernames.clear();
        return username;
    }

    public static String getPassword(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Notice : Your password must contain at least 8"
                + " characters using alphabets and digits" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Enter your password : " + ConsoleColors.RESET);
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        while (password.length() < 8 || !validPassword(password)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "The password is not valid!\nTry again"
                    + ConsoleColors.RESET);
            Commands.delay(1500);
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Enter your password : " + ConsoleColors.RESET);
            password = scanner.nextLine();
        }
        return password;
    }

    public static boolean validEmailAddress(String emailAddress){
        boolean flag = true;
        if (!emailAddress.contains("@")){
            flag = false;
        }
        else {
            int index = emailAddress.indexOf('@');
            String temp = emailAddress.substring(index, emailAddress.length());
            if (!temp.contains(".")){
                flag = false;
            }
        }
        return flag;
    }              // Done

    public static boolean emailExists(String emailAddress){
        boolean flag = false;
        ArrayList<String> emailAddresses = getData.getEmails();
        if (emailAddresses.contains(emailAddress)){
            flag = true;
        }
        emailAddresses.clear();
        return flag;
    }                    // Done

    public static boolean validPassword(String password){
        boolean flag1 = false, flag2 = false;
        for (int i = 0; i < password.length(); i++){
            if (Character.isLetter(password.charAt(i))){
                flag1 = true;
            }
            else if (Character.isDigit(password.charAt(i))){
                flag2 = true;
            }
        }
        if (flag1 && flag2){
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean validPhoneNumber(String phoneNumber){
        boolean isValid = true;
        for (int i = 0; i < phoneNumber.length(); i++){
            if (!Character.isDigit(phoneNumber.charAt(i))){
                isValid = false;
            }
        }
        return isValid;
    }
}

