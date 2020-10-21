
package tictactoe;

import javax.naming.PartialResultException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static String[][] gameboard = {
            {"_","_","_"},
            {"_","_","_"},
            {"_","_","_"}
    };
    static boolean oWins = false;
    static boolean xWins = false;
    static boolean empties = false;
    static boolean xVo = false;
    static String userMove = "";
    static String userMove2 = "";
    static boolean isMoveValid = false;
    static boolean isGameFinished = false;
    static String player = "X";


    public static void main(String[] args) {

        while (!isGameFinished) {
            isMoveValid = false;
            //todo: print playing field
            printGameBoard(gameboard);

            //todo: scan in coordinates
            //getUserCoordinates();

            //todo: validate coordinates
            //todo: put coordinates on the the playing field
            //done in validation
            while (!isMoveValid) {
                getUserCoordinates();
                isMoveValid = validateCoordinates(userMove, userMove2);

            }

            //todo: verify game status
            oWins = isHorizontalWin(gameboard, "O");
            xWins = isHorizontalWin(gameboard, "X");
            xVo = compareCounts();

            if (!oWins && !xWins) {
                oWins = isVerticalWin(gameboard, "O");
                xWins = isVerticalWin(gameboard, "X");
            }

            if (!oWins && !xWins) {
                oWins = isDiagWin(gameboard, "O");
                xWins = isDiagWin(gameboard, "X");
            }
            empties = isEmpties(gameboard);

            validateResults();
            //todo:print the playing field
            //todo: print the result
            //todo: loop to scan if game is not over

        }
    }

    private static boolean compareCounts() {

        long countX = Arrays.stream(gameboard).filter(str -> str.equals("X")).count();
        long countO = Arrays.stream(gameboard).filter(str -> str.equals("O")).count();

        if (Math.abs(countO - countX)  > 1){
            return true;
        }
        return false;
    }

    private static boolean validateCoordinates(String userMove, String userMove2) {
        isMoveValid = false;
        if (!isInteger(userMove)){
            System.out.println("You should enter numbers!");
        } else if (!isInteger(userMove2)) {
            System.out.println("You should enter numbers!");
        } else if(Integer.parseInt(userMove) < 1 || Integer.parseInt(userMove2) < 1 ||
                Integer.parseInt(userMove) > 3 || Integer.parseInt(userMove2) > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
        } else if(!isValidInput(gameboard, userMove, userMove2)) {
            System.out.println("This cell is occupied! Choose another one!");
        } else {
            isMoveValid = true;
        }
        return isMoveValid;
    }

    private static boolean isValidInput(String[][] gameboard, String userMove, String userMove2) {
        int firstCoord = Integer.parseInt(userMove);
        int secondCoord = Integer.parseInt(userMove2);
        // add 1 to each
        firstCoord--;
        secondCoord--;
        // invert the gameboard
        invertGameBoard(gameboard);
        if ("_".equals(gameboard[secondCoord][firstCoord])) {
            gameboard[secondCoord][firstCoord] = player;
            changePlayer();
            invertGameBoard(gameboard);
        } else {
            invertGameBoard(gameboard);
            return false;
        }
        return true;
    }

    private static void changePlayer() {
        switch (player){
            case "X":
                player = "O";
                break;
            case "O":
                player = "X";
                break;
        }
    }

    private static void getUserCoordinates() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the coordinates: ");
        userMove = scanner.next();
        userMove2 = scanner.next();

    }

    private static void printGameBoard(String[][] gameboard) {
        System.out.println("---------");
        System.out.println("| " + gameboard[0][0] + " "
                + gameboard[0][1] + " " + gameboard[0][2] +" |");
        System.out.println("| " + gameboard[1][0] + " "
                + gameboard[1][1] + " " + gameboard[1][2] +" |");
        System.out.println("| " + gameboard[2][0] + " "
                + gameboard[2][1] + " " + gameboard[2][2] +" |");
        System.out.println("---------");
    }

    private static void invertGameBoard(String[][] gameboard) {
        for (int i = 0; i < 3; i++){
            String top = gameboard[0][i];
            String bottom = gameboard[2][i];
            gameboard[2][i] = top;
            gameboard[0][i] = bottom;
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    private static boolean isHorizontalWin(String[][] horGameboard, String player){
        char result = 'N';
        int count=0;
        for (int i = 0; i < 3; i++){
            for (int x = 0; x < 3; x++){

                if (horGameboard[i][x].equals(player)) {
                    ++count;
                } else {
                    count = 0;
                }
                if (count == 3) return true;
            }
            count = 0;
        }
        return false;

    }

    private static boolean isDiagWin(String[][] gameboard, String player) {
        char result = 'N';
        int count=0;
        for (int i = 0; i < 3; i++){
            if (gameboard[i][i].equals(player)) {
                ++count;
            } else {
                count = 0;
            }
            if (count == 3) return true;
        }
        //2,0 1,1 0,2
        count = 0;
        if (gameboard[0][2].equals(player) && gameboard[1][1].equals(player)
                && gameboard[2][0].equals(player)) {
            return true;
        }
        return false;
    }

    private static boolean isEmpties(String[][] gameboard) {
        for (int i = 0; i < 3; i++){
            for (int x = 0; x < 3; x++){
                if (gameboard[i][x].equals("_")) return true;
            }
        }
        return false;
    }

    private static boolean isVerticalWin(String[][] horGameboard, String player){
        char result = 'N';
        int count=0;
        for (int i = 0; i < 3; i++){
            for (int x = 0; x < 3; x++){
                if (horGameboard[x][i].equals(player)) {
                    ++count;
                } else {
                    count = 0;
                }
                if (count == 3) return true;
            }
            count = 0;
        }
        return false;

    }

    private static void validateResults() {

        if (oWins && xWins || xVo){
            printGameBoard(gameboard);
            System.out.println("Impossible");
            isGameFinished = true;
        } else if (oWins) {
            printGameBoard(gameboard);
            System.out.println("O wins");
            isGameFinished = true;
        } else if (xWins) {
            printGameBoard(gameboard);
            System.out.println("X wins");
            isGameFinished = true;
        } else if (!oWins && !xWins && empties) {
            //System.out.println("Game not finished");
        } else if (!oWins && !xWins && !empties) {
            printGameBoard(gameboard);
            System.out.println("Draw");
            isGameFinished = true;
        }
    }
}