package se370_team3_projectv1;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
THIS CLASS IS NOT USED AND IS MADE OBSOLETE BY LevelListerV2

This class reads in level file names from a .csv file that holds the names of all saved levels
 */

public class LevelLister {
    private final File lFile;
    private BufferedReader br;

    private LevelInfo[] levels;
    private int numLevels;

    private LevelReader lr;

    public LevelLister(){
        this.lFile = new File("levels.csv");
        this.lr = new LevelReader();
        try {
            this.br = new BufferedReader(new FileReader(this.lFile));
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
            System.out.println("[FILE NOT FOUND]");
        }

        if(readFirstLine()){
            this.readLevels();
        }
        else{
            System.out.println("[FIRST LINE ERROR]");
        }
    }
    private boolean readFirstLine(){
        String s;

        try{
            s = this.br.readLine();
        }catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("[IOExCEPTION_1]");
            return false;
        }

        if(!s.isEmpty()){
            this.numLevels = Integer.parseInt(s);
            this.levels = new LevelInfo[this.numLevels];
            for(int i = 0; i < this.numLevels; i++){
                this.levels[i] = null;
            }
            return true;
        }
        else{
            return false;
        }
    }
    private void readLevels(){
        String f;
        int i = 0;
        try {
            while (((f = br.readLine()) != null) && i < this.numLevels) {
                this.levels[i] = lr.nextFile(f);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("[IOExCEPTION_2]");
        }
    }
    public LevelInfo getInfo(int i){
        if(i < this.numLevels && i > 0){
            return this.levels[i];
        }
        return null;
    }
}
