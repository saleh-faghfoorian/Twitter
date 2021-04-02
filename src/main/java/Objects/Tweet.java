package Objects;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import Color.*;
import Database.*;
import Logging.MyLogger;
import Pages.*;
import Run.*;
import SignIn.*;


public class Tweet {

    // Fields
    public int id, numberOfRetweets;
    public User user;
    public String text;
    public LocalDateTime date;
    public Report report;

    public ArrayList<Integer> comments = new ArrayList<>();
    public ArrayList<Integer> likesId  = new ArrayList<>();
    public ArrayList<Integer> retweetedUsers = new ArrayList<>();

    // Constructor
    public Tweet(User user, String text) {
        this.user   = user;
        this.text     = text;
        this.numberOfRetweets = 0;
        report = new Report(0, 0, 0, 0, 0);
        this.date = LocalDateTime.now();
    }

    public void showComments(User viewer, boolean isTweet){
        Scanner scanner = new Scanner(System.in);
        int choice;
        ArrayList<Comment> comments = new ArrayList<>();
        int numberOfComments = this.comments.size();
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tTweet\t\tConnected to database");
            /************************************ Getting comments on this **************************************/
            String sql1 = "SELECT * FROM tweets WHERE father_id = " + this.id + " AND is_tweet = 'false'";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            while (resultSet1.next()){
                int fatherId        = this.id;
                int id              = resultSet1.getInt("id");
                String text         = resultSet1.getString("text");
                int userId          = resultSet1.getInt("user_id");
                LocalDateTime dt    = resultSet1.getTimestamp("date").toLocalDateTime();
                int retweets        = resultSet1.getInt("retweets");
                Comment comment = null;

                /******************************* Getting father type of comments ********************************/
                if (isTweet){
                    comment = new Comment(null, text, fatherId, true);
                }
                else {
                    comment     = new Comment(null, text, fatherId, false);
                }
                comment.date = dt;
                comment.id   = id;
                comment.numberOfRetweets = retweets;
                /******************************** Updating objects of comments **********************************/
                String sql2 = "SELECT * FROM users WHERE user_id = " + userId;
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(sql2);
                while (resultSet2.next()){
                    comment.user = getData.getUser(resultSet2.getInt("user_id"));
                }
                comments.add(comment);
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tTweet\t\tError in connecting to database");
            e.printStackTrace();
        }
        if (numberOfComments == 0){
            System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Nobody has commented on this tweet yet!"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n0. Return to tweet" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
            while (choice != 0){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
        }
        else {
            for (int i = 0; i < numberOfComments; i++){
                String[] splitText = comments.get(i).text.split(" ");
                String partOfText;
                if (splitText.length >= 4){
                    partOfText = splitText[0] + " " + splitText[1] + " " + splitText[2]
                            + " " + splitText[3] + " ...";
                }
                else {
                    partOfText = comments.get(i).text;
                }
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + (i+1) + ". " + comments.get(i).user.name + " "
                        + comments.get(i).user.lastName + " :\t" + partOfText + "\t\t\t\t"
                        + comments.get(i).date.getYear() + "-" + comments.get(i).date.getMonthValue() + "-"
                        + comments.get(i).date.getDayOfMonth() + " " + comments.get(i).date.getHour() + ":"
                        + comments.get(i).date.getMinute() + ":" + comments.get(i).date.getSecond()
                        + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n0. Return" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : ");
            int choice1 = scanner.nextInt();
            while (!(choice1 >= 0 && choice1 <= numberOfComments)){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\nWrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : ");
                choice1 = scanner.nextInt();
            }
            if (choice1 != 0){
                comments.get(choice1 - 1).show(viewer);
            }
        }
    }

    public void deleteTweet(){
        this.user.tweets.remove(this);
        DeleteData.deleteTweet(this.id);
    }

    public void newComment(User viewer, boolean isCommentOfTweet){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\t\t***** Comment *****\n"
                + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter the text :" + ConsoleColors.RESET);
        String text = scanner.nextLine();
        Comment comment = new Comment(viewer, text, this.id, isCommentOfTweet);
        comment.id = Submit.submitComment(comment);
        this.comments.add(comment.id);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\nComment submitted!" + ConsoleColors.RESET);
        Commands.delay(1500);
    }

    public void show(User viewer){
        String date = this.date.getYear()   + "-" + this.date.getMonthValue() + "-"
                + this.date.getDayOfMonth() + " " + this.date.getHour()       + ":"
                + this.date.getMinute()     + ":" + this.date.getSecond();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + this.user.name + " "
                + this.user.lastName + " :\t" + this.text + "\t\t" + date + ConsoleColors.RESET
                + ConsoleColors.GREEN_BOLD_BRIGHT + "\n\t\t\t" + this.likesId.size() + " likes\t\t"
                + this.numberOfRetweets + " retweets" + ConsoleColors.RESET);
        this.options(viewer, true);
    }

    public void options(User viewer, boolean isTweet){
        Scanner scanner = new Scanner(System.in);
        boolean hasLikedBefore;
        String save = "", like = "", command = "";


        if (viewer.savedMessages.tweets.contains(this)){
            save = "7. Delete from saved tweets";
        }                           // show delete from save
        else {
            save = "7. Save tweet";
        }                                                                 // show save


        if (this.likesId.contains(viewer.id)){
            like = "1. Dislike";
            hasLikedBefore = true;
        }                                 // Show dislike
        else {
            like = "1. Like";
            hasLikedBefore = false;
        }                                                                 // Show like


        int threshold = 8;
        if (this.user.id != viewer.id){
            if (viewer.mutedUsers.contains(this.user.id)){
                command = "\n9. Unmute this user\n10. Report";
            }
            else {
                command = "\n9. Mute this user\n10. Report";
            }
            threshold = 10;
        }


        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n" + like + "\n2. Show likes\n3. Comment"
                + "\n4. Show Comments\n5. Retweet\n6. Forward\n" + save + "\n8. Go to user page" + command
                + "\n0. Return" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= threshold)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }


        switch (choice){
            case 1:{
                if (hasLikedBefore){
                    this.dislike(viewer);
                }
                else {
                    this.like(viewer);
                }
                this.show(viewer);
                break;
            }        // Like or Dislike  ----------> Done
            case 2:{
                ArrayList<String> likes = this.showLikes();
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "0. Return" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                int choice1 = scanner.nextInt();
                while (!(choice1 >= 0 && choice1 <= this.likesId.size())){
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again"
                            + ConsoleColors.RESET);
                    System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                    choice1 = scanner.nextInt();
                }
                if (choice1 != 0){
                    String[] temp = likes.get(choice1 - 1).split(" ");
                    int userId = Integer.parseInt(temp[0]);
                    User temp1 = getData.getUser(userId);
                    getData.getFullData(temp1);
                    temp1.showProfile(viewer);
                }
                this.show(viewer);
                break;
            }        // Show likes       ----------> Done
            case 3:{            // Comment on tweet
                this.newComment(viewer, isTweet);
                this.show(viewer);
                break;
            }        // Comment          ----------> Done
            case 4:{
                this.showComments(viewer, isTweet);
                this.show(viewer);
                break;
            }        // Show comments    ----------> Done
            case 5:{
                this.retweet(viewer);
                this.show(viewer);
                break;
            }        // Retweet          ----------> Done
            case 6:{            // Forward tweet
                this.forward(viewer);
                this.show(viewer);
                break;
            }        // Forward          ----------> Done
            case 7:{
                this.save(viewer);
                this.show(viewer);
                break;
            }        // Save tweet       ----------> Done
            case 8:{
                getData.getFollowings(this.user);
                getData.getFollowers(this.user);
                this.user.showProfile(viewer);
                this.show(viewer);
                break;
            }        // Go to user page  ----------> Done
            case 9:{
                this.user.mute(viewer);
                break;
            }        // Mute user        ----------> Done
            case 10:{
                this.report(viewer);
                this.show(viewer);
                break;
            }       // Report tweet     ----------> Done
        }
    }

    public void like(User viewer){
        this.likesId.add(viewer.id);
        Submit.submitLike(viewer.id, this.id);
    }

    public void dislike(User viewer){
        DeleteData.dislikeTweet(viewer.id, this.id);
        this.likesId.remove((Integer) viewer.id);
    }

    public ArrayList<String> showLikes(){
        ArrayList<String> likes = new ArrayList<>();
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            //System.out.println("Connected to the server");                 // Should be replaced by logging
            /************************************ Getting users liked tweet *************************************/
            String sql1 = "SELECT * FROM likes WHERE id = " + this.id;
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            while (resultSet1.next()){
                int userId = resultSet1.getInt("user_id");
                /************************************ Adding users to list **************************************/
                String sql2 = "SELECT * FROM users WHERE user_id = " + userId;
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(sql2);
                while (resultSet2.next()){
                    likes.add(userId + " " + resultSet2.getString("name")
                            + " " + resultSet2.getString("lastname"));
                }
            }
            connection.close();
        } catch (SQLException e){
            //System.out.println("Error in connecting to server");          // Should be replaced by logging
            e.printStackTrace();
        }
        if (this.likesId.size() == 0){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Nobody has liked this tweet yet!" + ConsoleColors.RESET);
        }
        else {
            for (int i = 0; i < this.likesId.size(); i++){
                String[] temp = likes.get(i).split(" ");
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + temp[1] + " " + temp[2]
                        + " liked this." + ConsoleColors.RESET);
            }
        }
        return likes;
    }

    public void retweet(User viewer){
        Tweet tweet = new Tweet(viewer, this.text);
        tweet.id = Submit.submitTweet(viewer.id, tweet);
        viewer.tweets.add(tweet);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\ntweet Retweeted!"
                + ConsoleColors.RESET);
        this.numberOfRetweets++;
        MyLogger.getLogger().log("\t\tInfo\t\tTweet\t\tUser " + user.id + " retweeted tweet " + this.id);
        Submit.updateRetweet(this.id);
    }

    public void forwardToContact(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Select your contact : " + ConsoleColors.RESET);

        for (int i = 0; i < viewer.followings.size(); i++){
            User temp = getData.getUser(viewer.followings.get(i));
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i + 1) + ". " + temp.name + " " + temp.lastName
                    + ConsoleColors.RESET);
        }
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 1 && choice <= viewer.followings.size())){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        User contact = getData.getUser(viewer.followings.get(choice - 1));
        getData.getFullData(contact);
        int chatIndex = -1;
        for (int i = 0; i < viewer.chats.size(); i++){
            if (viewer.chats.get(i).contact.id == contact.id){
                chatIndex = i;
                break;
            }
        }
        PM firstPM = new PM(viewer, contact, this.text, LocalDateTime.now());
        if (chatIndex == -1){
            Chat myNewChat = new Chat(viewer, contact);
            myNewChat.pms.add(firstPM);
            viewer.chats.add(myNewChat);
        }
        else {
            viewer.chats.get(chatIndex).pms.add(firstPM);
        }
        Submit.submitPM(firstPM);
        MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tUser " + viewer.id + " forwarded tweet " + this.id + " to "
                + contact.id);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\nTweet forwarded to " + contact.name + " "
                + contact.lastName + ConsoleColors.RESET);
    }

    public void save(User viewer){
        if (viewer.savedMessages.tweets.contains(this)){
            viewer.savedMessages.tweets.remove((Tweet) this);
            DeleteData.deleteSavedTweet(viewer.id, this.id);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\nTweet deleted from saved tweets!"
                    + ConsoleColors.RESET);
        }
        else {
            viewer.savedMessages.tweets.add(this);
            Submit.submitSave(viewer.id, this.id);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\nTweet saved!" + ConsoleColors.RESET);
        }
    }

    public void report(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\t\t***** Report *****" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n1. Report spam\n2. Report dangerous organization"
                + "\n3. Report child abuse\n4. Report bullying\n5. Report scam\n\n0. Return" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= 5)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\nWrong command!\nTry again"
                    + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\t\t***** Report *****" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "\n1. Report spam\n2.Report dangerous organization"
                    + "\n3. Report child abuse\n4. Report bullying\n5. Report scam\n\n0. Return" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        switch (choice){
            case 1:{
                this.report.spam++;
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\nSpam reported!"
                        + "\nThanks for your report" + ConsoleColors.RESET);
                Submit.submitReport(this.id, "spam");
                break;
            }
            case 2:{
                this.report.dangerousOrganization++;
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\nDangerous organization reported!"
                        + "\nThanks for your report" + ConsoleColors.RESET);
                Submit.submitReport(this.id, "dangerous_organization");
                break;
            }
            case 3:{
                this.report.childAbuse++;
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\nChild abuse reported!"
                        + "\nThanks for your report" + ConsoleColors.RESET);
                Submit.submitReport(this.id, "child_abuse");
                break;
            }
            case 4:{
                this.report.bullying++;
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\nBullying reported!"
                        + "\nThanks for your report" + ConsoleColors.RESET);
                Submit.submitReport(this.id, "bullying");
                break;
            }
            case 5:{
                this.report.scam++;
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\nScam reported!"
                        + "\nThanks for your report" + ConsoleColors.RESET);
                Submit.submitReport(this.id, "scam");
                break;
            }
        }
        Commands.delay(2000);
        this.show(viewer);
    }                                   // Report

    public void forwardToList(User viewer){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        getData.getLists(viewer);
        Scanner scanner = new Scanner(System.in);
        if (viewer.lists.isEmpty()){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You have no list to forward to" + ConsoleColors.RESET);
        }
        else {
            for (int i = 0; i < viewer.lists.size(); i++){
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i+1) + ". " + viewer.lists.get(i).name);
            }
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one list to forward message to all of them : "
                    + ConsoleColors.RESET);
            int choice = scanner.nextInt();
            while (!(choice >= 1 && choice <= viewer.lists.size())){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one list to forward message to all of them : "
                        + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
            for (int i = 0; i < viewer.lists.get(choice - 1).users.size(); i++){
                User contact = getData.getUser(viewer.lists.get(choice - 1).users.get(i));
                getData.getFullData(contact);

                int chatIndex = -1;
                for (int j = 0; j < viewer.chats.size(); j++){
                    if (viewer.chats.get(j).contact.id == contact.id){
                        chatIndex = j;
                        break;
                    }
                }
                PM firstPM = new PM(viewer, contact, this.text, LocalDateTime.now());
                if (chatIndex == -1){
                    Chat myNewChat = new Chat(viewer, contact);
                    myNewChat.pms.add(firstPM);
                    viewer.chats.add(myNewChat);
                }
                else {
                    viewer.chats.get(chatIndex).pms.add(firstPM);
                }
                Submit.submitPM(firstPM);
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tUser " + viewer.id + " forwarded tweet " + this.id
                        + " to list" + viewer.lists.get(choice - 1).name);
            }
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Tweet forwarded to list" + ConsoleColors.RESET);
            Commands.delay(1500);
        }
        this.show(viewer);
    }

    public void forward(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Forward to user\n2. Forward to list" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
        String choice = scanner.nextLine();
        while (!(choice.equals("1") || choice.equals("2"))){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Forward to user\n2. Forward to list" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
            choice = scanner.nextLine();
        }
        if (choice.equals("1")){
            this.forwardToContact(viewer);
        }
        else {
            this.forwardToList(viewer);
        }
        this.show(viewer);
    }

}

