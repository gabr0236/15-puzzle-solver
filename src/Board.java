import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private final int[][] tiles;
    private final int dimensions;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles.length != tiles[0].length) throw new IllegalArgumentException();
        dimensions = tiles.length;
        this.tiles = new int[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    //TODO Del:
    private int[][] goalBoard(){
        int[][] orderedTiles = new int[dimensions][dimensions];
        int count = 1;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (i + 1 >= dimensions && j + 1 >= dimensions) {
                    orderedTiles[i][j] = 0;
                } else {
                    orderedTiles[i][j] = count++;
                }
            }
        }
        return orderedTiles;
    }

    // string representation of this board
    public String toString() {
        StringBuilder string = new StringBuilder(dimensions + "\n");
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                string.append(tiles[i][j]).append(j + 1 >= dimensions ? "\n" : " ");
            }
        }
        return string.toString();
    }

    // board dimension n
    public int dimension() {
        return dimensions;
    }

    // number of tiles out of place
    public int hamming() {
        int[][] orderedTiles = goalBoard();
        int k = 0;
        int hammingCount = 0;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (++k<dimensions) {
                    if (++k != tiles[i][j]) hammingCount++;
                }
            }
        }
        return hammingCount;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int[][] orderedTiles = goalBoard();
        int count = 0;
        int[] coordinatesOfTile;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (orderedTiles[i][j]!=0 && orderedTiles[i][j] != tiles[i][j]) {
                    coordinatesOfTile = getCoordinatesOfTile(orderedTiles[i][j]);
                    if (coordinatesOfTile.length != 0) {
                        count += Math.abs(i - coordinatesOfTile[0]) + Math.abs(j - coordinatesOfTile[1]);
                    }
                }
            }
        }
        return count;
    }

    private int[] getCoordinatesOfTile(int tile) {
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (tiles[i][j] == tile) return new int[]{i, j};
            }
        }
        return new int[0];
    }

    // is this board the goal board?
    public boolean isGoal() {
        int[][] orderedTiles = goalBoard();
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (orderedTiles[i][j] != tiles[i][j]) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;

        if (y instanceof Board) {
            if (((Board) y).dimension() == this.dimensions) {
                return y.toString().equals(this.toString());
            }
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int[] coordinatesOfTile0 = getCoordinatesOfTile(0);

        int[][] copyOfBoard;
        int x0, y0;
        boolean isInsideDimensions;
        for (int i = 0; i < 4; i++) {
            isInsideDimensions = false;
            x0 = coordinatesOfTile0[0];
            y0 = coordinatesOfTile0[1];
            copyOfBoard = copyBoardArr();

            switch (i) {
                case 0:
                    if (x0 + 1 < dimensions) {
                        //Swap
                        int temp = copyOfBoard[x0][y0];
                        copyOfBoard[x0][y0] = copyOfBoard[x0 + 1][y0];
                        copyOfBoard[x0 + 1][y0] = temp;
                        isInsideDimensions = true;
                    }
                    break;
                case 1:
                    if (x0-1>=0 && x0 - 1 < dimensions) {
                        //Swap
                        int temp = copyOfBoard[x0][y0];
                        copyOfBoard[x0][y0] = copyOfBoard[x0 - 1][y0];
                        copyOfBoard[x0 - 1][y0] = temp;
                        isInsideDimensions = true;
                    }
                    break;
                case 2:
                    if (y0 + 1 < dimensions) {
                        //Swap
                        int temp = copyOfBoard[x0][y0];
                        copyOfBoard[x0][y0] = copyOfBoard[x0][y0 + 1];
                        copyOfBoard[x0][y0 + 1] = temp;
                        isInsideDimensions = true;
                    }
                    break;
                case 3:
                    if (y0-1>=0 && y0 - 1 < dimensions) {
                        //Swap
                        int temp = copyOfBoard[x0][y0];
                        copyOfBoard[x0][y0] = copyOfBoard[x0][y0 - 1];
                        copyOfBoard[x0][y0 - 1] = temp;
                        isInsideDimensions = true;
                    }
                    break;
            }
            if (isInsideDimensions) {
                neighbors.add(new Board(copyOfBoard));
            }
        }
        return neighbors;
    }

    //Returns a copy of the board array
    private int[][] copyBoardArr() {
        int[][] arr = new int[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                arr[i][j] = tiles[i][j];
            }
        }
        return arr;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
            for (int i = 0; i < dimensions; i++) {
                for (int j = 1; j < dimensions; j++) {
                    if (tiles[i][j - 1] != 0 && tiles[i][j] != 0) {
                        int[][] twinBoard = copyBoardArr();
                        int temp = twinBoard[i][j - 1];
                        twinBoard[i][j - 1] = twinBoard[i][j];
                        twinBoard[i][j] = temp;
                        return new Board(twinBoard);
                    }
                }
            }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] boardarr = {{2,1},{0,3}}; //{{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};
        Board board = new Board(boardarr);

        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println("MANHATTAN: " + board.manhattan());

        System.out.println("ITERABLE TEST");
        Iterable<Board> boards = board.neighbors();
        for (Board b : boards) {
            System.out.println(b.toString());
        }

        System.out.println("TWIN");
        System.out.println(board.twin());
    }
}