import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.MinPQ;
public class Solver {
	private Board[] path;
	private ArrayList<Board> past;
	private ArrayList<Board> twinPast;
	private MinPQ<SearchNode> pq;
	private MinPQ<SearchNode> twinPQ;

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		pq = new MinPQ<SearchNode>();
		pq.insert(new SearchNode(initial, null, 0));
		twinPQ = new MinPQ<SearchNode>();
		twinPQ.insert(new SearchNode(initial.twin(), null, 0));
		past = new ArrayList<Board>();
		past.add(initial);
		twinPast = new ArrayList<Board>();
		twinPast.add(initial);
		SearchNode current;
		while(!pq.min().getNow().isGoal() && !twinPQ.min().getNow().isGoal()) {
			current = pq.delMin();
			for(Board b : current.getNow().neighbors()) {
				if(!past.contains(b)) {
					pq.insert(new SearchNode(b, current, current.getMoves()+1));
					past.add(b);
				}
			}
			current = twinPQ.delMin();
			for(Board b : current.getNow().neighbors()) {
				if(!twinPast.contains(b)) {
					twinPQ.insert(new SearchNode(b, current, current.getMoves()+1));
					twinPast.add(b);
				}
			}
			//System.out.println(pq.min().getNow());
			//System.out.println(past.size());
		}
	}
	public boolean isSolvable() {
		// is the initial board solvable?
		return pq.min().getNow().isGoal();
	}
	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		if(isSolvable()) return pq.min().getMoves();
		return -1;

	}
	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if unsolvable
		if(isSolvable()) {
			path = new Board[moves()];
			SearchNode current = pq.min();
			for(int i = 0; i<moves();i++) {
				path[i] = current.getNow();
				current = current.getPrev();
			}
			return Arrays.asList(path);
		}
		return null;
	}
	public static void main(String[] args) {
		// solve a slider puzzle (given below)
	}
}
class SearchNode implements Comparable<SearchNode>{
	private int numMoves;
	private Board now;
	private SearchNode prev;
	private int manhattan;
	public SearchNode(Board b, SearchNode last, int p) {
		numMoves = p;
		now = b;
		prev = last;
		manhattan = b.manhattan();
	}

	public Board getNow() {
		return now;
	}
	public int getMoves() {
		return numMoves;
	}
	public SearchNode getPrev() {
		return prev;
	}
	public int getManhattan() {
		return manhattan;
	}
	@Override
	public int compareTo(SearchNode o) {
		if(o.getMoves()+o.getManhattan() > this.getMoves()+this.getManhattan()) return -1;
		else if(o.getMoves()+o.getManhattan() > this.getMoves()+this.getManhattan()) return 1;
		return 0;
	}


}