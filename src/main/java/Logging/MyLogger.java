package Logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;

public class MyLogger {
    static String address = "./log.txt";
    PrintStream printStream;
    static MyLogger myLogger;

    private MyLogger() {
        try {
            printStream = new PrintStream(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MyLogger getLogger(){
        if (myLogger == null){
            myLogger = new MyLogger();
        }
        return myLogger;
    }

    public void log(String log){
        printStream.println(LocalDateTime.now() + "  " + log);
        printStream.flush();
    }




}
