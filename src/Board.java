import java.util.*;

public class Board {

    private final int[][] tiles;
    private final int[][] orderedTiles;
    private final int dimensions;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles.length != tiles[0].length) throw new IllegalArgumentException();
        this.tiles = tiles;
        dimensions = tiles.length;

        orderedTiles = new int[dimensions][dimensions];
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
        int count = 0;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (orderedTiles[i][j] != 0) {
                    if (orderedTiles[i][j] != tiles[i][j]) count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (orderedTiles[i][j] != tiles[i][j]) {
                    for (int k = 0; k < dimensions; k++) {
                        for (int l = 0; l < dimensions; l++) {
                            if (orderedTiles[i][j] == tiles[k][l]) {
                                count += Math.abs((i + j) - (k + l));
                            }
                        }
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
        return null;
    }

    // is this board the goal board?
    public boolean isGoal() {
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
            x0 = coordinatesOfTile0[0];//TODO: dictionary? tuple?
            y0 = coordinatesOfTile0[1];
            copyOfBoard = copyBoardArr();

            switch (i) { //tODO: Method for swapping?
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
                    if (x0 - 1 < dimensions) {
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
                    if (y0 - 1 < dimensions) {
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
        for (int i = 1; i < dimensions; i++) {
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
        int[][] boardarr = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(boardarr);

        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());

        System.out.println("ITERABLE TEST");
        Iterable<Board> boards = board.neighbors();
        for (Board b : boards) {
            System.out.println(b.toString());
        }

        System.out.println("TWIN");
        System.out.println(board.twin());
    }
}