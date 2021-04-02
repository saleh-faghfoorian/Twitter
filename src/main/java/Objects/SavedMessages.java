package Objects;

import Color.*;
import SignIn.*;
import Database.*;
import Pages.*;
import Run.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SavedMessages {

    // Fields
    public int ownerId;
    public ArrayList<Tweet> tweets;
    public ArrayList<Note>  notes;


    // Constructor
    public SavedMessages(int ownerId){
        this.ownerId = ownerId;
        this.tweets = new ArrayList<>();
        this.notes  = new ArrayList<>();
    }

    public void action(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\t\t\t\t\t***** Saved messages *****\n" + ConsoleColors.RESET);
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Saved tweets\n2. Saved notes\n0. Return"
                + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
        String command = scanner.nextLine();
        while (!(command.equals("0") || command.equals("1") || command.equals("2"))){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\t\t\t\t\t***** Saved messages *****\n"
                    + ConsoleColors.RESET);
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Saved tweets\n2. Saved notes\n0. Return"
                    + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
            command = scanner.nextLine();
        }
        if (command.equals("0")){
            PersonalPage.action(viewer);
        }
        else if (command.equals("1")){
            this.showSavedTweets(viewer);
        }
        else {
            this.showSavedNotes(viewer);
        }
    }

    public void showSavedTweets(User viewer){
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        int threshold;
        if (this.tweets == null || this.tweets.isEmpty()){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You have no saved tweet yet!" + ConsoleColors.RESET);
            threshold = 0;
            System.out.print("\n\n" + ConsoleColors.BLUE_BOLD_BRIGHT + "\n0. Return" + ConsoleColors.RESET
                    + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
        }
        else {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            for (int i = 0; i < this.tweets.size(); i++){
                Tweet tweet = getData.getTweet(this.tweets.get(i).id);
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + (i+1) + ". " + tweet.user.name
                        + " " + tweet.user.lastName + " : " + tweet.text + ConsoleColors.RESET);
            }
            System.out.print("\n\n" + ConsoleColors.BLUE_BOLD_BRIGHT + (this.tweets.size() + 1) + ". Delete tweet from saved"
                    + " tweets\n0. Return" + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : "
                    + ConsoleColors.RESET);
            threshold = this.tweets.size() + 1;
        }
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= threshold)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        if (choice == 0){
            this.action(viewer);
        }
        else if (choice == this.tweets.size() + 1){
            //Deleting tweet from saved messages
            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Which tweet do you want to delete?" + ConsoleColors.RESET);
            int temp = scanner1.nextInt();
            while (!(temp >= 1 && temp <= this.tweets.size())){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Which tweet do you want to delete? "
                        + ConsoleColors.RESET);
                temp = scanner.nextInt();
            }
            DeleteData.deleteSavedTweet(viewer.id, this.tweets.get(temp - 1).id);
            this.tweets.remove(this.tweets.get(temp - 1));
        }
        else {
            this.tweets.get(choice - 1).show(viewer);
        }
        this.action(viewer);
    }

    public void showSavedNotes(User viewer){
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        int threshold;
        if (this.notes.isEmpty()){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You have no saved note yet!" + ConsoleColors.RESET);
            System.out.print("\n\n\n" + ConsoleColors.BLUE_BOLD_BRIGHT + "1. New note" + "\n0. Return"
                    + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
            threshold = 1;
        }
        else {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            for (int i = 0; i < this.notes.size(); i++){
                System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + (i+1) + ". ");
                this.notes.get(i).show();
            }
            System.out.print("\n\n\n" + ConsoleColors.BLUE_BOLD_BRIGHT + "1. New note\n2. Delete note from saved messages"
                    + "\n0. Return" + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : "
                    + ConsoleColors.RESET);
            threshold = 2;
        }
        int choice = scanner.nextInt();
        while (!(choice >= 0 && choice <= threshold)){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            choice = scanner.nextInt();
        }
        if (choice == 1){
            // New note
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Please enter the note here : " + ConsoleColors.RESET);
            String note = scanner1.nextLine();
            Note note1 = new Note(viewer.id, note);
            note1.noteId = Submit.submitNote(viewer.id, note);
            this.notes.add(note1);
        }
        else if (choice == 2){
            // Deleting note
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nEnter the number of note you want to delete : "
                    + ConsoleColors.RESET);
            int choice2 = scanner1.nextInt();
            while (!(choice2 >= 1 && choice2 <= this.notes.size())){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nEnter the number of note you want to delete : "
                        + ConsoleColors.RESET);
                choice2 = scanner.nextInt();
            }
            DeleteData.deleteNote(this.notes.get(choice2 - 1).noteId);
            this.notes.remove(this.notes.get(choice2 - 1));
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Note deleted!" + ConsoleColors.RESET);
        }
        this.action(viewer);
    }
}
