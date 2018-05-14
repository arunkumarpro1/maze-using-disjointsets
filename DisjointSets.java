package com.arun;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class DisjointSets {

	MazeCell[] s;

	/**
	 * Construct the disjoint sets object.
	 * 
	 * @param numElements
	 *            the initial number of disjoint sets
	 */
	public DisjointSets(int N, int numElements) {
		s = new MazeCell[numElements];
		s[0] = new MazeCell(false, true, false, true);
		for (int i = 1; i < numElements - 1; i++) {
			s[i] = new MazeCell(true, true, true, true);
		}
		s[numElements - 1] = new MazeCell(true, false, true, false);
	}

	/**
	 * Union two disjoint sets using the height heuristic. For simplicity, we
	 * assume root1 and root2 are distinct and represent set names.
	 * 
	 * @param root1
	 *            the root of set 1.
	 * @param root2
	 *            the root of set 2.
	 */
	public void union(int root1, int root2) {
		if (s[root2].value < s[root1].value) // root2 is deeper
			s[root1].value = root2;// Make root2 new root
		else {
			if (s[root1].value == s[root2].value)
				s[root1].value = s[root1].value - 1;// Update height if
													// same
			s[root2].value = root1;// Make root1 new root
		}
	}

	/**
	 * Perform a find. Error checks omitted again for simplicity.
	 * 
	 * @param x
	 *            the element being searched for.
	 * @return the set containing x.
	 */
	public int find(int x) {
		if (s[x].value < 0)
			return x;
		else
			return find(s[x].value); // Since union by height method is
										// used, path
		// compression is not done.
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int N = s.nextInt();
		int numElements = N * N;
		DisjointSets disjointSet = new DisjointSets(N, numElements);
		System.out.println("2D Cell\n");
		printmaze(disjointSet, N);
		int cell, adjCell = 0, wall, nUnions = 0, root1, root2;

		for (;;) {
			cell = ThreadLocalRandom.current().nextInt(0, numElements);
			for (;;) {
				wall = ThreadLocalRandom.current().nextInt(0, 4);
				switch (wall) {
				// left wall
				case 0:
					if (cell == 0 || cell % N == 0)
						continue;
					adjCell = cell - 1;
					break;

				// right wall
				case 1:
					if (cell == N - 1 || cell % N == N - 1)
						continue;
					adjCell = cell + 1;
					break;

				// top wall
				case 2:
					if (cell < N)
						continue;
					adjCell = cell - N;
					break;

				// bottom wall
				case 3:
					if (cell >= numElements - N)
						continue;
					adjCell = cell + N;
				}
				break;
			}
			if (!((root1 = disjointSet.find(cell)) == (root2 = disjointSet
					.find(adjCell)))) {
				int adjWall;
				if (wall % 2 == 0)
					adjWall = wall + 1;
				else
					adjWall = wall - 1;

				disjointSet.s[cell].walls[wall] = disjointSet.s[adjCell].walls[adjWall] = false;
				disjointSet.union(root1, root2);
				nUnions += 1;
				if (nUnions >= numElements - 1)
					break;
			}
		}
		System.out.println("\n");
		System.out.println("Generated Maze\n");
		printmaze(disjointSet, N);
		s.close();
	}

	public static void printmaze(DisjointSets disjointSet, int N) {
		MazeCell s[] = disjointSet.s;
		for (int i = 0; i < s.length; i += N) {
			printTopWall(i, s, N);
			System.out.println("+");
			printCell(i, s, N);
			System.out.println();
		}

		// Bottom walls
		for (int i = 0; i < N - 1; i++)
			System.out.print("+--");

		System.out.print("+");
	}

	private static void printCell(int start, MazeCell[] s, int N) {
		int end = start + N;
		Boolean walls[];
		for (int i = start; i < end; i++) {
			walls = s[i].walls;
			if (i % N == 0 && walls[0] == true)
				System.out.print("|");
			else if (i == 0)
				System.out.print(" ");
			// System.out.print((char) (65 + i));
			System.out.print("  ");
			if (walls[1] == true)
				System.out.print("|");
			else
				System.out.print(" ");
		}
	}

	private static void printTopWall(int start, MazeCell[] s, int N) {
		int end = start + N;
		Boolean walls[];
		for (int i = start; i < end; i++) {
			walls = s[i].walls;
			System.out.print("+");
			if (walls[2] == true)
				System.out.print("--");
			else
				System.out.print("  ");
		}
	}

}
