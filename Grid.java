package sudoku;

import java.util.*;

public class Grid {
	private int[][] values;

	public Grid(String[] rows) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++) {
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i = 0; i < 9; i++) {
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}

	public String toString() {
		String s = "";
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char) ('0' + n);
			}
			s += "\n";
		}
		return s;
	}

	Grid(Grid src) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++)
			for (int i = 0; i < 9; i++)
				values[j][i] = src.values[j][i];
	}

	public ArrayList<Grid> next9Grids() {
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;
		int number = 1;
		boolean found = false;
		// Find x,y of an empty cell.
		for (int row = 0; row < values.length; row++) 
		{
			for (int col = 0; col < values[0].length; col++) 
			{
				int current = values[row][col];
				if(!found)
				{
					if (current == 0) 
					{
						xOfNextEmptyCell = row;
						yOfNextEmptyCell = col;
						found = true;
					}
				}
			}
		}
		ArrayList<Grid> grids = new ArrayList<Grid>();

		for (int i = 0; i < 9; i++) {
			grids.add(new Grid(this));
		}
		
		for (int i = 0; i < grids.size(); i++) {
			grids.get(i).values[xOfNextEmptyCell][yOfNextEmptyCell] = number;
			number++;
		}
		return grids;
	}

	public boolean isLegal() {
		boolean legal = false;
		// Check every row. If you find an illegal row, return false.
		boolean iRow = true;
		for (int row = 0; row < values.length; row++) {
			ArrayList<String> valOfRow = new ArrayList<String>();
			for (int col = 0; col < values[0].length; col++) {
				String current = values[row][col] + "";
				if (!valOfRow.contains(current) || current.equals("0")) {
					valOfRow.add(values[row][col] + "");
				} else {
					iRow = false;
				}
			}
		}

		// Check every column. If you find an illegal column, return false.
		boolean iCol = true;
		for (int col = 0; col < values[0].length; col++) {
			ArrayList<String> valOfCol = new ArrayList<String>();
			for (int row = 0; row < values.length; row++) {
				String current = values[row][col] + "";
				if (!valOfCol.contains(current) || current.equals("0")) {
					valOfCol.add(values[row][col] + "");
				} else {
					iCol = false;
					
				}
			}
		}

		// Check every block. If you find an illegal block, return false.
		boolean iBlock = true;
		for (int row = 0; row < values.length; row += 3) {
			for (int col = 0; col < values[0].length; col += 3) {
				int[] numbers = new int[9];
				int n = 0;
				for(int jj = row; jj < row+3; jj++)
				{
					for(int ii= col; ii < col+3; ii++)
					{
						numbers[n++] = values[jj][ii];
					}
				}
				if(containsRepeated(numbers)) {
					iBlock = false;
				}
			}
		}

		// All rows/cols/blocks are legal.
		if (iRow && iCol && iBlock) {
			legal = true;
		}
		return legal;
	}
	
	public boolean containsRepeated(int[] ints)
	{
		boolean[] list = new boolean[10];
		for(int i: ints)
		{
			if(i == 0)
			{
				continue;
			}
			else if(list[i] == true)
			{
				return true;
			}
			else
			{
				list[i] = true;
			}
		}
		return false;
	}
	
	public boolean isFull() {
		boolean full = true;

		for (int row = 0; row < values.length; row++) {
			for (int col = 0; col < values[0].length; col++) {
				int current = values[row][col];
				if (current < 1 || current > 9) {
					full = false;
					break;
				}
			}
		}
		return full;
	}

	public boolean equals(Object x) {
		boolean equal = true;
		Grid that = (Grid) x;
		for (int row = 0; row < values.length; row++) {
			for (int col = 0; col < values[0].length; col++) {
				if (this.values[row][col] != that.values[row][col]) {
					equal = false;
				}
			}
		}
		return equal;
	}

	public static void main(String[] args) {
		TestGridSupplier test = new TestGridSupplier();
		Grid tester = test.getPuzzle3();
		System.out.println(tester.isLegal());
	}
}
