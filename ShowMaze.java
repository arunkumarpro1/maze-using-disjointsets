package com.arun;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ShowMaze extends Frame {
	private static final long serialVersionUID = 1L;
	static int N;
	static DisjointSets disjointSet;
	static double x = 30;
	static double y = 30;

	/*
	 * Constructor, Calls the method which starts the GUI
	 */
	ShowMaze() {
		prepareGUI();
	}

	/*
	 * It brings up the GUI for the maze
	 */
	public void prepareGUI() {
		setSize(600, 600);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
	}

	/*
	 * It displays the walls, if visible param of wall is true
	 * 
	 * @param Graphics for printing maze
	 */

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("Serif", Font.PLAIN, 24);
		g2.setFont(font);

		MazeCell s[] = disjointSet.s;
		for (int i = 0; i < s.length; i += N) {
			printTopWall(i, s, g2);
			printCell(i, s, g2);
		}

		// Bottom walls
		for (int i = 0; i < N - 1; i++) {
			Line2D line = new Line2D.Double();
			line.setLine(x, y, x + 25, y);
			g2.draw(line);
		}

	}

	private static void printCell(int start, MazeCell[] s, Graphics2D g) {
		int end = start + N;
		Boolean walls[];
		for (int i = start; i < end; i++) {
			walls = s[i].walls;
			if (i % N == 0 && walls[0] == true)
				System.out.print("|");
			// System.out.print((char) (65 + i));
			System.out.print(" ");
			if (walls[1] == true)
				System.out.print("|");
			else
				System.out.print(" ");
		}
	}

	private static void printTopWall(int start, MazeCell[] s, Graphics2D g) {
		int end = start + N;
		Boolean walls[];
		for (int i = start; i < end; i++) {
			walls = s[i].walls;
			if (walls[2] == true) {
				Line2D line = new Line2D.Double();
				line.setLine(x, y, x + 25, y);
				g.draw(line);
			}
		}
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		N = s.nextInt();
		int numElements = N * N;
		disjointSet = new DisjointSets(N, numElements);
		// printmaze(disjointSet, N);
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
		ShowMaze maze = new ShowMaze();
		// printmaze(disjointSet, N);
		maze.setVisible(true);
		s.close();
	}
}
