package Pages;

import Color.ConsoleColors;
import Objects.*;
import Database.*;
import Run.Commands;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Timeline {

    public static void action(User viewer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "\t\t\t\t***** Timeline *****\n" + ConsoleColors.RESET);
        ArrayList<Tweet> tweets = new ArrayList<>();
        ArrayList<Tweet> sortedTweets = new ArrayList<>();
        /************************************* Getting followings and tweets ************************************/
        for (int i = 0; i < viewer.followings.size(); i++) {
            User temp = getData.getUser(viewer.followings.get(i));
            getData.getTweetsOfUser(temp);
            tweets.addAll(temp.tweets);
        }
        int[] tweetIds = new int[tweets.size()];
        for (int i = 0; i < tweets.size(); i++) {
            tweetIds[i] = tweets.get(i).id;
        }
        /*************************************** Sorting tweetIds by date ***************************************/
        for (int i = 0; i < tweets.size(); i++) {
            for (int j = i; j < tweets.size(); j++) {
                if (tweets.get(j).date.isAfter(tweets.get(i).date)) {
                    int temp = tweetIds[i];
                    tweetIds[i] = tweetIds[j];
                    tweetIds[j] = temp;
                }
            }
        }
        /**************************************** Sorting tweets by date ****************************************/
        for (int i = 0; i < tweets.size(); i++) {
            for (int j = 0; j < tweets.size(); j++) {
                if (tweetIds[i] == tweets.get(j).id) {
                    sortedTweets.add(tweets.get(j));
                }
            }
        }
        tweets.clear();
        /******************************************** Showing tweets ********************************************/
        if (sortedTweets.size() == 0) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "There is no tweet to show!" + ConsoleColors.RESET);
        } else {
            boolean flag = true;
            if (sortedTweets.get(0).date.isAfter(viewer.lastSeen)) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\t\t\t\t***** New tweets *****\n\n"
                        + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "There is no new tweet to show!\n"
                        + "\n\t\t\t\t***** Old tweets *****\n\n" + ConsoleColors.RESET);
                flag = false;
            }
            for (int i = 0; i < sortedTweets.size(); i++) {

                String date =   sortedTweets.get(i).date.getYear() + "-" + sortedTweets.get(i).date.getMonthValue()
                        + "-" + sortedTweets.get(i).date.getDayOfMonth() + " " + sortedTweets.get(i).date.getHour()
                        + ":" + sortedTweets.get(i).date.getMinute() + ":" + sortedTweets.get(i).date.getSecond();
                String[] splitText = sortedTweets.get(i).text.split(" ");
                String partOfText;
                if (splitText.length >= 4) {
                    partOfText = splitText[0] + " " + splitText[1] + " " + splitText[2]
                            + " " + splitText[3] + " ...";
                } else {
                    partOfText = sortedTweets.get(i).text;
                }
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i + 1) + ". " + sortedTweets.get(i).user.name
                        + " " + sortedTweets.get(i).user.lastName + " :\t" + partOfText + "\t\t\t\t" + date
                        + ConsoleColors.RESET);

                if (i < sortedTweets.size() - 1) {
                    if (flag && sortedTweets.get(i + 1).date.isBefore(viewer.lastSeen)) {
                        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "\n\n\t\t\t\t***** Old tweets *****\n\n"
                                + ConsoleColors.RESET);
                    }
                    flag = false;
                }
            }
        }
        viewer.lastSeen = LocalDateTime.now();
        System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "\n0. Return\n" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
        int choice;
        choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= sortedTweets.size())){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        if (choice != 0){
            sortedTweets.get(choice - 1).show(viewer);
        }
        Commands.actions(viewer);
    }

}
