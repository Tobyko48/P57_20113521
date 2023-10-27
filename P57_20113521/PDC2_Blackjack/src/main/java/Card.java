
import java.io.File;
import java.io.FilenameFilter;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Pikachu
 */
public class Card implements HandInterface {
    private String suit;
    private String rank;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        switch (rank) {
            case "Ace":
                return 11;
            case "King":
            case "Queen":
            case "Jack":
                return 10;
            default:
                return Integer.parseInt(rank);
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    
    public String getImagePath() {
        File dir = new File("resources/cards");
        String fileName = findFileIgnoreCase(dir, rank + "_of_" + suit + ".png");
        if (fileName != null) {
            return dir + File.separator + fileName;
        }
        return null; 
    }
    
    private static String findFileIgnoreCase(File dir, String fileName) {
        String[] matchingFiles = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.equalsIgnoreCase(fileName);
            }
        });
        if (matchingFiles != null && matchingFiles.length > 0) {
            return matchingFiles[0];
        }
        return null;
    }
    
}


