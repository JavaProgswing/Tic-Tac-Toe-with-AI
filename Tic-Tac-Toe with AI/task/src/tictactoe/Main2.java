package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Main2 {
    char[] board = new char[9];
    String firstUser,secondUser;
    public static void main(String[] args) {
        Main2 main = new Main2();
        main.getInputChoice();
    }
    private char getPrintable(char c) {
        if (c == 'X') {
            return 'X';
        } else if (c == 'O') {
            return 'O';
        } else {
            return ' ';
        }
    }
    private void printBoard() {
        System.out.println("---------");
        System.out.println("| " + getPrintable(board[0]) + " " + getPrintable(board[1]) + " " + getPrintable(board[2]) + " |");
        System.out.println("| " + getPrintable(board[3]) + " " + getPrintable(board[4]) + " " + getPrintable(board[5]) + " |");
        System.out.println("| " + getPrintable(board[6]) + " " + getPrintable(board[7]) + " " + getPrintable(board[8]) + " |");
        System.out.println("---------");
    }
    private Coordinate getCoordinate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the coordinates: ");
        String coordLine=scanner.nextLine();
        coordLine=coordLine.substring(coordLine.indexOf('>')+1).trim();
        String[] coords=coordLine.split(" ");
        int x,y;
        try {
            x = Integer.parseInt(coords[0]);
            y = Integer.parseInt(coords[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return getCoordinate();
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("You should enter 2 numbers!");
            return getCoordinate();
        }
        if (x > 3 || y > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            return getCoordinate();
        }
        int index = (x-1)*3 + (y-1);
        if (board[index] != '_') {
            System.out.println("This cell is occupied! Choose another one!");
            return getCoordinate();
        }
        return new Coordinate(x, y);
    }
    private void initBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = '_';
        }
        printBoard();
    }
    private void getInputChoice()
    {
        //Inputs can be user and easy, medium, hard
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input command: ");
        String input=scanner.nextLine();
        input=input.substring(input.indexOf('>')+1).trim();
        String[] inputs=input.split(" ");
        if(inputs[0].equals("exit"))
        {
            System.exit(0);
        }
        if(inputs.length!=3)
        {
            System.out.println("Bad parameters!");
            getInputChoice();
        }
        if(inputs[0].equals("start"))
        {
            if(inputs[1].equals("user") && inputs[2].equals("easy"))
            {
                initBoard();
                firstUser="user";
                secondUser="easy";
                doTurn();
            }
            else if(inputs[1].equals("user") && inputs[2].equals("medium"))
            {
                initBoard();
                firstUser="user";
                secondUser="medium";
                doTurn();
            }
            else if(inputs[1].equals("user") && inputs[2].equals("hard"))
            {
                initBoard();
                firstUser="user";
                secondUser="hard";
                doTurn();
            }
            else if(inputs[1].equals("easy") && inputs[2].equals("user"))
            {
                initBoard();
                firstUser="easy";
                secondUser="user";
                doTurn();
            }
            else if(inputs[1].equals("medium") && inputs[2].equals("user"))
            {
                initBoard();
                firstUser="medium";
                secondUser="user";
                doTurn();
            }
            else if(inputs[1].equals("hard") && inputs[2].equals("user"))
            {
                initBoard();
                firstUser="medium";
                secondUser="user";
                doTurn();
            }
            else if(inputs[1].equals("easy") && inputs[2].equals("easy"))
            {
                initBoard();
                firstUser="easy";
                secondUser="easy";
                doTurn();
            }
            else if(inputs[1].equals("medium") && inputs[2].equals("medium"))
            {
                initBoard();
                firstUser="medium";
                secondUser="medium";
                doTurn();
            }
            else if(inputs[1].equals("hard") && inputs[2].equals("hard"))
            {
                initBoard();
                firstUser="hard";
                secondUser="hard";
                doTurn();
            }
        }
        else
        {
            System.out.println("Bad parameters!");
            getInputChoice();
        }
    }
    private void doTurn() {
        if(firstUser.equals(secondUser))
        {System.out.println("Draw");System.exit(0);}
        switch (firstUser) {
            case "user" -> {
                Coordinate currentCoordinate = getCoordinate();
                int index = (currentCoordinate.x() - 1) * 3 + (currentCoordinate.y() - 1);
                board[index] = 'X';
                printBoard();
            }
            case "easy" -> {
                System.out.println("Making move level \"easy\"");
                int computerMove = getComputerEasyMove();
                board[computerMove] = 'X';
                printBoard();
            }
            case "medium" -> {
                System.out.println("Making move level \"medium\"");
                int computerMove = getComputerMediumMove('O');
                board[computerMove] = 'X';
                printBoard();
            }
            case "hard" -> {
                System.out.println("Making move level \"hard\"");
                int computerMove = getComputerHardMove();
                board[computerMove] = 'X';
                printBoard();
            }
        }
        if(isBoardFull(board))
        {System.out.println("Draw");System.exit(0);}
        switch (secondUser) {
            case "user" -> {
                Coordinate currentCoordinate = getCoordinate();
                int index = (currentCoordinate.x() - 1) * 3 + (currentCoordinate.y() - 1);
                board[index] = 'O';
                printBoard();
            }
            case "easy" -> {
                System.out.println("Making move level \"easy\"");
                int computerMove = getComputerEasyMove();
                board[computerMove] = 'O';
                printBoard();
            }
            case "medium" -> {
                System.out.println("Making move level \"medium\"");
                int computerMove = getComputerMediumMove('O');
                board[computerMove] = 'O';
                printBoard();
            }
            case "hard" -> {
                System.out.println("Making move level \"hard\"");
                int computerMove = getComputerHardMove();
                board[computerMove] = 'O';
                printBoard();
            }
        }
        checkBoardWin();
        doTurn();
    }
    private int getOpponentMediumMove(char currentChar){
        //Check if currentChar is in 2 places on the board either vertically,horizontally or diagonally
        //If yes, return the index of the third place
        //If no, return -1
        int index=-1;
        //Check for vertical
        for(int i=0;i<3;i++)
        {
            if(board[i]==currentChar && board[i+3]==currentChar && board[i+6]=='_')
            {
                index=i+6;
                break;
            }
            else if(board[i]==currentChar && board[i+6]==currentChar && board[i+3]=='_')
            {
                index=i+3;
                break;
            }
            else if(board[i+3]==currentChar && board[i+6]==currentChar && board[i]=='_')
            {
                index=i;
                break;
            }
        }
        if(index!=-1) {
            return index;
        }
        //Check for horizontal
        for(int i=0;i<9;i+=3)
        {
            if(board[i]==currentChar && board[i+1]==currentChar && board[i+2]=='_')
            {
                index=i+2;
                break;
            }
            else if(board[i]==currentChar && board[i+2]==currentChar && board[i+1]=='_')
            {
                index=i+1;
                break;
            }
            else if(board[i+1]==currentChar && board[i+2]==currentChar && board[i]=='_')
            {
                index=i;
                break;
            }
        }
        if(index!=-1) {
            return index;
        }
        //Check for diagonal
        if(board[0]==currentChar && board[4]==currentChar && board[8]=='_')
        {
            index=8;
        }
        else if(board[0]==currentChar && board[8]==currentChar && board[4]=='_')
        {
            index=4;
        }
        else if(board[4]==currentChar && board[8]==currentChar && board[0]=='_')
        {
            index=0;
        }
        else if(board[2]==currentChar && board[4]==currentChar && board[6]=='_')
        {
            index=6;
        }
        else if(board[2]==currentChar && board[6]==currentChar && board[4]=='_')
        {
            index=4;
        }
        else if(board[4]==currentChar && board[6]==currentChar && board[2]=='_')
        {
            index=2;
        }
        return index;
    }
    private boolean isBoardFull(char[] board) {
        // Loop through each cell of the game board
        for (char cell : board) {
            // If any cell is empty, the game board is not full
            if (cell == '_') {
                return false;
            }
        }

        // If all cells are filled, the game board is full
        return true;
    }
    private int getComputerMediumMove(char currentChar) {
        //Check if currentChar is in 2 places on the board either vertically,horizontally or diagonally
        //If yes, return the index of the third place
        //If no, return -1
        int index=-1;
        //Check for vertical
        for(int i=0;i<3;i++)
        {
            if(board[i]==currentChar && board[i+3]==currentChar && board[i+6]=='_')
            {
                index=i+6;
                break;
            }
            else if(board[i]==currentChar && board[i+6]==currentChar && board[i+3]=='_')
            {
                index=i+3;
                break;
            }
            else if(board[i+3]==currentChar && board[i+6]==currentChar && board[i]=='_')
            {
                index=i;
                break;
            }
        }
        if(index!=-1) {
            return index;
        }
        //Check for horizontal
        for(int i=0;i<9;i+=3)
        {
            if(board[i]==currentChar && board[i+1]==currentChar && board[i+2]=='_')
            {
                index=i+2;
                break;
            }
            else if(board[i]==currentChar && board[i+2]==currentChar && board[i+1]=='_')
            {
                index=i+1;
                break;
            }
            else if(board[i+1]==currentChar && board[i+2]==currentChar && board[i]=='_')
            {
                index=i;
                break;
            }
        }
        if(index!=-1) {
            return index;
        }
        //Check for diagonal
        if(board[0]==currentChar && board[4]==currentChar && board[8]=='_')
        {
            index=8;
        }
        else if(board[0]==currentChar && board[8]==currentChar && board[4]=='_')
        {
            index=4;
        }
        else if(board[4]==currentChar && board[8]==currentChar && board[0]=='_')
        {
            index=0;
        }
        else if(board[2]==currentChar && board[4]==currentChar && board[6]=='_')
        {
            index=6;
        }
        else if(board[2]==currentChar && board[6]==currentChar && board[4]=='_')
        {
            index=4;
        }
        else if(board[4]==currentChar && board[6]==currentChar && board[2]=='_')
        {
            index=2;
        }
        if(index!=-1) {
            return index;
        }
        //Check if the opponent can win using one move
        //If yes, block the opponent
        //If no, return -1
        if(currentChar=='X')
        {
            index=getOpponentMediumMove('O');
        }
        else if(currentChar=='O')
        {
            index=getOpponentMediumMove('X');
        }
        if(index!=-1) {
            return index;
        }
        //If no one can win in one move, return a random index
        return getComputerEasyMove();
    }
    // Method to find the optimal move for the AI player
    public int getComputerHardMove() {
        int maxValue = Integer.MIN_VALUE;
        int move=-1;
        // Loop through each cell of the game board
        for (int index=0;index<9;index++) {

                // Check if the cell is empty
                if (board[index] == '_') {
                    // Make a hypothetical move
                    board[index] = 'O';

                    // Calculate the value of the move using the minimax function
                    int value = minimax(board,0, false);

                    // Undo the hypothetical move
                    board[index] = '_';

                    // Update the optimal move if necessary
                    if (value > maxValue) {
                        maxValue = value;
                        move=index;
                    }
                }

        }
        return move;
    }
    // Recursive function to calculate the minimax value of a given board state
    private int minimax(char[] board, int depth, boolean isMaximizing) {
        // Check if the game has ended
        if (hasWon(board, 'X')) {
            return -1;
        } else if (hasWon(board, 'O')) {
            return 1;
        } else if (isDraw(board)) {
            return 0;
        }

        // Initialize the value to the appropriate infinity value
        int value = (isMaximizing) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Loop through each cell of the game board
        for (int i = 0; i < board.length; i++) {
            // Check if the cell is empty
            if (board[i] == '_') {
                // Make a hypothetical move
                board[i] = (isMaximizing) ? 'O' : 'X';

                // Calculate the value of the move by calling the minimax function recursively
                int newValue = minimax(board, depth + 1, !isMaximizing);

                // Undo the hypothetical move
                board[i] = '_';

                // Update the value if necessary
                if (isMaximizing) {
                    value = Math.max(value, newValue);
                } else {
                    value = Math.min(value, newValue);
                }
            }
        }

        return value;
    }
    // Method to check if a player has won the game
    private boolean hasWon(char[] board, char player) {
        // Check the rows
        for (int i = 0; i < 9; i += 3) {
            if (board[i] == player && board[i + 1] == player && board[i + 2] == player) {
                return true;
            }
        }

        // Check the columns
        for (int i = 0; i < 3; i++) {
            if (board[i] == player && board[i + 3] == player && board[i + 6] == player) {
                return true;
            }
        }

        // Check the diagonals
        if (board[0] == player && board[4] == player && board[8] == player) {
            return true;
        }
        if (board[2] == player && board[4] == player && board[6] == player) {
            return true;
        }

        return false;
    }

    // Method to check if the game is a draw
    private boolean isDraw(char[] board) {
        // Check if there are any empty cells
        for (char cell : board) {
            if (cell == '_') {
                return false;
            }
        }

        // If there are no empty cells and no player has won, the game is a draw
        return true;
    }
    private int getComputerEasyMove()
    {
        Random random = new Random();
        int index,minValue=0,maxValue=8;
        do{
            index = minValue + random.nextInt(maxValue - minValue + 1);
        }while(board[index]!='_');
        return index;
    }
    private void checkBoardWin() {
        int xCount = 0;
        int oCount = 0;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 'X') {
                xCount++;
            } else if (board[i] == 'O') {
                oCount++;
            }
        }
        boolean xWin = false;
        boolean oWin = false;
        for (int i = 0; i < 3; i++) {
            if (board[i] == board[i + 3] && board[i] == board[i + 6] && board[i] == 'X') {
                xWin = true;
            }
            if (board[i] == board[i + 3] && board[i] == board[i + 6] && board[i] == 'O') {
                oWin = true;
            }
            if (board[i * 3] == board[i * 3 + 1] && board[i * 3] == board[i * 3 + 2] && board[i * 3] == 'X') {
                xWin = true;
            }
            if (board[i * 3] == board[i * 3 + 1] && board[i * 3] == board[i * 3 + 2] && board[i * 3] == 'O') {
                oWin = true;
            }
        }
        if (board[0] == board[4] && board[0] == board[8] && board[0] == 'X') {
            xWin = true;
        }
        if (board[0] == board[4] && board[0] == board[8] && board[0] == 'O') {
            oWin = true;
        }
        if (board[2] == board[4] && board[2] == board[6] && board[2] == 'X') {
            xWin = true;
        }
        if (board[2] == board[4] && board[2] == board[6] && board[2] == 'O') {
            oWin = true;
        }
        if (xWin)
        {System.out.println("X wins");System.exit(0);}
        else if (oWin)
        {System.out.println("O wins");System.exit(0);}
        else if (xCount + oCount == 9)
        {System.out.println("Draw");System.exit(0);}

    }
    record Coordinate(int x, int y) {
    }
}
