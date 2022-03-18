import java.util.Arrays;

public class Board {
	private int[][] Blocks;
	public Board(int[][] blocks) {
		// construct a board from an n-by-n array of blocks
		// (where blocks[i][j] = block in row i, column j)
		if(blocks == null) throw new IllegalArgumentException("blocks cannot be null");
		Blocks = new int[blocks.length][blocks.length];
		for(int i = 0; i < blocks.length; i++) {
			Blocks[i] = blocks[i].clone();
		}
	}

	public int dimension() {
		// board dimension n
		return Blocks.length;
	}
	public int hamming() {
		// number of blocks out of place
		int count = 1;
		int num = 0;
		for(int i =0;i<dimension();i++) {
			for(int j =0; j<dimension(); j++) {
				if(Blocks[i][j] != 0 && Blocks[i][j] != count) {
					num++;	
				}
				count++;
			}
		}
		return num;
	}
	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int count = 1;
		int num = 0;
		for(int i = 0;i<dimension();i++) {
			for(int j = 0; j<dimension(); j++) {
				if(Blocks[i][j] != 0 && Blocks[i][j] != count) {
					if ((Blocks[i][j]-1) % dimension() - j < 0) {
						num = num - ((Blocks[i][j]-1) % dimension() - j);
					}
					else {
						num = num + ((Blocks[i][j]-1) % dimension() - j);
					}
					if((Blocks[i][j]-1) / dimension() - i < 0) {
						num = num - ((Blocks[i][j]-1) / dimension() - i);
					}
					else {
						num = num + ((Blocks[i][j]-1) / dimension() - i);
					}
				}
				count++;
			}

		}
		return num;
	}

	public boolean isGoal() {
		// is this board the goal board?
		int count = 1;
		for(int[] i : Blocks) {
			for(int j : i) {
				if(j != count)return false;
				if(count == Blocks.length*Blocks.length-1) return true;
				count++;
			}
		}
		throw new ArithmeticException("How did you get here?");
	}
	public Board twin() {
		// a board that is obtained by exchanging any pair of blocks
		if(Blocks.length == 1 || Blocks.length == 0) throw new NullPointerException("No blocks to exchange.");
		int[][] temp = new int[Blocks.length][Blocks.length];
		for(int i =0; i < Blocks.length; i++) {
			temp[i] = Blocks[i].clone();
		}
		if(temp[0][0] == 0 || temp [0][1] == 0) {
			int Int = temp[1][0];
			temp[1][0] = temp[1][1];
			temp[1][1] = Int;
		}
		else {
			int Int = temp[0][0];
			temp[0][0] = temp[0][1];
			temp[0][1] = Int;
		}
		return new Board(temp);
	}
	public boolean equals(Object y) {
		// does this board equal y?
		if(this == y) return true;
		if(y == null) return false;
		if(y.getClass() == this.getClass()) {
			Board newY = (Board) y;
			return newY.dimension() == this.dimension() && Arrays.deepEquals(newY.Blocks, this.Blocks);
		}
		return false;
	}
	public Iterable<Board> neighbors() {
		// all neighboring boards
		int row = 0;
		int col = 0;
		Board[] boards;
		while (Blocks[row][col] != 0) { 
			if(col != dimension()-1) col++;
			else {
				row++;
				col = 0;
			}

		}
		if(row != 0 && row != dimension()-1) {
			if(col != 0 && col != dimension()-1){
				boards = new Board[4];
				boards[1] = new Board(Blocks);
				boards[1].Blocks[row][col] = boards[1].Blocks[row-1][col];
				boards[1].Blocks[row-1][col] = 0;
				
				boards[2] = new Board(Blocks);
				boards[2].Blocks[row][col] = boards[2].Blocks[row+1][col];
				boards[2].Blocks[row+1][col] = 0;
				
				boards[3] = new Board(Blocks);
				boards[3].Blocks[row][col] = boards[3].Blocks[row][col+1];
				boards[3].Blocks[row][col+1] = 0;
				
				boards[0] = new Board(Blocks);
				boards[0].Blocks[row][col] = boards[0].Blocks[row][col-1];
				boards[0].Blocks[row][col-1] = 0;
			}
			else {
				boards = new Board[3];
				boards[1] = new Board(Blocks);
				boards[1].Blocks[row][col] = boards[1].Blocks[row-1][col];
				boards[1].Blocks[row-1][col] = 0;
				
				boards[2] = new Board(Blocks);
				boards[2].Blocks[row][col] = boards[2].Blocks[row+1][col];
				boards[2].Blocks[row+1][col] = 0;
				
				if(col == 0) {
					boards[0] = new Board(Blocks);
					boards[0].Blocks[row][col] = boards[0].Blocks[row][col+1];
					boards[0].Blocks[row][col+1] = 0;
				}
				else {
					boards[0] = new Board(Blocks);
					boards[0].Blocks[row][col] = boards[0].Blocks[row][col-1];
					boards[0].Blocks[row][col-1] = 0;
				}
				
				
			}
		}
		else {
			if(col != 0 && col != dimension()-1) {
				boards = new Board[3];
				if(row == 0) {
					boards[1] = new Board(Blocks);
					boards[1].Blocks[row][col] = boards[1].Blocks[row+1][col];
					boards[1].Blocks[row+1][col] = 0;
				}
				else {
					boards[1] = new Board(Blocks);
					boards[1].Blocks[row][col] = boards[1].Blocks[row-1][col];
					boards[1].Blocks[row-1][col] = 0;
				}
				
				boards[2] = new Board(Blocks);
				boards[2].Blocks[row][col] = boards[2].Blocks[row][col+1];
				boards[2].Blocks[row][col+1] = 0;
				
				boards[0] = new Board(Blocks);
				boards[0].Blocks[row][col] = boards[0].Blocks[row][col-1];
				boards[0].Blocks[row][col-1] = 0;
			}
			else {
				boards = new Board[2];
				boards[0] = new Board(Blocks);
				boards[1] = new Board(Blocks);
				if(row == 0) {
					boards[1].Blocks[row][col] = boards[1].Blocks[row+1][col];
					boards[1].Blocks[row+1][col] = 0;
				}
				else {
					boards[1].Blocks[row][col] = boards[1].Blocks[row-1][col];
					boards[1].Blocks[row-1][col] = 0;
				}
				if(col == 0) {
					boards[0].Blocks[row][col] = boards[0].Blocks[row][col+1];
					boards[0].Blocks[row][col+1] = 0;
					
				}
				else {
					boards[0].Blocks[row][col] = boards[0].Blocks[row][col-1];
					boards[0].Blocks[row][col-1] = 0;
				}
			}
		}
		return Arrays.asList(boards);
	}
	public String toString() {
		// string representation of this board (in the output format specified below)
		StringBuilder s = new StringBuilder();
		s.append(dimension() + "\n");
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				s.append(String.format("%2d ", Blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	public static void main(String[] args) {
		// unit tests (not graded)
	}
}