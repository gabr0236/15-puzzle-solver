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
                if (orderedTiles[i][j]!=0) {
                    if (orderedTiles[i][j] != tiles[i][j]) count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int count = 0;
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (orderedTiles[i][j]!=tiles[i][j]){
                    for (int k = 0; k < dimensions; k++) {
                        for (int l = 0; l < dimensions; l++) {
                            if(orderedTiles[i][j]==tiles[k][l]){
                                count+=Math.abs((i+j)-(k+l));
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    private int[] getIndexOfTile(int tile){
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (orderedTiles[i][j]==tile) return new int[]{i, j};
            }
        }
        return new int[2];
    }

    // is this board the goal board?
    public boolean isGoal(){
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (orderedTiles[i][j]!=tiles[i][j]) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(this==y) return true;

        if (y instanceof Board){
            if (((Board) y).dimension() == this.dimensions){
                if (y.toString()==this.toString()){
                    return true;
                }
            }
        }
        return false;
    }

    // all neighboring boards
    //public Iterable<Board> neighbors()

    // a board that is obtained by exchanging any pair of tiles
    //public Board twin()

    // unit testing (not graded)
    public static void main(String[] args){
        int boardarr[][] = {{8,1,3},{4,0,2},{7,6,5}};
        int boardarr2[][] = {{1,2,3},{4,5,6},{7,8,0}};
        Board board = new Board(boardarr);
        Board board2 = new Board(boardarr2);

        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board2.isGoal());
    }

}