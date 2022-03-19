import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;

/**
 * 
 * @author monke
 *
 */
public class Solver {
	private ArrayList<Board> path;
	private boolean solvable;

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		if(initial == null) throw new IllegalArgumentException("Starting board cannot be null.");
		Solved(initial);
		
	}
	
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
	public boolean isSolvable() {
		// is the initial board solvable?
		return solvable;
	}
	
	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		if(isSolvable()) return path.size()-1;
		return -1;

	}
	
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

class SearchNode {
	private final int numMoves;
	private final Board now;
	private final SearchNode prev;
	private final int manhattan;
	
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


}
class SearchNodeComparator implements Comparator<SearchNode>{

	@Override
	public int compare(SearchNode o1, SearchNode o2) {
		//if(o1.getManhattan()+o1.getMoves()-o2.getManhattan()-o2.getMoves() != 0) return o1.getManhattan()+o1.getMoves()-o2.getManhattan()-o2.getMoves();
		//else return o1.getManhattan()-o2.getManhattan();
		return o1.getManhattan()+o1.getMoves()-o2.getManhattan()-o2.getMoves();
	}
	
}