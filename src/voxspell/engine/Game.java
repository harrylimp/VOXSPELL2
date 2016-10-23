package voxspell.engine;

/**
 * Created by harrylimp on 23/10/16.
 */
public class Game {

    private static int points;

    public Game() {
        points = 0;
    }

    public static void buyButton() {
        points = points - 100;
    }

    public static boolean canBuy() {
        if (points >= 100) {
            return true;
        } else {
            return false;
        }
    }

    public static void addPoints(int _points) {
        points += _points;
    }

    public static int getPoints() {
        return points;
    }

}
