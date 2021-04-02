package Objects;

import Color.ConsoleColors;

public class Note {
    // Fields
    public int noteId;
    public int userId;
    public String text;

    // Constructor

    public Note(int userId, String text) {
        this.userId = userId;
        this.text   = text;
    }

    public void show(){
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + this.text + ConsoleColors.RESET);
    }
}
