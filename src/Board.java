public class Board {

    private final int[][] tiles;
    private final int[][] orderedTiles;
    private final int dimensions;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        if (tiles.length!=tiles[0].length) throw new IllegalArgumentException();
        this.tiles=tiles;
        dimensions=tiles.length;

        orderedTiles = new int[dimensions][dimensions];
        int count = 1;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (i+1>=dimensions && j+1>=dimensions){
                    orderedTiles[i][j]=0;
                } else {
                    orderedTiles[i][j] = count++;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        String string = dimensions + "\n";
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                string += (tiles[i][j] + ((j+1>=dimensions? "\n" : " ")));
            }
        }
        return string;
    }

    // board dimension n
    public int dimension() { return dimensions; }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (orderedTiles[i][j]!=tiles[i][j]) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    //public int manhattan(){
    //    int count = 0;
    //    for (int i = 0; i < dimensions; i++) {
    //        for (int j = 0; j < dimensions; j++) {
    //
    //        }
    //    }
    //}

    // is this board the goal board?
    //public boolean isGoal()

    // does this board equal y?
    //public boolean equals(Object y)

    // all neighboring boards
    //public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    //public Board twin()

    // unit testing (not graded)
    public static void main(String[] args){
        int boardarr[][] = {{1,2,3},{4,5,6},{7,8,0}};
        Board board = new Board(boardarr);
        System.out.println(board);
        System.out.println(board.hamming());
    }

}