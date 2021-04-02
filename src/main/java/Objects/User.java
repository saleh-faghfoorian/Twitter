package Objects;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import Color.*;
import Logging.MyLogger;
import SignIn.*;
import Database.*;
import Pages.*;
import Run.*;

public class User {

    // Fields
    public String name, lastName, emailAddress, userName, password, phoneNumber, bio;
    public int id;
    public boolean isActive, isPublic;
    public LocalDate dateOfBirth;
    public LocalDateTime lastSeen;
    public int lastSeenMode;
    public SavedMessages savedMessages;
    /**
     * if (lastSeenMode == 1) -> Everybody
     * if (lastSeenMode == 2) -> Nobody
     * if (lastSeenMode == 3) -> Followers
     **/


    public ArrayList<Chat>    chats;
    public ArrayList<Tweet>   tweets;
    public ArrayList<Integer> followings;
    public ArrayList<Integer> followers;
    public ArrayList<Integer> blockedUsers;
    public ArrayList<Integer> requestedUsers;
    public ArrayList<Integer> requested;
    public ArrayList<Integer> mutedUsers;
    public ArrayList<String>  notifications;
    public ArrayList<List>    lists;

    // Constructor
    public User(String name, String lastName, String emailAddress, String userName, String password, boolean isActive) {
        this.name           = name;
        this.lastName       = lastName;
        this.emailAddress   = emailAddress;
        this.userName       = userName;
        this.password       = password;
        this.isActive       = true;
        this.tweets         = new ArrayList<>();
        this.followers      = new ArrayList<>();
        this.followings     = new ArrayList<>();
        this.blockedUsers   = new ArrayList<>();
        this.requested      = new ArrayList<>();
        this.chats          = new ArrayList<>();
        this.requestedUsers = new ArrayList<>();
        this.mutedUsers     = new ArrayList<>();
        this.notifications  = new ArrayList<>();
        this.lists          = new ArrayList<>();
        this.lastSeenMode   = 1;
    }

    public void showProfile(User viewer){
        if (this.id == viewer.id){
            PersonalPage.action(viewer);
        }
        Scanner scanner = new Scanner(System.in);
        int temp, threshold;
        String lastSeen = "";
        String follow = "", block = "", showTweets = "", commands = "";
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        /**
         * if (lastSeenMode == 1) -> Everybody
         * if (lastSeenMode == 2) -> Nobody
         * if (lastSeenMode == 3) -> Followers
         **/
        if (this.lastSeenMode == 1 || (this.lastSeenMode == 3 && this.followers.contains(viewer.id))){
            lastSeen = "\nLast seen at : " + this.lastSeen.getYear() + "-" + this.lastSeen.getMonthValue()
                    + "-" + this.lastSeen.getDayOfMonth() + " " + this.lastSeen.getHour() + ":"
                    + this.lastSeen.getMinute() + ":" + this.lastSeen.getSecond();
        }
        else {
            lastSeen = "\nLast seen recently";
        }

        if (this.followers.contains(viewer.id)){
            follow = "\n1. Unfollow\n";
        } else {
            follow = "\n1. Follow\n";
        }                       // Follow or unfollow
        if (viewer.blockedUsers.contains(this.id)){
            block = "2. Unblock\n";
        } else {
            block = "2. Block\n";
        }                    // Block or unblock
        if (this.isPublic || this.followers.contains(viewer.id)){
            showTweets = "3. Show tweets\n4. Send message\n0. Return\n";
            threshold = 4;
        } else {
            showTweets = "0. Return\n";
            threshold = 2;
        }      // show tweets of not

        commands = follow + block + showTweets;

        String commonUsers = "\n\n";
        for (int i = 0; i < viewer.followings.size(); i++){
            if (this.followers.contains(viewer.followings.get(i))){
                User commonUser = getData.getUser(viewer.followings.get(i));
                commonUsers = commonUsers + "Followed by " + commonUser.name + " " + commonUser.lastName + "\n";
            }
        }
        //////////////////////////////////////////   Showing Profile   ///////////////////////////////////////////
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\t\t\t***** Profile *****\n\n\n\n\n"
                + ConsoleColors.RESET);
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + this.name + " " + this.lastName + "\t("
                + this.userName + ")\n" + ConsoleColors.RESET + ConsoleColors.CYAN_BOLD_BRIGHT
                + this.followers.size() + " followers\t" + this.followings.size()
                + " followings" + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + lastSeen
                + ConsoleColors.RESET + ConsoleColors.PURPLE_BOLD_BRIGHT + commonUsers + commands
                + "\nChoose one item : " + ConsoleColors.RESET);
        temp = scanner.nextInt();
        while (!(temp >= 0 && temp <= threshold)){
            System.out.print(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET
                    + ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            temp = scanner.nextInt();
        }
        if (temp == 1){
            if (viewer.followings.contains(this.id)){
                this.unfollow(viewer);
            }
            else {
                this.follow(viewer);
            }
        }                               // Follow or unfollow
        else if (temp == 2){
            if (viewer.blockedUsers.contains(this.id)){
                this.unblock(viewer);
            } else {
                this.block(viewer);
            }
        }                          // Block of unblock
        else if (temp == 3){
            this.showTweets(viewer);
        }                          // Show tweets or not
        else if (temp == 4){
            boolean flag = true;
            for (int i = 0; i < viewer.chats.size(); i++){
                if (viewer.chats.get(i).contact.id == this.id){
                    viewer.chats.get(i).show(viewer);
                    flag = false;
                    break;
                }
            }
            if (flag){
                Scanner scanner1 = new Scanner(System.in);
                System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Write your first message : " + ConsoleColors.RESET);
                String firstMessage = scanner1.nextLine();
                PM firstPM = new PM(viewer, this, firstMessage, LocalDateTime.now());
                Chat newChat = new Chat(viewer, this);
                viewer.chats.add(newChat);
                newChat.pms.add(firstPM);
                Submit.submitPM(firstPM);
                newChat.show(viewer);
            }
        }
        else {

        }
    }

    public void newTweet(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t"
                + "***** New tweet *****\n\n\n\n\n\n" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter the text :" + ConsoleColors.RESET);
        String text = scanner.nextLine();
        Tweet tweet = new Tweet(this, text);
        tweet.id = Submit.submitTweet(this.id, tweet);
        tweet.date = LocalDateTime.now();
        this.tweets.add(tweet);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\ntweet submitted!" + ConsoleColors.RESET);
        Commands.delay(1500);
        PersonalPage.action(this);
    }

    public void showTweets(User viewer){
        int threshold = 0;
        int numberOfTweets = this.tweets.size();
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n**** tweets *****\n"
                + ConsoleColors.RESET);
        /*******************************************  Showing tweets  *******************************************/
        if (numberOfTweets == 0){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "You have no tweet yet :(\n\n\n\n\n"
                    + ConsoleColors.RESET);
        }
        else {
            for (int i = 0; i < numberOfTweets; i++){
                String date =   this.tweets.get(i).date.getYear() + "-" + this.tweets.get(i).date.getMonthValue()
                        + "-" + this.tweets.get(i).date.getDayOfMonth() + " " + this.tweets.get(i).date.getHour()
                        + ":" + this.tweets.get(i).date.getMinute() + ":" + this.tweets.get(i).date.getSecond();

                String[] splitText = this.tweets.get(i).text.split(" ");
                String partOfText;
                if (splitText.length >= 4){
                    partOfText = splitText[0] + " " + splitText[1] + " " + splitText[2]
                            + " " + splitText[3] + " ...";
                }
                else {
                    partOfText = this.tweets.get(i).text;
                }
                System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + (i+1) + ". " + this.name + " " + this.lastName
                        + " :\t" + partOfText + ConsoleColors.RESET);
                User.suitableBlanks(this.tweets.get(i));
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + date + ConsoleColors.RESET);
            }
            System.out.println("");
        }
        /*******************************************  Showing options  ******************************************/
        if (this.id == viewer.id && numberOfTweets != 0){
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n" + (numberOfTweets + 1) + ". New tweet\n"
                    + (numberOfTweets + 2) + ". Delete tweet" + ConsoleColors.RESET);
            threshold = numberOfTweets + 2;
        }               // User shows his own tweets
        else if (this.id == viewer.id && numberOfTweets == 0){
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n" + (numberOfTweets + 1) + ". New tweet\n"
                    + ConsoleColors.RESET);
            threshold = numberOfTweets + 1;
        }
        else {
            threshold = numberOfTweets;
        }                                   // User shows tweets of another user

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n0. Return to personal page" + ConsoleColors.RESET);
        Scanner scanner = new Scanner(System.in);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= threshold)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            Commands.delay(1500);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        /*********************************************  Operation  **********************************************/
        if (choice <= numberOfTweets && choice >= 1){
            this.tweets.get(choice - 1).show(viewer);
        }
        else if (choice == numberOfTweets + 1){
            viewer.newTweet();
        }
        else if (choice == numberOfTweets + 2){

            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Which tweet do you want to delete? "
                    + ConsoleColors.RESET);
            int choice1 = scanner.nextInt();
            while (!(choice1 >= 1 && choice1 <= numberOfTweets)){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Which tweet do you want to delete? "
                        + ConsoleColors.RESET);
                choice1 = scanner.nextInt();
            }
            this.tweets.get(choice1 - 1).deleteTweet();
            PersonalPage.action(viewer);
        }
        else if (choice == numberOfTweets + 3){
            PersonalPage.action(viewer);
        }
        else if (choice == 0){
            this.showProfile(viewer);
        }
    }

    public void mute(User muter){
        if (muter.mutedUsers.contains(this.id)){
            muter.mutedUsers.remove((Integer) this.id);
            DeleteData.deleteMutedUser(muter.id, this.id);
            System.out.println("User unmuted!");
        }
        else {
            muter.mutedUsers.add(this.id);
            Submit.submitMute(muter.id, this.id);
            System.out.println("User muted!");
        }
    }

    public void showList(User viewer, int mode){
        /**
         * if (mode == 1) ---------> show followers
         * if (mode == 2) ---------> show followings
         * if (mode == 3) ---------> show black list
         * */
        String column = "", identifier = "", operation = "", title = "";
        Scanner scanner = new Scanner(System.in);
        switch (mode){
            case 1:{
                column     = "user1_id";                    // followerId
                identifier = "user2_id";                    // followingId
                operation  = "follow";                      // operation
                title      = "***** Followers *****";       // title of page
                break;
            }                                    // followers
            case 2:{
                column     = "user2_id";                    // followingId
                identifier = "user1_id";                    // followerId
                operation  = "follow";                      // operation
                title      = "***** Followings *****";      // title of page
                break;
            }                                    // followings
            case 3:{
                column     = "user2_id";                    // blockedId
                identifier = "user1_id";                    // blockerId
                operation  = "block";                       // operation
                title      = "***** Black list *****";      // title of page
                break;
            }                                    // black list
        }
        ArrayList<User> list = new ArrayList<>();
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        /******************************************** getting list **********************************************/
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            //System.out.println("Connected to the server");                  // Should be replaced by logging
            /******************************************* getting id *********************************************/
            String sql1 = "SELECT " + column + " FROM follow_and_block WHERE " + identifier + " = "
                    + this.id + " AND operation = '" + operation + "'";
            Statement statement1 = connection.createStatement();
            ResultSet result1    = statement1.executeQuery(sql1);
            while (result1.next()){
                int userId = result1.getInt(column);
                User temp = null;
                /************************************** getting username ****************************************/
                String slq2 = "SELECT username FROM users WHERE user_id = " + userId;
                Statement statement2 = connection.createStatement();
                ResultSet result2    = statement2.executeQuery(slq2);
                while (result2.next()){
                    String username = result2.getString("username");
                    temp   = getData.getUser(userId);
                }
                list.add(temp);
            }
            connection.close();
        } catch (SQLException e){
            //System.out.println("Error in connecting to server");               // Should be substituted by logging
            e.printStackTrace();
        }

        if (list.size() > 0){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t" + title + "\n"
                    + ConsoleColors.RESET);
            /****************************************** showing list ********************************************/
            for (int i = 0; i < list.size(); i++){
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + (i+1) + ". " + list.get(i).name
                        + " " + list.get(i).lastName + "\t(" + list.get(i).userName + ")");
            }
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n0. Return" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            int choice1 = scanner.nextInt();
            while (!(choice1 >= 0 && choice1 <= list.size())){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                choice1 = scanner.nextInt();
            }
            if (choice1 != 0){
                getData.getFullData(list.get(choice1 - 1));
                list.get(choice1 - 1).showProfile(viewer);
            }
        }
        else {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n"
                    + "\t\t***** 0 follower *****" + ConsoleColors.RESET);
            Commands.delay(2000);
        }
    }

    public void accept(User accepter){
        accepter.followers.add(this.id);
        accepter.requestedUsers.remove((Integer) this.id);
        this.followings.add(accepter.id);
        this.requested.remove((Integer) accepter.id);
        String notificationForFollower = accepter.name + " " + accepter.lastName + " accepted your follow request.\n"
                + "now you can see profile and tweets!";
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        String notificationForFollowing = this.name + " " + this.lastName + " started following you.";
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\nUser accepted"
                + ConsoleColors.RESET);
        this.notifications.add(notificationForFollower);
        accepter.notifications.add(notificationForFollowing);
        Submit.submitAccept(this.id, accepter.id, notificationForFollower);
    }

    public void reject(User rejecter){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Send a notification for " + this.name + " "
                + this.lastName + " ? (y/n) : " + ConsoleColors.RESET);
        String choice = scanner.nextLine();
        while (!(choice.equals("y") || choice.equals("n"))){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Send a notification for " + this.name + " "
                    + this.lastName + " ? (y/n) : " + ConsoleColors.RESET);
            choice = scanner.nextLine();
        }
        String notificationForRejected = "";
        if (choice.equals("y")){
            notificationForRejected = rejecter.name + " " + rejecter.lastName + " rejected your follow request.";
            this.notifications.add(notificationForRejected);
        }
        rejecter.requestedUsers.remove((Integer) this.id);
        this.requested.remove((Integer) rejecter.id);
        Submit.submitReject(this.id, rejecter.id, notificationForRejected);
        System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + this.name + " " + this.lastName + " rejected!"
                + "\nWant to block it? (y/n) : " + ConsoleColors.RESET);
        String temp = scanner.nextLine();
        while (!(temp.equals("y") || temp.equals("n"))){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Do you want to block " + this.name + " "
                    + this.lastName + " ? (y/n) : " + ConsoleColors.RESET);
            temp = scanner.nextLine();
        }
        if (temp.equals("y")){
            this.block(rejecter);
        }
    }

    public void block(User blocker){
        /**
         * if (mode == 1) --------> block a follower
         * if (mode == 2) --------> block a requested user
         * if (mode == 3) --------> block another one
         */
        int mode;
        if (blocker.followers.contains(this.id)){
            blocker.followers.remove((Integer) this.id);
            this.followings.remove((Integer) blocker.id);
            mode = 1;
        }
        else if (blocker.requestedUsers.contains(this.id)){
            blocker.requestedUsers.remove((Integer) this.id);
            this.requested.remove((Integer) blocker.id);
            mode = 2;
        }
        else {
            mode = 3;
        }
        blocker.blockedUsers.add(this.id);
        Submit.submitBlock(blocker.id, this.id, mode);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + this.name + " " + this.lastName + " blocked!");
    }

    public void follow(User follower){
        String notification = "";
        if (this.isPublic){
            this.followers.add(follower.id);
            follower.followings.add(this.id);
            notification = follower.name + " " + follower.lastName + " started following you.";
            Submit.submitFollow(follower.id, this.id, true, notification);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "User followed!" + ConsoleColors.RESET);
        }
        else {
            if (this.requestedUsers.contains(follower.id) && follower.requested.contains(this.id)){
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You have requested to follow before!"
                        + ConsoleColors.RESET);
            }
            else {
                this.requestedUsers.add(follower.id);
                follower.requested.add(this.id);
                Submit.submitFollow(follower.id, this.id, false, notification);
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Follow request sent!"
                        + ConsoleColors.RESET);
            }
        }
        Commands.delay(2000);
    }

    public void unfollow(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        String command = "";
        if (!this.isPublic){
            command = "This account is private and if you want to follow again, you have to "
                    + "send follow request";
        }
        command = command + "\nAre you sure? (y/n)";
        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + command + ConsoleColors.RESET);
        String choice = scanner.nextLine();
        while (!(choice.equals("y") || choice.equals("n"))){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again\n\n\n\n" + command);
            choice = scanner.nextLine();
        }
        if (choice.equals("y")){
            this.followers.remove((Integer) viewer.id);
            viewer.followings.remove((Integer) this.id);
            String notification = viewer.name + " " + viewer.lastName + " unfollowed you!";
            this.notifications.add(notification);
            DeleteData.deleteFollow(viewer.id, this.id, notification);
            System.out.println(ConsoleColors.GREEN + "User unfollowed!" + ConsoleColors.RESET);
            Commands.delay(2000);
        }
    }

    public void unblock(User blocker){
        blocker.blockedUsers.remove((Integer) this.id);
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tUser\t\tConnected to database");

            String sql1 = "DELETE FROM follow_and_block WHERE user1_id = " + blocker.id + " AND user2_id = "
                    + this.id + " AND operation = 'block'";
            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);
            if (rows1 > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tUser\t\tUser " + blocker.id + " unblocked user " + this.id);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tUser\t\tUnblocking user " + this.id + " by user "
                        + blocker.id + " failed");
            }
            connection.close();

        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tUser\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void suitableBlanks(Tweet tweet){
        int length = 60 - tweet.user.name.length() - tweet.user.lastName.length() - tweet.text.length();
        for (int i = 0; i < length; i++){
            System.out.print(" ");
        }
    }
}

