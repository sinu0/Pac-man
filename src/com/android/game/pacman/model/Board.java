package com.android.game.pacman.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import android.content.res.Resources;

import com.adroid.game.pacman.R;
import com.android.game.pacman.utils.GameEnum;

public class Board {

	private LinkedList<SolidObject> bGame;

	private int size;
	private char[][] lvl;
	private LinkedList<Block> path;
	private LinkedList<Block> wall;
	private Block[][] blockTab;
	Resources res;

	public Board(int x, int y, int size, Resources res) {

		this.size = size;
		this.res = res;
		blockTab = new Block[x][y];
		bGame = new LinkedList<SolidObject>();
		lvl = readLvl(x, y, res);
		path = new LinkedList<Block>();
		wall = new LinkedList<Block>();
		// Bitmap bitmap=
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {

				blockTab[j][i] = new Block(j, i, size);
				switch (lvl[j][i]) {
				case 'x':
					setBlock(j, i);
					break;
				case '0':
					setPath(j, i);
					blockTab[j][i].kind = GameEnum.PATH;
					break;
				case 'o':
					setFoodUp(j, i, res);
					blockTab[j][i].kind = GameEnum.PATH;
					break;
				case '.':
					setFood(j, i, res);

					blockTab[j][i].kind = GameEnum.PATH;
					break;
				}

			}

		}

	}

	private void setBlock(int x, int y) {
		Block newB = new Block(x, y, size);
		wall.add(newB);
	}

	private void setPath(int x, int y) {

		Block newB = new Block(x, y, size);
		newB.kind = GameEnum.PATH;
		path.add(newB);

	}

	private void setFood(int x, int y, Resources res) {
		Food newF = new Food(x, y, res);
		bGame.add(newF);
		setPath(x, y);
	}

	private void setFoodUp(int x, int y, Resources res) {
		FoodUp newF = new FoodUp(x, y, res);

		bGame.add(newF);
		setPath(x, y);

	}

	public Block[][] getBlock() {
		return blockTab;
	}

	public LinkedList<SolidObject> getbGame() {
		return bGame;
	}

	public LinkedList<Block> getPath() {
		return path;
	}

	public LinkedList<Block> getWall() {
		return wall;
	}

	public char[][] readLvl(int x, int y, Resources res) {

		InputStream inputStream = res.openRawResource(R.raw.lvl);

		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		int r;
		char[][] buf = new char[x][y];
		int _x = 0, _y = 0;
		try {
			while ((r = buffreader.read()) != -1) {
				if (r == 10) {

					_y++;
					_x = 0;

				} else {

					buf[_x][_y] = (char) r;
					_x++;
				}
			}
		} catch (IOException e) {
			return null;
		}

		return buf;

	}
}
