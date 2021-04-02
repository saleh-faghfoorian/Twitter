package Objects;

import Color.ConsoleColors;
import Database.DeleteData;
import Database.Submit;
import Database.getData;
import Logging.MyLogger;
import Pages.PersonalPage;
import Run.Commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class List {

    // Fields
    public String name;
    public int id;
    public int ownerId;
    public ArrayList<Integer> users = new ArrayList<>();

    // Constructor
    public List(int ownerId, String name){
        this.ownerId = ownerId;
        this.name    = name;
    }

    public static void action(User viewer){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Scanner scanner = new Scanner(System.in);
        int choice;
        getData.getLists(viewer);
        if (viewer.lists.isEmpty()){
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "There is no personal list to show\n1. Create a new list"
                    + "\n0. Return" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
            while (!(choice == 0 || choice == 1)){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
            if (choice == 1){
                List.newList(viewer);
            }
        }
        else {
            for (int i = 0; i < viewer.lists.size(); i++){
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i + 1) + ". " + viewer.lists.get(i).name
                        + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n" + (viewer.lists.size() + 1) + ". Create a new list"
                    + "\n0. Return" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
            while (!(choice >= 0 || choice <= viewer.lists.size() + 1)){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
            if (choice == viewer.lists.size() + 1){
                List.action(viewer);
            }
            else if (choice >= 1 && choice <= viewer.lists.size()){
                viewer.lists.get(choice - 1).show(viewer);
            }
        }
        if (choice == 0){
            PersonalPage.action(viewer);
        }
    }

    public void show(User viewer){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        if (this.users.isEmpty()){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "There is no user in " + this.name);
            Commands.delay(2000);
            List.action(viewer);
        }
        else {
            for (int i = 0; i < this.users.size(); i++){
                User temp = getData.getUser(this.users.get(i));
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i+1) + ". " + temp.name + " " + temp.lastName
                        + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n\n" + (this.users.size() + 1) + ". Delete list\n"
                    + (this.users.size() + 2) + ". Delete user from list\n" + (this.users.size() + 3) + ". Add user to list\n"
                    + "0. Return" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            int choice = scanner.nextInt();
            while (!(choice >= 0 && choice <= this.users.size() + 3)){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
            if (choice == 0){
                List.action(viewer);
            }
            else if (choice == this.users.size() + 1){
                DeleteData.deleteList(viewer.id, this.name);
                List.action(viewer);
            }
            else if (choice == this.users.size() + 2){
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Enter the number of user you want to remove from list : "
                        + ConsoleColors.RESET);
                int choice2 = scanner1.nextInt();
                while (!(choice2 >= 1 && choice2 <= this.users.size())){
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                    System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                    choice2 = scanner.nextInt();
                }
                this.removeUser(this.users.get(choice2 - 1));
            }
            else if (choice == this.users.size() + 3) {
                boolean flag = false;
                int counter = 1;
                ArrayList<Integer> AddUserIds = new ArrayList<>();
                for (int i = 0; i < viewer.followings.size(); i++){
                    if (!this.users.contains(viewer.followings.get(i))){
                        User temp = getData.getUser(viewer.followings.get(i));
                        AddUserIds.add(temp.id);
                        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + counter + ". " + temp.name + " " + temp.lastName
                                + ConsoleColors.RESET);
                        flag = true;
                        counter++;
                    }
                }
                if (!flag){
                    System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "There is no following to add to the list!"
                            + ConsoleColors.RESET);
                    List.action(viewer);
                }
                else {
                    System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one user to add : " + ConsoleColors.RESET);
                    int choice1 = scanner1.nextInt();
                    while (!(choice1 >= 0 && choice1 <= counter)){
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one user : " + ConsoleColors.RESET);
                        choice1 = scanner.nextInt();
                    }
                    this.addUser(AddUserIds.get(choice1 - 1));
                }
                List.action(viewer);
            }
            else {
                User chosen = getData.getUser(this.users.get(choice - 1));
                getData.getFollowers(chosen);
                getData.getFollowings(chosen);
                chosen.showProfile(viewer);
                List.action(viewer);
            }
        }
    }

    public static void newList(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Enter the name of list : " + ConsoleColors.RESET);
        String name = scanner.nextLine();
        boolean nameUsed = false;
        while (true){
            for (int i = 0; i < viewer.lists.size(); i++){
                if (viewer.lists.get(i).name.equals(name)){
                    nameUsed = true;
                    break;
                }
            }
            if (nameUsed){
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "This name is reserved before!\nTry another name");
                name = scanner.nextLine();
            }
            else {
                break;
            }
        }
        List list = new List(viewer.id, name);
        if (viewer.followings.isEmpty()){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "There is no following to add to list " + name
                    + ConsoleColors.RESET);
            Commands.delay(2000);
            List.action(viewer);
        }
        else {
            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\n\n\n\n\n\nChoose one user to add to list"
                    + ConsoleColors.RESET);
            for (int i = 0; i < viewer.followings.size(); i++){
                User temp = getData.getUser(viewer.followings.get(i));
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i + 1) + ". " + temp.name + " " + temp.lastName);
            }
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
            int choice = scanner.nextInt();
            while (!(choice >= 1 && choice <= viewer.followings.size())){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
            list.addUser(viewer.followings.get(choice - 1));
            viewer.lists.add(list);
            Submit.submitList(list);
            List.action(viewer);
        }
    }

    public void addUser(int userId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql = "INSERT INTO lists (name, owner, user_id)"
                    + " VALUES ('" + this.name + "', '" + this.ownerId + "', '" + userId + "')";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tUser " + userId + " added to list " + this.name);
                Commands.delay(3000);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tAdding user " + userId + " to lists "
                        + this.name + " failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public void removeUser(int userId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tList\t\tConnected to database");
            String sql = "DELETE FROM lists WHERE name = '" + this.name + "' AND user_id = " + userId;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tUser " + userId + " deleted from list " + this.name);
                Commands.delay(3000);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tDeleting user " + userId + " from list "
                        + this.name + "failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }

    }


}
