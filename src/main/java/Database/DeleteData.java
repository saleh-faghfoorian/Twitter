package Database;

import java.sql.*;
import Color.*;
import Logging.MyLogger;
import Objects.*;
import Pages.*;
import Run.*;
import SignIn.*;

public class DeleteData {

    public static void deleteAccount(int userId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            int sum = 0;
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql1 = "DELETE FROM users WHERE user_id = " + userId;
            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);
            sum += rows1;

            String sql2 = "DELETE FROM tweets WHERE user_id = " + userId;
            Statement statement2 = connection.createStatement();
            int rows2 = statement2.executeUpdate(sql2);
            sum += rows2;

            String sql3 = "DELETE FROM save WHERE user_id = " + userId;
            Statement statement3 = connection.createStatement();
            int rows3 = statement3.executeUpdate(sql3);
            sum += rows3;

            String sql4 = "DELETE FROM black_list WHERE blocker_id = " + userId;
            Statement statement4 = connection.createStatement();
            int rows4 = statement4.executeUpdate(sql4);
            sum += rows4;

            String sql5 = "DELETE FROM black_list WHERE blocked_id = " + userId;
            Statement statement5 = connection.createStatement();
            int rows5 = statement5.executeUpdate(sql5);
            sum += rows5;

            String sql6 = "DELETE FROM follow_and_block WHERE user1_id = " + userId;
            Statement statement6 = connection.createStatement();
            int rows6 = statement6.executeUpdate(sql6);
            sum += rows6;

            String sql7 = "DELETE FROM follow_and_block WHERE user2_id = " + userId;
            Statement statement7 = connection.createStatement();
            int rows7 = statement7.executeUpdate(sql7);
            sum += rows7;

            String sql8 = "DELETE FROM follow_request WHERE follower_id = " + userId;
            Statement statement8 = connection.createStatement();
            int rows8 = statement8.executeUpdate(sql8);
            sum += rows8;

            String sql9 = "DELETE FROM follow_request WHERE following_id = " + userId;
            Statement statement9 = connection.createStatement();
            int rows9 = statement9.executeUpdate(sql9);
            sum += rows9;

            String sql10 = "DELETE FROM likes WHERE user_id = " + userId;
            Statement statement10 = connection.createStatement();
            int rows10 = statement10.executeUpdate(sql10);
            sum += rows10;

            String sql11 = "DELETE FROM mute WHERE user_id = " + userId;
            Statement statement11 = connection.createStatement();
            int rows11 = statement11.executeUpdate(sql11);
            sum += rows11;

            String sql12 = "DELETE FROM mute WHERE muted_id = " + userId;
            Statement statement12 = connection.createStatement();
            int rows12 = statement12.executeUpdate(sql12);
            sum += rows12;

            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tUser " + userId + " deleted");
            Commands.delay(3000);
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void deleteTweet(int tweetId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql = "DELETE FROM tweets WHERE id = " + tweetId;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tTweet " + tweetId + " deleted");
                Commands.delay(3000);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tDeleting tweet " + tweetId + " failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void dislikeTweet(int userId, int id){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql = "DELETE FROM likes WHERE id = " + id + "AND user_id = " + userId;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);

            int numberOfLikes = 0;
            String sql1 = "SELECT * FROM tweets WHERE id = " + id;
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql1);
            while (resultSet1.next()){
                numberOfLikes = resultSet1.getInt("likes");
            }
            numberOfLikes--;

            String sql2 = "UPDATE tweets SET likes = " + numberOfLikes + " WHERE id = " + id;
            Statement statement2 = connection.createStatement();
            int rows2 = statement2.executeUpdate(sql2);

            if (rows > 0 && rows2 > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tUser " + userId + " disliked tweet " + id);
                Commands.delay(3000);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tDisliking tweet " + id + " by user " + userId + " failed");
                System.out.println(ConsoleColors.RED + "Error!\nPlease try again" + ConsoleColors.RESET);
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void deleteFollow(int userId, int targetId, String notification){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tIndo\t\tDeleteData\t\tConnected to database");
            String sql = "DELETE FROM follow_and_block WHERE operation = 'follow' user1_id = "
                    + userId + "AND user2_id = " + targetId;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tUser " + userId + " unfollowed user " + targetId);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tUnfollowing user " + targetId + " by user "
                        + userId + " failed");
            }

            String sql1 = "INSERT INTO notification (user_id, notification)"
                    + " VALUES ('" + targetId + "', '" + notification + "')";
            Statement statement1 = connection.createStatement();
            int rows1 = statement1.executeUpdate(sql1);

            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void deleteSavedTweet(int userId, int id){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql = "DELETE FROM save WHERE user_id = " + userId + " AND id = " + id;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tTweet deleted from saved tweets of user " + userId);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tDeleting tweet from saved tweets of user "
                        + userId + " failed");
            }
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void deleteMutedUser(int muterId, int mutedId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql = "DELETE FROM mute WHERE user_id = " + muterId + "AND muted_id = " + mutedId;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows <= 0){
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tUnmuting user " + mutedId + " by user "
                        + muterId + " failed");
            }
            else {
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tUser " + muterId + " unmuted user " + mutedId);
            }
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void deleteNotifications(int userId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql = "DELETE FROM notification WHERE user_id = " + userId;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tNotifications of user " + userId + " deleted");
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tDeleting notifications of user " + userId + " failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void deleteNote(int noteId){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql = "DELETE FROM note WHERE note_id = " + noteId;
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tNote deleted");
                Commands.delay(3000);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tDeleting note failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }

    public static void deleteList(int ownerId, String name){
        String jdbcURL = "jdbc:postgresql://localhost:5432/twitter";
        String userName = "postgres";
        String passWord = "saleh791378";
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, userName, passWord);
            MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tConnected to database");
            String sql = "DELETE FROM lists WHERE owner = " + ownerId + " AND name = '" + name + "'";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0){
                MyLogger.getLogger().log("\t\tInfo\t\tDeleteData\t\tList " + name + " deleted");
                Commands.delay(3000);
            }
            else {
                MyLogger.getLogger().log("\t\tDebug\t\tDeleteData\t\tDeleting list " + name + " failed");
            }
            connection.close();
        } catch (SQLException e){
            MyLogger.getLogger().log("\t\tError\t\tDeleteData\t\tError in connecting to database");
            e.printStackTrace();
        }
    }


}
