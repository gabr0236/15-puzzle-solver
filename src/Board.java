import java.util.ArrayList;
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
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, dimensions);
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
        int k = 0; //Correct tile at current position
        int numberOfTiles = dimensions*dimensions;
        int hammingCount = 0;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (++k<numberOfTiles) {
                    if (k != tiles[i][j]) hammingCount++;
                }
            }
        }
        return hammingCount;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        int k = 0; //Correct tile at current position
        int numberOfTiles = dimensions*dimensions;
        int[] coordinatesOfTile;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (++k<numberOfTiles && k != tiles[i][j]) {
                    coordinatesOfTile = getCoordinatesOfTile(k);
                    count += Math.abs(i - coordinatesOfTile[0]) + Math.abs(j - coordinatesOfTile[1]);
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
        int k = 0; //Correct tile at current position
        int numberOfTiles = dimensions*dimensions;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (++k<numberOfTiles && k!= tiles[i][j]) return false;
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
        int x1 = coordinatesOfTile0[0], y1 = coordinatesOfTile0[1];

        for (int i = 0; i < 4; i++) {
            if (i==0) {
                if (x1 + 1 < dimensions) {
                    neighbors.add(new Board(swapTileZero(copyBoardArr(), x1, y1, x1 + 1, y1)));
                }
            } else if (i==1) {
                if (x1 - 1 >= 0 && x1 - 1 < dimensions) {
                    neighbors.add(new Board(swapTileZero(copyBoardArr(), x1, y1, x1 - 1, y1)));
                }
            } else if (i==2) {
                if (y1 + 1 < dimensions) {
                    neighbors.add(new Board(swapTileZero(copyBoardArr(), x1, y1, x1, y1 + 1)));
                }
            } else {
                    if (y1-1>=0 && y1 - 1 < dimensions) {
                        neighbors.add(new Board(swapTileZero(copyBoardArr(), x1, y1, x1,y1-1)));
                    }
            }
        }
        return neighbors;
    }

    //Swaps tile zero (x1,y1) with another tile (x2,y2)
    private int[][] swapTileZero(int[][] arr, int x1, int y1, int x2, int y2){
        int temp = arr[x1][y1];
        arr[x1][y1] = arr[x2][y2];
        arr[x2][y2] = temp;
        return arr;
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
        int[][] boardArr = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};//{{2,1},{0,3}};
        Board board = new Board(boardArr);

        System.out.println(board);
        System.out.println("HAMMING: " + board.hamming());
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