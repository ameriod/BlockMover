package com.nordeck.blockmover;

import com.badlogic.gdx.Gdx;

/**
 * Created by Parker on 4/12/2014.
 * <p/>
 * Common Utility methods
 */
public class Utils {

    /**
     * Prints out log statements with System.out.println controlled by JumpShipGame.DEBUG_LOG)
     *
     * @param tag
     * @param message
     */
    public static void log(String tag, String message) {
        if (Ludum32Game.IS_DEBUG) {
            Gdx.app.log(tag, message);
        }
    }

    /**
     * Checks if c is between a and b
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static boolean isBetween(int a, int b, int c) {
        return b > a ? c > a && c < b : c > b && c < a;
    }

    /**
     * Checks if c is between a and b
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static boolean isBetween(float a, float b, float c) {
        return b > a ? c > a && c < b : c > b && c < a;
    }

    /**
     * Checks to see if c is inBetween a and b or equals
     *
     * @param a bound
     * @param b bound
     * @param c checkmark value
     * @return
     */
    public static boolean isBetweenOrEquals(int a, int b, int c) {
        return b >= a ? c >= a && c <= b : c >= b && c <= a;
    }

    /**
     * Checks to see if c is inBetween a and b or equals
     *
     * @param a bound
     * @param b bound
     * @param c checkmark value
     * @return
     */
    public static boolean isBetweenOrEquals(float a, float b, float c) {
        return b >= a ? c >= a && c <= b : c >= b && c <= a;
    }

    /**
     * Checks if the given string is null or equal to "".
     *
     * @param input
     * @return
     */
    public static boolean isTextEmpty(CharSequence input) {
        if (input == null) {
            return true;
        } else if (input.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }
}
