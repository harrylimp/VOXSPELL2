package voxspell.engine;

import java.util.ArrayList;

/**
 * Static ('singleton') class to store global application state
 *
 * Created by nhur714 on 16/09/16.
 * Modefied by harrylimp.
 */

/**
 * TODO: saveAll voice and max level
 */
public class LevelData {
    private static int level = -1;
    private static DataIO data = new DataIO();
    private static ArrayList<Word> currentWordList;
    private static String voice = data.getVoice();
    private static String password = "l1verpool";
    private static String wordlist = "NZCER-spelling-lists.txt";
    private static boolean isReview = false;
    private static boolean isReset = false;
    private static boolean isEnable = false;

    /**
     * checks if the password is true
     */
    public static boolean checkPassword(String userInput) {
        if (userInput.equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    // Getters and setters for all the fields to follow below

    /**
     * set currentWordList
     */
    public static void setCurrentWordList(ArrayList<Word> words) {
        currentWordList = words;
    }

    /**
     * get currentWordList
     */
    public static ArrayList<Word> getCurrentWordList() {
        return currentWordList;
    }

    /**
     * set current session's selected level
     */
    public static void setLevel(int number) {
        level = number;
    }

    /**
     * get current session's selected level
     */
    public static int getLevel() {
        return level;
    }

    /**
     * gets maximum unlocked level from file
     */
    public static int getMaxEnabledLevel() {
        return data.highestLevelEnabled();
    }

    /**
     * set voice
     */
    public static void setVoice(String toSet) {
        voice = toSet;
        data.setVoice(voice); // update file
    }

    /**
     * get voice
     */
    public static String getVoice() {
        return voice;
    }

    /**
     * Get words that have been faulted/failed right after a quiz
     */
    public static ArrayList<Word> getReviewWords(int level) {
        ArrayList<Word> wordList = new ArrayList<Word>();
        for (Word word : currentWordList) {
            if (word.getMastered() == 0) {
                wordList.add(word);
            }
        }
        return wordList;
    }

    /**
     * gets the current wordlist
     */
    public static String getWordlist() {
        return wordlist;
    }

    /**
     * sets the current wordlist
     */
    public static void setWordlist(String newList) {
        wordlist = newList;
    }

    /**
     * gets whether it is in review mode
     */
    public static boolean isReview() {
        return isReview;
    }

    /**
     * sets whether it is in review mode
     */
    public static void setIsReview(boolean isReview) {
        LevelData.isReview = isReview;
    }

    /**
     * gets whether levels should be reset
     */
    public static boolean isReset() {
        return isReset;
    }

    /**
     * sets whether levels should be reset
     */
    public static void setIsReset(boolean isReset) {
        LevelData.isReset = isReset;
    }

    /**
     * gets whether all levels should be enabled
     */
    public static boolean isEnable() {
        return isEnable;
    }

    /**
     * sets whether all levels should be enabled
     */
    public static void setIsEnable(boolean isReset) {
        LevelData.isEnable = isReset;
    }

}
