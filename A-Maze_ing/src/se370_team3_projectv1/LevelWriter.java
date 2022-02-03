package se370_team3_projectv1;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

/*
This class basically opens or creates a new .csv file and uses to toString() method created in the LevelInfo class
to write the level information  for a maze to a file.
 */

public class LevelWriter {
    private LevelInfo li;
    private File filename;
    private PrintWriter pw;

    public LevelWriter(){

    }
    public boolean nextSave(LevelInfo l){
        this.li = l;
        this.filename = new File(this.li.getName() + ".csv");
        return this.write();
    }
    public boolean write(){
        try{
            this.pw = new PrintWriter(this.filename);
            this.pw.write(this.li.toString());
            this.pw.flush();

        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
            System.out.println("[FILE NOT FOUND]");
            return false;
        }
        return true;
    }
}


