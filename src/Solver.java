import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

public class Solver {

    private MinPQ<Board> minPQ = new MinPQ<>();
    private Board initial;
    private Board initialTwin;
    private boolean isSolvable = false;

    //TODO: remember to exclude the first board to ever become neighbor

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();

        this.initial=initial;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){

    }

    private class SearchNode implements Comparator<Board>{

        private Board board;
        private int moves;
        private int hamming;
        private SearchNode previousSearchNode;

        public SearchNode(Board board, SearchNode previousSearchNode) {
            this.board = board;
            this.previousSearchNode=previousSearchNode;
            this.hamming=board.hamming();
            moves = previousSearchNode==null ? 0 : previousSearchNode.moves+1;
        }

        public int getMoves(){ return moves; }

        @Override
        public int compare(Board o1, Board o2) {
            if (o1.hamming()<o2.hamming()) return -1;
            if (o1.hamming()>o2.hamming()) return 1;
            else return 0;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

       /* // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }*/
    }
}
