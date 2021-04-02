package Objects;

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


public class Chat {

    // Fields
    public User self, contact;
    public ArrayList<PM> pms = new ArrayList<>();

    // Constructor
    public Chat(User self, User contact) {
        this.self = self;
        this.contact = contact;
    }

    public static void action(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        if (viewer.chats.isEmpty()){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You have no conversation yet." + ConsoleColors.RESET);
            System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "1. Start a conversation\n0. Return" + ConsoleColors.RESET
                    + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
            String temp = scanner.nextLine();
            while (!(temp.equals("1") || temp.equals("0"))){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                temp = scanner.nextLine();
            }
            if (temp.equals("1")){
                startConversation(viewer);
            }
            else {
                Commands.actions(viewer);
            }
        }

        else {
            int[] chatIndexes = showChatLists(viewer);
            System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT + (viewer.chats.size() + 1) + ". New conversation"
                    + "\n0. Return" + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : "
                    + ConsoleColors.RESET);
            int choice = scanner.nextInt();
            while (!(choice >= 0 && choice <= viewer.chats.size() + 1)){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
            if (choice == 0){
                Commands.actions(viewer);

            } else if (choice == viewer.chats.size() + 1){
                startConversation(viewer);
            }
            else {
                viewer.chats.get(chatIndexes[choice - 1]).show(viewer);
            }
        }
    }

    public void show(User viewer){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        Scanner scanner = new Scanner(System.in);
        LocalDate localDate = null;
        boolean flag = false;
        for (int i = 0; i < this.pms.size(); i++){
            this.pms.get(i).hasRead = true;
            String name = this.pms.get(i).writer.name;
            String text = this.pms.get(i).text;
            String date = this.pms.get(i).date.getYear() + "-" + this.pms.get(i).date.getMonthValue() + "-"
                        + this.pms.get(i).date.getDayOfMonth();
            String time = this.pms.get(i).date.getHour() + ":" + this.pms.get(i).date.getMinute();

            if (localDate == null){
                System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\t\t\t\t\t\t\t\t\t" + date + "\n"
                        + ConsoleColors.RESET);
                localDate = this.pms.get(i).date.toLocalDate();
            }
            else {
                if (this.pms.get(i).date.toLocalDate().isAfter(localDate)){
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\n\t\t\t\t\t\t\t\t\t" + date + "\n"
                            + ConsoleColors.RESET);
                    localDate = this.pms.get(i).date.toLocalDate();
                }
            }


            if (name.equals(this.contact.name)){
                if (!flag){
                    System.out.println("");
                }
                System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT + name + " : " + text + ConsoleColors.RESET);
                suitableBlanks(name, text, time);
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + time + ConsoleColors.RESET);
                flag = true;
            }
            else {
                if (flag){
                    System.out.println("");
                }
                System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + time + ConsoleColors.RESET);
                suitableBlanks(name, text, time);
                System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + text + " : " + name + ConsoleColors.RESET);
                flag = false;
            }


        }
        Submit.updateHasRead(this);
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "1. New message\n0. Exit conversation"
                + ConsoleColors.RESET + ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : "
                + ConsoleColors.RESET);
        String choice = scanner.nextLine();
        while (!(choice.equals("0") || choice.equals("1"))){
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
            choice = scanner.nextLine();
        }
        if (choice.equals("1")){
            newMessage(viewer, this);
        }
        else {
            action(viewer);
        }
    }

    public static void startConversation(User viewer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ConsoleColors.PURPLE_BOLD_BRIGHT + "Select your contact : " + ConsoleColors.RESET);

        int counter = 0;
        for (int i = 0; i < viewer.followings.size(); i++){
            User temp = getData.getUser(viewer.followings.get(i));
            boolean hasChat = false;
            for (int j = 0; j < viewer.chats.size(); j++){
                if (temp.id == viewer.chats.get(j).contact.id){
                    hasChat = true;
                }
            }
            if (!hasChat){
                counter++;
                System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + counter + ". " + temp.name + " " + temp.lastName
                        + ConsoleColors.RESET);
            }
        }
        if (counter == 0){
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "There is no contact to start a conversation!"
                    + ConsoleColors.RESET);
            Commands.delay(2000);
            action(viewer);
        }
        else {
            System.out.print(ConsoleColors.YELLOW_BOLD_BRIGHT + "\nChoose one item : " + ConsoleColors.RESET);
            int choice = scanner.nextInt();
            while (!(choice >= 1 && choice <= viewer.followings.size())){
                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Wrong command!\nTry again" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Choose one item : " + ConsoleColors.RESET);
                choice = scanner.nextInt();
            }
            User contact = getData.getUser(viewer.followings.get(choice - 1));
            //getData.getFullData(contact);
            Scanner scanner1 = new Scanner(System.in);
            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT + "Write your first message : " + ConsoleColors.RESET);
            String firstMessage = scanner1.nextLine();
            PM firstPM = new PM(viewer, contact, firstMessage, LocalDateTime.now());
            Chat newChat = new Chat(viewer, contact);
            viewer.chats.add(newChat);
            newChat.pms.add(firstPM);
            Submit.submitPM(firstPM);
            newChat.show(viewer);
        }
    }

    public static void newMessage(User viewer, Chat chat){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print(ConsoleColors.BLUE_BOLD_BRIGHT + "Write your message : " + ConsoleColors.RESET);
        String message = scanner.nextLine();
        PM newPM = new PM(viewer, chat.contact, message, LocalDateTime.now());
        chat.pms.add(newPM);
        Submit.submitPM(newPM);
        chat.show(viewer);
    }

    public static void suitableBlanks(String name, String text, String time){
        int numberOfBlanks = 80 - name.length() - text.length() - time.length();
        for (int i = 0; i < numberOfBlanks; i++){
            System.out.print(" ");
        }
    }

    public static int newMessages(Chat chat, User viewer){
        int newMessages = 0;
        for (int i = 0; i < chat.pms.size(); i++){
            if (!chat.pms.get(i).hasRead && chat.pms.get(i).writer.id != viewer.id){
                newMessages++;
            }
        }
        return newMessages;
    }

    public static int[] showChatLists(User viewer){
        String[][] list = new String[20][4];
        int counter = 0;
        for (int i = 0; i < viewer.chats.size(); i++){
            int newMessages = newMessages(viewer.chats.get(i), viewer);
            list[counter][0] = Integer.toString(newMessages);
            list[counter][1] = viewer.chats.get(i).contact.name;
            list[counter][2] = viewer.chats.get(i).contact.lastName;
            list[counter][3] = Integer.toString(i);
            counter++;
        }
        int[] chatIndexes = new int[counter];
        for (int i = 0; i < counter; i++){
            for (int j = i; j < counter; j++){
                if (Integer.parseInt(list[i][0]) < Integer.parseInt(list[j][0])){
                    String temp1 = list[i][0];
                    String temp2 = list[i][1];
                    String temp3 = list[i][2];
                    String temp4 = list[i][3];
                    list[i][0] = list[j][0];
                    list[i][1] = list[j][1];
                    list[i][2] = list[j][2];
                    list[i][3] = list[j][3];
                    list[j][0] = temp1;
                    list[j][1] = temp2;
                    list[j][2] = temp3;
                    list[j][3] = temp4;
                }
            }
        }
        for (int i = 0; i < counter; i++){
            chatIndexes[i] = Integer.parseInt(list[i][3]);
            System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT + (i + 1) + ". " + list[i][1] + " " + list[i][2]
                    + ConsoleColors.RESET);
            if (!list[i][0].equals("0")){
                System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT + "\t\t\t(" + list[i][0] + " new messages)"
                        + ConsoleColors.RESET);
            }
            System.out.println("");
        }
        return chatIndexes;
    }




}
