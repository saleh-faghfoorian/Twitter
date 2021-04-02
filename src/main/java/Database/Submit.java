package Database;

import java.sql.*;
import java.time.LocalDateTime;

import Logging.MyLogger;
import Pages.*;
import Objects.*;
import Color.*;
import SignIn.*;
import Run.*;

public class Submit {


    public static int submitTweet(int userId, Tweet tweet){
        int tweetId = -1;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        LocalDateTime dateTime = LocalDateTime.now();
        int year   = dateTime.getYear();
        int month  = dateTime.getMonthValue();
        int day    = dateTime.getDayOfMonth();
        int hour   = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            /***************************************** Submitting tweet *****************************************/
            String sql1 = "INSERT INTO tweets (user_id, is_tweet, text, date, likes, retweets) "
                    +  "VALUES ('" + userId + "', 'true', '" + tweet.text + "', '" + year + "-" + month
                    + "-" + day + " " + hour + ":" + minute + ":" + second + "', 0, 0)";

            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);
            if (rows1 <= 0){
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting tweet " + tweetId + " failed");
            }
            /***************************************** Getting tweetId ******************************************/
            String sql2 = "SELECT * FROM tweets WHERE is_tweet = 'true' AND id = (SELECT MAX (id) FROM tweets)";
            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery(sql2);
            while (result2.next()){
                tweetId = result2.getInt("id");
            }
            /************************************** Initializing reports ****************************************/
            String sql3 = "INSERT INTO reports (id, spam, dangerous_organization, child_abuse"
                    + ", bullying, scam)" + "VALUES ('" + tweetId + "', '0', '0', '0', '0', '0')";

            Statement statement3 = connection.createStatement();
            int rows3 = statement3.executeUpdate(sql3);
            if (rows3 <= 0){
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tInitializing reports of tweet " + tweetId + " failed");
            }

            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
        return tweetId;
    }

    public static int submitUser(User user, boolean hasDateOfBirth){
        int userId = -1;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");
            ////////////////////////////////    Submitting user into database    /////////////////////////////////
            String sql;
            if (hasDateOfBirth){
                sql = "INSERT INTO users (name, lastName, userName, passWord, date_of_birth, email_Address, " +
                        "phone_Number, bio, is_Active, is_Public, last_seen_mode)"
                        + "VALUES ('" + user.name + "', '" + user.lastName + "', '" + user.userName + "', '"
                        + user.password + "', '" + user.dateOfBirth.getYear() + "-" + user.dateOfBirth.getMonth()
                        + "-" + user.dateOfBirth.getDayOfMonth() + "', '" + user.emailAddress + "', '"
                        + user.phoneNumber + "', '" + user.bio + "', 'true', 'true', 1)";
            }
            else {
                sql = "INSERT INTO users (name, lastName, userName, passWord, email_Address, " +
                        "phone_Number, bio, is_Active, is_Public, last_seen_mode)"
                        + "VALUES ('" + user.name + "', '" + user.lastName + "', '" + user.userName + "', '"
                        + user.password + "', '" + user.emailAddress + "', '" + user.phoneNumber  + "', '"
                        + user.bio + "', 'true', 'true', 1)";
            }
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + user.id + " submitted into database");
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting report user " + user.id + " failed");
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////    Getting user ID from database    /////////////////////////////////
            String sql1 = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(sql1);
            while (result.next()){
                userId = result.getInt("user_id");
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
        return userId;
    }

    public static void submitLike(int userId, int id){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");
            /******************************** Submitting like in likes database *********************************/
            String sql = "INSERT INTO likes (user_id, id)"
                    + "VALUES ('" + userId + "', '" + id + "')";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            /************************************* Getting number of likes **************************************/
            int numberOfLikes = 0;
            String sql1 = "SELECT likes FROM tweets WHERE id = " + id;
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            while (resultSet1.next()){
                numberOfLikes = resultSet1.getInt("likes");
            }
            /************************************* Updating number of likes *************************************/
            String sql2 = "UPDATE tweets SET likes = " + (numberOfLikes + 1) + "WHERE id = " + id;
            Statement statement2 = connection.createStatement();
            int rows2 = statement2.executeUpdate(sql2);
            if (rows > 0 && rows2 > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + userId + " liked tweet " + id);
                Commands.delay(3000);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting like of user " + userId + " on tweet " + id + " failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static int submitComment(Comment comment){
        int commentId = -1;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        LocalDateTime dateTime = LocalDateTime.now();
        int year   = dateTime.getYear();
        int month  = dateTime.getMonthValue();
        int day    = dateTime.getDayOfMonth();
        int hour   = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");
            String sql = "INSERT INTO tweets (user_id, is_tweet, father_id, text, date, likes, retweets)"
                    + "VALUES ('" + comment.user.id + "', 'false', '" + comment.fatherId + "', '" + comment.text
                    + "', '" + year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second
                    + "', '0', '0')";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tComment " + comment.id + " submitted");
                Commands.delay(3000);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting comment " + comment.id + " failed");
                System.out.println(ConsoleColors.RED + "Error!\nPlease try again" + ConsoleColors.RESET);
            }

            String sql1 = "SELECT * FROM tweets WHERE id = (SELECT MAX (id) FROM tweets)";
            ResultSet result = statement.executeQuery(sql1);
            while (result.next()){
                commentId = result.getInt("id");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
        return commentId;
    }

    public static void submitReport(int tweetId, String reportMode){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database ");

            String getNumberOfReport = "SELECT * FROM reports";
            Statement statement      = connection.createStatement();
            ResultSet resultSet      = statement.executeQuery(getNumberOfReport);

            int numberOfReports = 0;
            while (resultSet.next()){
                numberOfReports = resultSet.getInt(reportMode);
                numberOfReports++;
            }

            String sql = "INSERT INTO reports (id, " + reportMode + ")"
                    +  "VALUES ('" + tweetId + "', '" + numberOfReports + "')";

            Statement statement1 = connection.createStatement();
            int rows = statement1.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tTweet " + tweetId + " reported");
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting report on tweet " + tweetId + " failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitFollow(int followerId, int followingId, boolean isPublic, String notification){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql;
            if (isPublic){
                sql = "INSERT INTO follow_and_block (user1_id, user2_id, operation)"
                        +  "VALUES ('" + followerId + "', '" + followingId + "', 'follow')";
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + followerId + " followed user " + followingId);
            }
            else {
                sql = "INSERT INTO follow_request (follower_id, following_id)"
                        +  "VALUES ('" + followerId + "', '" + followingId + "')";
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + followerId + " sent a follow request for user "
                        + followingId);
            }
            Statement statement1 = connection.createStatement();
            int rows = statement1.executeUpdate(sql);

            if (isPublic){
                String sql2 = "INSERT INTO notification (user_id, notification)"
                        + " VALUES ('" + followingId + "', '" + notification + "')";
                Statement statement2 = connection.createStatement();
                int rows2 = statement2.executeUpdate(sql2);
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitLastSeen(int userId, LocalDateTime lastSeen){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql = "UPDATE users Set last_seen = '"
                    + lastSeen.getYear() + "-" + lastSeen.getMonthValue() + "-"
                    + lastSeen.getDayOfMonth() + " " + lastSeen.getHour() + ":" + lastSeen.getMinute() + ":"
                    + lastSeen.getSecond() + "' WHERE user_id = " + userId;

            Statement statement1 = connection.createStatement();
            int rows = statement1.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tLast seen of user " + userId + " submitted");
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting last seen of user " + userId + " failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitSave(int userId, int tweetId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + userId + " saved tweet " + tweetId);
            String sql = "INSERT INTO save (user_id, id)"
                    + "VALUES ('" + userId + "', '" + tweetId + "')";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + userId + " saved tweet " + tweetId);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSaving tweet " + tweetId + " by user " + userId + " failed");
            }
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void updateRetweet(int tweetId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            //MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");
            String sql = "UPDATE tweets SET retweets = (SELECT (retweets + 1) FROM tweets WHERE id = "
                    + tweetId + ") WHERE id = " + tweetId;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
        } catch (SQLException e){
            //MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitMute(int muterId, int mutedId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");
            String sql = "INSERT INTO mute (user_id, muted_id)"
                    + "VALUES ('" + muterId + "', '" + mutedId + "')";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + muterId + " muted user " + mutedId);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tMuting user " + mutedId + " by user " + muterId + " failed");
            }
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitDeactivation(int userId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql = "UPDATE users SET is_active = 'false' WHERE user_id = " + userId;
            Statement statement = connection.createStatement();
            int row = statement.executeUpdate(sql);
            if (row > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tAccount of user " + userId + " deactivated");
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tDeactivating account of user " + userId + " failed");
            }

            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void changeLastSeenMode(int userId, int lastSeenMode){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql = "UPDATE users SET last_seen_mode = " + lastSeenMode + "WHERE user_id = " + userId;
            Statement statement = connection.createStatement();
            int row = statement.executeUpdate(sql);
            if (row > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tlast seen mode of user " + userId + " changed");
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tChanging last seen mode of user " + userId + " failed");
            }

            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void changePrivacy(int userId, boolean isPublic){
        String text;
        if (isPublic){
            text = "'true'";
        } else {
            text = "'false'";
        }
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql = "UPDATE users SET is_public = " + text + "WHERE user_id = " + userId;
            Statement statement = connection.createStatement();
            int row = statement.executeUpdate(sql);
            if (row > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tPrivacy changed");
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tChanging privacy settings of user " + userId + " failed");
            }

            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void changePassword(int userId, String password){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql = "UPDATE users SET password = '" + password + "' WHERE user_id = "
                    + userId;

            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tPassword of user " + userId + " changed");
                Commands.delay(1500);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tChanging password of user " + userId + " failed");
            }

            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitAccept(int followerId, int followingId, String notifForFollower){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");
            String sql = "INSERT INTO follow_and_block (user1_id, user2_id, operation)"
                    + "VALUES ('" + followerId + "', '" + followingId + "', 'follow')";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + followingId + " accepted user " + followerId);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting acceptation failed");
            }

            String sql1 = "DELETE FROM follow_request WHERE follower_id = " + followerId + "AND following_id = "
                    + followingId;
            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);


            String sql2 = "INSERT INTO notification (user_id, notification)" + "VALUES ('" + followerId + "', '"
                    + notifForFollower + "')";
            Statement statement2 = connection.createStatement();
            int rows2 = statement2.executeUpdate(sql2);


            connection.close();

        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitReject(int followerId, int followingId, String notification){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql1 = "DELETE FROM follow_request WHERE follower_id = " + followerId + "AND following_id = "
                    + followingId;
            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);
            if (rows1 > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + followingId + " rejected request of user " + followerId);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting rejection failed");
            }

            if (!notification.equals("")){
                String sql2 = "INSERT INTO notification (user_id, notification)"
                        + " VALUES ('" + followerId + "', '" + notification + "')";
                Statement statement2 = connection.createStatement();
                int rows2 = statement2.executeUpdate(sql2);
            }

            connection.close();

        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitBlock(int blockerId, int blockedId, int mode){
        /**
         * if (mode == 1) --------> block a follower
         * if (mode == 2) --------> block a requested user
         * if (mode == 3) --------> block another one
         */
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            if (mode == 1){
                String sql1 = "DELETE FROM follow_and_block WHERE user1_id = " + blockedId + " AND user2_id = "
                        + blockerId + " AND operation = 'follow'";
                Statement statement1 = connection.createStatement();
                int rows1 = statement1.executeUpdate(sql1);
            }
            else if (mode == 2){
                String sql1 = "DELETE FROM follow_request WHERE follower_id = " + blockedId + "AND following_id = "
                        + blockerId;
                Statement statement1 = connection.createStatement();
                int rows1 = statement1.executeUpdate(sql1);
            }

            String sql2 = "INSERT INTO follow_and_block (user1_id, user2_id, operation)"
                    + "VALUES ('" + blockerId + "', '" + blockedId + "', 'block')";
            Statement statement2 = connection.createStatement();
            int rows2 = statement2.executeUpdate(sql2);
            if (rows2 <= 0){
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting blocking failed");
            }
            else {
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + blockerId + " blocked user " + blockedId);
            }
            connection.close();

        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitPM(PM pm){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String date = pm.date.getYear() + "-" + pm.date.getMonthValue() + "-" + pm.date.getDayOfMonth()
                 + " " +  pm.date.getHour() + ":" + pm.date.getMinute() + ":" + pm.date.getSecond();

            String sql1 = "INSERT INTO pms (writer_id, contact_id, text, date, has_read)"
                    + "VALUES ('" + pm.writer.id + "', '" + pm.reader.id + "', '" + pm.text + "', '"
                    + date + "', 'false')";
            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);
            if (rows1 <= 0){
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting PM of user " + pm.writer.id + " to user "
                        + pm.reader.id + " failed");
            }
            else {
                MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tUser " + pm.writer.id + " sent a message to user "
                        + pm.reader.id);
            }
            connection.close();

        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static int submitNote(int userId, String text){
        int noteId = -1;
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql1 = "INSERT INTO note (user_id, text)"
                    + "VALUES ('" + userId + "', '" + text + "')";
            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);
            if (rows1 <= 0){
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting note of user " + userId + " failed");
            }
            connection.close();

        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
        return noteId;
    }

    public static void updateHasRead(Chat chat){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            String sql1 = "UPDATE pms SET has_read = 'true' WHERE writer_id = " + chat.contact.id
                    + " AND contact_id = " + chat.self.id;
            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);
            if (rows1 <= 0){
                MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tSubmitting seen on tweets of user " + chat.contact.id + " failed");
            }
            connection.close();

        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void submitList(List list){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tSubmit\t\tConnected to database");

            for (int i = 0; i < list.users.size(); i++){
                String sql1 = "INSERT INTO lists (name, owner, user_id)"
                        + " VALUES ('" + list.name + "', '" + list.ownerId + "', '" + list.users.get(i) + "')";
                Statement statement1 = connection.createStatement();
                int rows1 = statement1.executeUpdate(sql1);
                if (rows1 > 0){
                    MyLogger.getLogger().log("\t\tDebug\t\tSubmit\t\tUser " + list.users.get(i) + " added to list "
                            + list.name);
                }
            }
            connection.close();

        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tSubmit\t\tError in connecting to database");
            e.printStackTrace();
        }
    }
}
