package se.liu.ida.jenli414.tddd78.tetris;

import java.util.Random;

/**
 * Types of squares in the game
 * */
public enum SquareType
{
    /**
     * I square type
     */
    I, /**
     * O square type
     */
    O, /**
     * T square type
     */
    T, /**
     * S square type
     */
    S, /**
     * Z square type
     */
    Z, /**
     * J square type
     */
    J, /**
     * L square type
     */
    L, /**
     * No square type
     */
    EMPTY, /**
     * Outside square type
     */
    OUTSIDE;

    private final static Random RND = new Random();

    public static SquareType getRndSquareType() {
        SquareType[] squareTypes = values();
        return squareTypes[RND.nextInt(squareTypes.length - 2)];
    }
}
