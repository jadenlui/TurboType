/**
 * Name: Jaden Lui
 * Date: Tuesday, January 14, 2020
 * Project Name: Lui_Jaden_CPT
 * Description: A Level object to clean up the main method and reuse useful
 * methods.  Each of the 14 levels of the game can be represented by a Level
 * object.
 */
package TurboType;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;

public class Level {

    private ArrayList<String> wordPool;
    private ArrayList<String> gameWords = new ArrayList<>();
    private JButton unlockedButton;
    private boolean completed;
    private int levelNum;
    private int wordLength;
    private int numOfWords;
    private int timeAllowed;
    private double levelAccuracy;
    private double levelTime;
    private double levelWPM;

    public Level(ArrayList<String> wPool, int levelNum, int wordL, int wordNum, int time, boolean completed, JButton uButton) {
        // gameWords is set in other methods of this class, so it is not part of the constructor
        this.wordPool = wPool;
        this.levelNum = levelNum;

        if (wordNum < 0) { // numOfWords cannot be negative, so it is set to 1 if wordNum is
            this.numOfWords = 1;
        } else {
            this.numOfWords = wordNum;
        }

        if (wordL < 0) { // wordLength cannot be negative, so it is set to 2, (smallest word length) if wordL is
            this.wordLength = 2;
        } else {
            this.wordLength = wordL;
        }

        if (time < 0) // timeAllowed cannot be negative, so it is set to 20, (default) if time is
        {
            this.timeAllowed = 20;
        } else {
            this.timeAllowed = time;
        }
        
        this.completed = completed;
        this.unlockedButton = uButton;

    }

    /**
     * Description: Sets the gameWords List to only include words of the length
     * for the level pre-condition: wordPool and gameWords are instantiated,
     * wordLength is initialized and positive post-condition: gameWords only
     * contains words of wordLength
     *
     */
    public void getProperWordLength() {
        for (int i = 0; i < this.wordPool.size(); i++) {
            if (this.wordPool.get(i).length() == this.wordLength) {
                this.gameWords.add(this.wordPool.get(i));
            }
        }
    }

    /**
     * Description: Sets the gameWords List to be of size wordNum for the level
     * pre-condition: gameWords is instantiated, numOfWords is initialized and
     * positive post-condition: gameWords only contains words of wordLength
     *
     */
    public void numberOfWords() {
        Random r = new Random();
        int tempSize = this.gameWords.size();

        for (int i = tempSize - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            String temp = this.gameWords.get(i);
            this.gameWords.set(i, this.gameWords.get(j));
            this.gameWords.set(j, temp);
        }

        for (int i = 0; i < tempSize - this.numOfWords; i++) {
            this.gameWords.remove(0);
        }
    }

    // getter and setter methods for all private fields for encapsulation purposes
    public ArrayList<String> getWordPool() {
        return wordPool;
    }

    public void setWordPool(ArrayList<String> wordPool) {
        this.wordPool = wordPool;
    }

    public ArrayList<String> getGameWords() {
        return gameWords;
    }

    public void setGameWords(ArrayList<String> gameWords) {
        this.gameWords = gameWords;
    }

    public int getNumOfWords() {
        return numOfWords;
    }

    public void setNumOfWords(int numOfWords) {
        this.numOfWords = numOfWords;
    }

    public int getWordLength() {
        return wordLength;
    }

    public void setWordLength(int wordLength) {
        this.wordLength = wordLength;
    }

    public int getTimeAllowed() {
        return timeAllowed;
    }

    public void setTimeAllowed(int time) {
        this.timeAllowed = time;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(int levelNum) {
        this.levelNum = levelNum;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public JButton getUnlockedButton() {
        return unlockedButton;
    }

    public void setUnlockedButton(JButton button) {
        this.unlockedButton = button;
    }

    public double getLevelAccuracy() {
        return levelAccuracy;
    }

    public void setLevelAccuracy(double levelAccuracy) {
        this.levelAccuracy = levelAccuracy;
    }

    public double getLevelTime() {
        return levelTime;
    }

    public void setLevelTime(double levelTime) {
        this.levelTime = levelTime;
    }

    public double getLevelWPM() {
        return levelWPM;
    }

    public void setLevelWPM(double levelWPM) {
        this.levelWPM = levelWPM;
    }

}
