import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private boolean isSolvable = false;
    private final Stack<Board> solutionBoards = new Stack<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<SearchNode> searchNodes = new MinPQ<>();

        searchNodes.insert(new SearchNode(initial, null));
        searchNodes.insert(new SearchNode(initial.twin(), null));

        while (!searchNodes.min().board.isGoal()) {
            SearchNode searchNode = searchNodes.delMin();
            for (Board board : searchNode.board.neighbors()) {
                if ((searchNode.previousSearchNode == null || !searchNode.previousSearchNode.board.equals(board) && searchNode.previousSearchNode!= null)) {
                    searchNodes.insert(new SearchNode(board, searchNode));
                }
            }
        }

        //Find out if the solution came from the twin or the initial board
        SearchNode currentNode = searchNodes.min();
        while (currentNode.previousSearchNode != null) {
            solutionBoards.push(currentNode.board);
            currentNode = currentNode.previousSearchNode;
        }
        solutionBoards.push(currentNode.board);

        if (currentNode.board.equals(initial)) {
            isSolvable = true;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable ? solutionBoards.size()-1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable ? solutionBoards : null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previousSearchNode;

        public SearchNode(Board board, SearchNode previousSearchNode) {
            this.board = board;
            this.previousSearchNode = previousSearchNode;
            moves = previousSearchNode == null ? 0 : previousSearchNode.moves + 1;
        }

        public int getMoves() {
            return moves;
        }

        public int getManhattan() {
            return board.manhattan();
        }

        @Override
        public int compareTo(SearchNode o) {
            int priority = (getManhattan() + moves) - (o.getManhattan() + o.getMoves());
            return priority == 0 ? getManhattan() - o.getManhattan() : priority;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
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
        }
    }
}
