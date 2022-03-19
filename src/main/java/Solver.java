import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;

/**
 * Solves 8 puzzle
 * @author monke
 *
 */
public class Solver {
	private ArrayList<Board> path;
	private boolean solvable;
	
	/**
	 * Initiates Solver class
	 * @param initial initial board
	 */
	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		if(initial == null) throw new IllegalArgumentException("Starting board cannot be null.");
		Solved(initial);
		
	}
	
	/**
	 * Solves 8 puzzle using a priority queue
	 * @param initial initial board
	 */
	private void Solved(Board initial) {
		MinPQ<SearchNode> pq = new MinPQ<SearchNode>(new SearchNodeComparator());
		MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>(new SearchNodeComparator());
		SearchNode current = new SearchNode(initial, null, 0);
		SearchNode currentTwin = new SearchNode(initial.twin(), null, 0);
		
		pq.insert(current);
		twinPQ.insert(currentTwin);
		
		while(!current.getNow().isGoal() && !currentTwin.getNow().isGoal()) {
			
			current = pq.delMin();
			for(Board b : current.getNow().neighbors()) {
				if(current.getPrev() == null || !b.equals(current.getPrev().getNow())) {
					pq.insert(new SearchNode(b, current, current.getMoves()+1));
				}
			}
			
			currentTwin = twinPQ.delMin();
			for(Board b : currentTwin.getNow().neighbors()) {
				if(currentTwin.getPrev() == null || !b.equals(currentTwin.getPrev().getNow())) {
					twinPQ.insert(new SearchNode(b, currentTwin, currentTwin.getMoves()+1));
				}
			}
		}
		if(current.getNow().isGoal()) solvable = true;
		else solvable = false;
		path = new ArrayList<Board>();
		while(current != null) {
			path.add(current.getNow());
			current = current.getPrev();
		}
		Collections.reverse(path);
	}
	
	/**
	 * Is the board solvable
	 * @return solvable?
	 */
	public boolean isSolvable() {
		// is the initial board solvable?
		return solvable;
	}
	
	/**
	 * How many moves until solved
	 * @return number of moves till solved
	 */
	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		if(isSolvable()) return path.size()-1;
		return -1;

	}
	
	/**
	 * Array of board states leading to solve
	 * @return path to solve
	 */
	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if unsolvable
		if(isSolvable()) {
			return path;
		}
		return null;
	}
	
	public static void main(String[] args) {
		//solve a slider puzzle (given below)
	}
}

/**
 * A node with a board, a previous board, and the moves taken so far
 * @author monke
 *
 */
class SearchNode {
	private final int numMoves;
	private final Board now;
	private final SearchNode prev;
	private final int manhattan;
	
	/**
	 * initiates SearchNode class
	 * @param b current board
	 * @param last last board
	 * @param p number of moves so far
	 */
	public SearchNode(Board b, SearchNode last, int p) {
		
		numMoves = p;
		now = b;
		prev = last;
		manhattan = b.manhattan();
	}
	
	/**
	 * returns current board
	 * @return current board
	 */
	public Board getNow() {
		return now;
	}
	
	/**
	 * Returns current number of moves
	 * @return number of moves
	 */
	public int getMoves() {
		return numMoves;
	}
	
	/**
	 * Returns previous node
	 * @return last node
	 */
	public SearchNode getPrev() {
		return prev;
	}
	
	/**
	 * returns the manhattan distance of current node
	 * @return manhattan distance
	 */
	public int getManhattan() {
		return manhattan;
	}


}

/**
 * Creates method for comparing SearchNodes by Manhattan distance
 * @author monke
 *
 */
class SearchNodeComparator implements Comparator<SearchNode>{

	@Override
	public int compare(SearchNode o1, SearchNode o2) {
		return o1.getManhattan()+o1.getMoves()-o2.getManhattan()-o2.getMoves();
	}
	
}