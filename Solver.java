package sudoku;

import java.util.*;

public class Solver 
{
	private Grid problem;
	private ArrayList<Grid> solutions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
	private void solveRecurse(Grid grid)
	{		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			return;
		}
		else if (eval == Evaluation.ACCEPT)
		{
			solutions.add(grid);
		}
		else
		{
			if(eval == Evaluation.CONTINUE)
			{
				ArrayList<Grid> nextSolutions = grid.next9Grids();
				for(Grid current: nextSolutions)
				{
					solveRecurse(current);
				}
			}
		}
	}
	
	public Evaluation evaluate(Grid grid)
	{
		if(!grid.isLegal())
		{
			return Evaluation.ABANDON;
		}
		else if(grid.isLegal() && grid.isFull())
		{
			return Evaluation.ACCEPT;
		}
		else if(grid.isLegal() && !grid.isFull())
		{
			return Evaluation.CONTINUE;
		}
		else
		{
			return null; 
		}
	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		Grid g = TestGridSupplier.getPuzzle1();
		Solver solver = new Solver(g);
		System.out.println("Will solve\n" + g);
		solver.solve();
		System.out.println(solver.getSolutions());
	}
}
