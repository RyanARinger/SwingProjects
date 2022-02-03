package se370_team3_projectv1;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
This class has the same purpose as the LevelLister class but is simplified

It should be noted that neither of these classes work and are not used in this application
 */

import java.util.ArrayList;

public class LevelListerV2 {
    private final File lFile;
    private BufferedReader br;

    private ArrayList<String> levels;

    public LevelListerV2(){
        this.levels = new ArrayList<String>();

        this.lFile = new File("levels.csv");
        try {
            this.br = new BufferedReader(new FileReader(this.lFile));
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
            System.out.println("[FILE NOT FOUND]");
        }
        this.readNames();
    }

    private void readNames(){
        String f;
        try{
            while((f = br.readLine()) != null){
                levels.add(f);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
            System.out.println("[IOExCEPTION_1]");
        }
    }

    public ArrayList<String> getLevels(){
        return this.levels;
    }
}
