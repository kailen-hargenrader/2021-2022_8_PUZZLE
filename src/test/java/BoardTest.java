import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoardTest {

	Board board;
	Board board2;
	Board board3;
	@Before
	public void setUp() throws Exception {
		board = generateBoard("puzzle00.txt");
		board2 = generateBoard("puzzle01.txt");
		board3 = generateBoard("puzzle18.txt");
	}

	private Board generateBoard(String filename) {
		// create initial board from file
		In in = new In("8puzzle-test-files/" + filename);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable()) StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution()) {
				StdOut.println(board);
			}
		}
		return initial;
	}

		@Test
		public void testDimension() {
			assertTrue("dimension should be 10, was " + board.dimension(), board.dimension() == 10);
		}

		@Test
		public void testHamming() {
			assertTrue("hamming should be 0, was " + board.hamming(), board.hamming() == 0);
		}

		@Test
		public void testManhattan() {
			assertTrue("Manhattan should be 0, was " + board.manhattan(), board.manhattan() == 0);
		}

		@Test
		public void testGoal() {
			assertTrue("Goal should be true, was " + board.isGoal(), board.isGoal() == true);
		}

		@Test
		public void testTwin() {
			Board twin = board.twin();
			assertTrue("Hamming of Twin should be 2, was " + twin.hamming(), twin.hamming() == 2);
			assertTrue("manhattan of Twin should be 2, was " + twin.manhattan(), twin.manhattan() == 2);
		}

		@Test
		public void testEquals() {
			int[][] Nums = new int[2][2];
			Nums[0][0] = 1;
			Nums[0][1] = 0;
			Nums[1][0] = 3;
			Nums[1][1] = 2;
			Board clone = new Board(Nums);
			assertTrue("boards should be equal", board2.equals(clone));
		}
	}
