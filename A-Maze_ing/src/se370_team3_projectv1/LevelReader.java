package se370_team3_projectv1;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
This class takes a string as a filename and reads its information to save a LevelInfo object and send it to the
GameFrame

The files will hold the level name and the size of the maze on the first line.
Each following line will contain "size" number of elements in a "0000" bit format
Each bit in the string corresponds to a cardinal direction for a specified tile to open
 */

public class LevelReader {
    private LevelInfo li;
    private File filename;
    private BufferedReader br;

    public LevelReader() {
        this.li = new LevelInfo();
    }

    public LevelInfo nextFile(String f){
        this.filename = new File(f);
        try {
            this.br = new BufferedReader(new FileReader(this.filename));
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
            System.out.println("[FILE NOT FOUND]");
        }

        if(this.readFirstLine()){
            this.readMazeInfo();
        }
        else{
            System.out.println("[FIRST LINE ERROR]");
        }

        return this.li;
    }

    private boolean readFirstLine(){
        String t = ""; // temp string
        String l = ""; // first line string
        int s;
        int c = 0;

        try {
            l = br.readLine();
        }catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println("[IOExCEPTION_1]");
            return false;
        }
//        System.out.println(l);
        if(!l.isEmpty()) {
            while (l.charAt(c) != ',') { // name
                t += l.charAt(c);
                c++;
            }
            this.li.setName(t);
            c++;
            t = "";

            for(int i = c; i < l.length(); i++){ // size
                t += l.charAt(i);
            }
//            System.out.println(t);
            s = Integer.parseInt(t);
            this.li.setSize(s);
            return true;
        }
        else{
            return false;
        }
    }

    private void readMazeInfo(){
        int x = 0;
        int y = 0;
        int c = 0;
        String t = "";

        while(c != -1){
            try{
                c = br.read();
            }catch(IOException ioe){
                ioe.printStackTrace();
                System.out.println("[IOExCEPTION_2]");
            }

            if((char)c == ','){
                this.li.addString(t, x, y);
                x++;
                t = "";
            }
            else if((char)c == '\n'){
                this.li.addString(t, x, y);
                x = 0;
                y++;
                t = "";
            }
            else if(c != -1){
                t += (char)c;
            }
        }
//        System.out.println((int)c);
//        System.out.println();
        this.li.addString(t, x, y);
    }


}
