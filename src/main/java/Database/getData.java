package Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import Logging.MyLogger;
import Objects.*;
import Color.*;
import Pages.*;
import SignIn.*;
import Run.*;

public class getData {


    static boolean isReported(int tweetId){
        int maximumReports = 0;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql = "SELECT * FROM reports WHERE id = " + tweetId;
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()){
                int spamReports                  = results.getInt("spam");
                int dangerousOrganizationReports = results.getInt("dangerous_organization");
                int childAbuseReports            = results.getInt("child_abuse");
                int bullyingReports              = results.getInt("bullying");
                int scamReports                  = results.getInt("scam");

                int[] reports = {spamReports, dangerousOrganizationReports, childAbuseReports,
                        bullyingReports, scamReports};

                for (int i = 0; i < 5; i++){
                    if (reports[i] > maximumReports){
                        maximumReports = reports[i];
                    }
                }
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
        if (maximumReports >= 50){
            return true;
        }
        else {
            return false;
        }
    }

    public static ArrayList<String> getUsernames(){
        ArrayList<String> usernames = new ArrayList<>();
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql = "SELECT username FROM users";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()){
                usernames.add(results.getString("username"));
            }
            statement.close();
            results.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tUsernames have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
        return usernames;
    }

    public static ArrayList<String> getEmails(){
        ArrayList<String> emailAddresses = new ArrayList<>();
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql = "SELECT email_address FROM users";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()){
                emailAddresses.add(results.getString("email_address"));
            }
            statement.close();
            results.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tE-mail addresses have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
        return emailAddresses;
    }

    public static void getTweetsOfUser(User user){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            /************************************** getting tweets of user **************************************/
            String sql1 = "SELECT * FROM tweets WHERE is_tweet = 'true' AND user_id = " + user.id;
            Statement statement1 = connection.createStatement();
            ResultSet result1    = statement1.executeQuery(sql1);
            while (result1.next()){
                int tweetId            = result1.getInt("id");
                String text            = result1.getString("text");
                Tweet tweet            = new Tweet(user, text);
                tweet.date             = result1.getTimestamp("date").toLocalDateTime();
                tweet.id               = tweetId;
                tweet.numberOfRetweets = result1.getInt("retweets");
                /*************************************** Getting reports ****************************************/
                String sql3 = "SELECT * FROM reports WHERE id = " + tweetId;
                Statement statement3 = connection.createStatement();
                ResultSet result3    = statement3.executeQuery(sql3);
                while (result3.next()){
                    tweet.report.spam                  = result3.getInt("spam");
                    tweet.report.scam                  = result3.getInt("scam");
                    tweet.report.bullying              = result3.getInt("bullying");
                    tweet.report.childAbuse            = result3.getInt("child_abuse");
                    tweet.report.dangerousOrganization = result3.getInt("dangerous_organization");
                }
                result3.close();
                statement3.close();
                /************************************* Getting commentIds ***************************************/
                String sql4 = "SELECT * FROM tweets WHERE is_tweet = 'false' AND father_id = " + tweetId;
                Statement statement4 = connection.createStatement();
                ResultSet result4    = statement4.executeQuery(sql4);
                while (result4.next()){
                    tweet.comments.add(result4.getInt("id"));
                }
                statement4.close();
                result4.close();
                /************************************** Getting retweets ****************************************/
                String sql5 = "SELECT * FROM retweets WHERE id = " + tweetId;
                Statement statement5 = connection.createStatement();
                ResultSet result5    = statement5.executeQuery(sql5);
                while (result5.next()){
                    tweet.retweetedUsers.add(result5.getInt("user_id"));
                }
                result5.close();
                statement5.close();
                user.tweets.add(tweet);
            }
            result1.close();
            statement1.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\ttweets of user " + user.id + " have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static User getUser(int userId){
        User user = null;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        /********************************************* getting user *********************************************/
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql = "SELECT * FROM users WHERE user_id = '" + userId + "'";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()){
                String name         = results.getString("name");
                String lastName     = results.getString("lastName");
                String username    = results.getString("username");
                String password     = results.getString("password");
                String emailAddress = results.getString("email_Address");
                String dateOfBirth  = results.getString("date_of_birth");
                String bio          = results.getString("bio");
                String phoneNumber  = results.getString("phone_number");
                boolean isActive    = results.getBoolean("is_active");
                boolean isPublic    = results.getBoolean("is_public");
                String lastSeen     = results.getString("last_seen");
                int lastSeenMode    = results.getInt("last_seen_mode");

                user = new User(name, lastName, emailAddress, username, password, true);
                user.id = userId;
                if (dateOfBirth != null && !dateOfBirth.isEmpty()){
                    String[] date = dateOfBirth.split("-");
                    int year  = Integer.parseInt(date[0]);
                    int month = Integer.parseInt(date[1]);
                    int day   = Integer.parseInt(date[2]);
                    LocalDate localDate  = LocalDate.of(year, month, day);
                    user.dateOfBirth = localDate;
                }
                user.phoneNumber      = phoneNumber;
                user.bio              = bio;
                user.isPublic         = isPublic;
                user.isActive         = isActive;
                user.lastSeenMode     = lastSeenMode;
                if (!(lastSeen == null)){
                    String[] dateAndTime  = lastSeen.split(" ");
                    String[] lastSeenDate = dateAndTime[0].split("-");
                    String[] lastSeenTime = dateAndTime[1].split(":");
                    int year   = Integer.parseInt(lastSeenDate[0]);
                    int month  = Integer.parseInt(lastSeenDate[1]);
                    int day    = Integer.parseInt(lastSeenDate[2]);
                    int hour   = Integer.parseInt(lastSeenTime[0]);
                    int minute = Integer.parseInt(lastSeenTime[1]);
                    int second = Integer.parseInt(lastSeenTime[2]);
                    LocalDateTime userLastSeen = LocalDateTime.of(year, month, day, hour, minute, second);
                    user.lastSeen = userLastSeen;
                }
            }
            results.close();
            statement.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tUser " + userId + " have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
        return user;
    }

    public static Tweet getTweet(int tweetId){
        Tweet tweet = null;
        User user   = null;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            /*************************************** Getting current user ***************************************/
            if (isReported(tweetId)){
                DeleteData.deleteTweet(tweetId);
                return null;
            }
            String sql = "SELECT * FROM tweets WHERE id = " + tweetId;
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()){
                int userId           = results.getInt("user_id");
                String text          = results.getString("text");
                LocalDateTime date   = results.getTimestamp("date").toLocalDateTime();
                int numberOfRetweets = results.getInt("retweets");

                String sql1 = "SELECT * FROM users WHERE user_id = " + userId;
                Statement statement1 = connection.createStatement();
                ResultSet results1 = statement1.executeQuery(sql1);
                while (results1.next()){
                    int userId1 = results1.getInt("user_id");
                    user = getUser(userId1);
                }
                results1.close();
                statement1.close();
                tweet = new Tweet(user, text);
                tweet.date = date;
                tweet.id   = tweetId;
                tweet.numberOfRetweets = numberOfRetweets;
                tweet.user = user;
                /**************************************** Getting likes *****************************************/
                String sql2 = "SELECT * FROM likes WHERE id = " + tweetId;
                Statement statement2 = connection.createStatement();
                ResultSet result2    = statement2.executeQuery(sql2);
                while (result2.next()){
                    tweet.likesId.add(result2.getInt("user_id"));
                }
                result2.close();
                statement2.close();
                /*************************************** Getting reports ****************************************/
                String sql3 = "SELECT * FROM reports WHERE id = " + tweetId;
                Statement statement3 = connection.createStatement();
                ResultSet result3    = statement3.executeQuery(sql3);
                while (result3.next()){
                    tweet.report.spam                  = result3.getInt("spam");
                    tweet.report.scam                  = result3.getInt("scam");
                    tweet.report.bullying              = result3.getInt("bullying");
                    tweet.report.childAbuse            = result3.getInt("child_abuse");
                    tweet.report.dangerousOrganization = result3.getInt("dangerous_organization");
                }
                result3.close();
                statement3.close();
                /************************************* Getting commentIds ***************************************/
                String sql4 = "SELECT * FROM tweets WHERE is_tweet = 'false' AND father_id = " + tweetId;
                Statement statement4 = connection.createStatement();
                ResultSet result4    = statement4.executeQuery(sql4);
                while (result4.next()){
                    tweet.comments.add(result4.getInt("id"));
                }
                result4.close();
                statement4.close();
                /************************************** Getting retweets ****************************************/
                String sql5 = "SELECT * FROM retweets WHERE id = " + tweetId;
                Statement statement5 = connection.createStatement();
                ResultSet result5    = statement5.executeQuery(sql5);
                while (result5.next()){
                    tweet.retweetedUsers.add(result5.getInt("user_id"));
                }
                result5.close();
                statement5.close();
            }
            statement.close();
            results.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\ttweet " + tweetId + " has gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
        return tweet;
    }

    public static String Search(int mode, String search){
        /**
         * if (mode == 1) --------> Search by name
         * if (mode == 2) --------> Search by lastname
         * if (mode == 3) --------> Search by username
         */
        String identifier = null;
        StringBuilder output = new StringBuilder();
        switch (mode){
            case 1:{
                identifier = "name";
                break;
            }
            case 2:{
                identifier = "lastname";
                break;
            }
            case 3:{
                identifier = "username";
                break;
            }
        }

        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            /*************************************** Getting current user ***************************************/
            String sql = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            while (results.next()){
                String searchObject = results.getString(identifier);
                int userId     = results.getInt("user_id");
                if (searchObject.toLowerCase(Locale.ROOT).contains(search.toLowerCase(Locale.ROOT))){
                    output.append(userId).append(" ");
                }
            }
            statement.close();
            results.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tSearch done and users found");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void getChats(User viewer){
        viewer.chats.clear();
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            for (int i = 0; i < viewer.followings.size(); i++){
                Chat tempChat = null;
                boolean isEmpty = true;
                String sql = "SELECT * FROM pms WHERE ( writer_id = " + viewer.id + " AND contact_id = "
                        + viewer.followings.get(i) + " ) OR ( writer_id = " + viewer.followings.get(i)
                        + " AND contact_id = " + viewer.id + " ) ORDER BY pm_id";
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(sql);
                while (results.next()){
                    if (isEmpty){
                        User contact = getUser(viewer.followings.get(i));
                        tempChat = new Chat(viewer, contact);
                        isEmpty = false;
                    }
                    User writer = getUser(results.getInt("writer_id"));
                    User reader = getUser(results.getInt("contact_id"));
                    String text = results.getString("text");
                    LocalDateTime date = results.getTimestamp("date").toLocalDateTime();
                    PM tempPM = new PM(writer, reader, text, date);
                    if (tempChat.contact.id == writer.id){
                        tempPM.hasRead = results.getBoolean("has_read");
                    }
                    tempChat.pms.add(tempPM);
                }
                results.close();
                statement.close();
                if (!isEmpty){
                    viewer.chats.add(tempChat);
                }
            }



            for (int i = 0; i < viewer.followers.size(); i++){
                Chat tempChat = null;
                boolean isEmpty = true;
                boolean chatExists = false;
                String sql = "SELECT * FROM pms WHERE ( writer_id = " + viewer.id + " AND contact_id = "
                        + viewer.followers.get(i) + " ) OR ( writer_id = " + viewer.followers.get(i)
                        + " AND contact_id = " + viewer.id + " ) ORDER BY pm_id";
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(sql);
                while (results.next()){
                    if (isEmpty){
                        User contact = getUser(viewer.followers.get(i));
                        for (int j = 0; j < viewer.chats.size(); j++){
                            if (viewer.chats.get(j).contact.id == contact.id){
                                chatExists = true;
                                break;
                            }
                        }
                        if (!chatExists){
                            tempChat = new Chat(viewer, contact);
                            isEmpty = false;
                        }
                    }
                    if (!chatExists){
                        User writer = getUser(results.getInt("writer_id"));
                        User reader = getUser(results.getInt("contact_id"));
                        String text = results.getString("text");
                        LocalDateTime date = results.getTimestamp("date").toLocalDateTime();
                        PM tempPM = new PM(writer, reader, text, date);
                        if (tempChat.contact.id == writer.id){
                            tempPM.hasRead = results.getBoolean("has_read");
                        }
                        tempChat.pms.add(tempPM);
                    }
                }
                results.close();
                statement.close();
                if (!isEmpty){
                    viewer.chats.add(tempChat);
                }
            }







            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tChats have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void getNotifications(User viewer){
        if (viewer.notifications != null){
            viewer.notifications.clear();
        }
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        /*************************************** Getting notification *******************************************/
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");

            String sql5 = "SELECT * FROM notification WHERE user_id = " + viewer.id;
            Statement statement5 = connection.createStatement();
            ResultSet results5 = statement5.executeQuery(sql5);
            while (results5.next()){
                viewer.notifications.add(results5.getString("notification"));
            }
            statement5.close();
            results5.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tNotifications have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }

    }

    public static void getRequests(User user){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql6 = "SELECT * FROM follow_request WHERE follower_id = " + user.id;
            Statement statement6 = connection.createStatement();
            ResultSet results6 = statement6.executeQuery(sql6);
            while (results6.next()){
                user.requested.add(results6.getInt("following_id"));
            }
            results6.close();
            statement6.close();
            String sql7 = "SELECT * FROM follow_request WHERE following_id = " + user.id;
            Statement statement7 = connection.createStatement();
            ResultSet results7 = statement7.executeQuery(sql7);
            while (results7.next()){
                user.requestedUsers.add(results7.getInt("follower_id"));
            }
            results7.close();
            statement7.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tFollow requests of user " + user.id + " have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void getNotes(User user){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        user.savedMessages = new SavedMessages(user.id);
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql4 = "SELECT * FROM save WHERE user_id = " + user.id;
            Statement statement4 = connection.createStatement();
            ResultSet results4 = statement4.executeQuery(sql4);

            while (results4.next()){
                int tweetId = results4.getInt("id");
                Tweet savedTweet = getTweet(tweetId);
                user.savedMessages.tweets.add(savedTweet);
            }
            results4.close();
            statement4.close();

            String sql5 = "SELECT * FROM note WHERE user_id = " + user.id;
            Statement statement5 = connection.createStatement();
            ResultSet results5 = statement5.executeQuery(sql5);

            while (results5.next()){
                int noteId = results5.getInt("note_id");
                String text = results5.getString("text");
                Note note = new Note(user.id, text);
                note.noteId = noteId;
                user.savedMessages.notes.add(note);
            }
            results5.close();
            statement5.close();

            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tNotes of user " + user.id + " have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void getBlockedUsers(User user){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql3 = "SELECT * FROM follow_and_block WHERE operation = 'block' AND user1_id = " + user.id;
            Statement statement3 = connection.createStatement();
            ResultSet results3 = statement3.executeQuery(sql3);
            while (results3.next()){
                user.blockedUsers.add(results3.getInt("user2_id"));
            }
            results3.close();
            statement3.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tBlocked users by user " + user.id + " have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void getFollowers(User user){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql2 = "SELECT * FROM follow_and_block WHERE operation = 'follow' AND user2_id = " + user.id;
            Statement statement2 = connection.createStatement();
            ResultSet results2 = statement2.executeQuery(sql2);
            while (results2.next()){
                user.followers.add(results2.getInt("user1_id"));
            }
            results2.close();
            statement2.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tFollowers of user " + user.id + " have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void getFollowings(User user){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql1 = "SELECT * FROM follow_and_block WHERE operation = 'follow' AND user1_id = " + user.id;
            Statement statement1 = connection.createStatement();
            ResultSet results1 = statement1.executeQuery(sql1);
            while (results1.next()){
                user.followings.add(results1.getInt("user2_id"));
            }
            results1.close();
            statement1.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tFollowings of user " + user.id + " have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void getFullData(User user){
        /**************************************** getting tweets of user ****************************************/
        if (user != null){
            getData.getTweetsOfUser(user);
        }
        /***************************************** Getting requests *********************************************/
        getRequests(user);
        /*************************************** Getting saved tweets *******************************************/
        getNotes(user);
        /*************************************** Getting blocked users ******************************************/
        getBlockedUsers(user);
        /***************************************** Getting followers ********************************************/
        getFollowers(user);
        /***************************************** Getting followings *******************************************/
        getFollowings(user);
        getData.getChats(user);
    }

    public static void getLists(User viewer){
        viewer.lists.clear();
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tConnected to database");
            String sql1 = "SELECT * FROM lists WHERE owner = " + viewer.id;
            Statement statement1 = connection.createStatement();
            ResultSet results1 = statement1.executeQuery(sql1);
            List list = null;
            String name = "";
            while (results1.next()){
                String tempName = results1.getString("name");
                if (name.equals("") || !name.equals(tempName)){
                    name = tempName;
                    list = new List(viewer.id, name);
                    String sql2 = "SELECT * FROM lists WHERE owner = " + viewer.id + " AND name = '" + name + "'";
                    Statement statement2 = connection.createStatement();
                    ResultSet results2 = statement2.executeQuery(sql2);
                    while (results2.next()){
                        int userId = results2.getInt("user_id");
                        list.users.add(userId);
                    }
                    viewer.lists.add(list);
                    list = null;
                }
            }
            results1.close();
            statement1.close();
            connection.close();
            MyLogger.getLogger().log("\t\tInfo\t\tgetData\t\tLists of user " + viewer.id + " have gotten");
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tgetData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

}
