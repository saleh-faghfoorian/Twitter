package Objects;

import java.time.LocalDateTime;
import Color.*;
import SignIn.*;
import Database.*;
import Pages.*;
import Run.*;

public class PM {

    // Fields
    public User writer, reader;
    public String text;
    public boolean hasRead;
    public LocalDateTime date;


    // Constructor
    public PM(User writer, User reader, String text, LocalDateTime date){
        this.writer  = writer;
        this.reader  = reader;
        this.text    = text;
        this.hasRead = false;
        this.date    = date;
    }
}
