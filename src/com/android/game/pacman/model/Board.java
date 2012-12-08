package com.android.game.pacman.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

import android.R.integer;
import android.content.res.Resources;
import android.util.Log;
import android.util.Pair;

import com.adroid.game.pacman.R;
import com.android.game.pacman.game.GameLogic;
import com.android.game.pacman.utils.GameEnum;

public class Board {

	private LinkedList<SolidObject> bGame;

	private int size;
	private char[][] lvl;
	private LinkedList<Block> path;
	private LinkedList<Block> wall;
	private Block[][] blockTab;
	Resources res;

	public Board(Resources res, int sourceLvl) {

		this.size = GameLogic.BOARD_TILE_SIZE;
		lvl = readLvl(res, sourceLvl);
		blockTab = new Block[GameLogic.WIDTH][GameLogic.BOARD_HEIGHT];
		bGame = new LinkedList<SolidObject>();

		path = new LinkedList<Block>();
		wall = new LinkedList<Block>();
		// Bitmap bitmap=
		for (int i = 0; i < GameLogic.BOARD_HEIGHT; i++) {
			for (int j = 0; j < GameLogic.BOARD_WIDTH; j++) {

				blockTab[j][i] = new Block(j, i);
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
		Block newB = new Block(x, y);
		wall.add(newB);
	}

	private void setPath(int x, int y) {

		Block newB = new Block(x, y);
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

	public char[][] readLvl(Resources res, int sourceLvl) {
		Pair<char[][], Pair<Integer, Integer>> x_y = readl(res, sourceLvl);
		GameLogic.BOARD_HEIGHT = x_y.second.second;
		GameLogic.BOARD_WIDTH = x_y.second.first;
		GameLogic.BOARD_TILE_SIZE = (int) ( GameLogic.WIDTH/x_y.second.first );
		Log.v("dem",String.valueOf(GameLogic.BOARD_TILE_SIZE));
		return x_y.first;

	}

	public static Pair<char[][], Pair<Integer, Integer>> readl(Resources res,
			int sourceLvl) {
		InputStream inputStream = res.openRawResource(sourceLvl);

		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		ArrayList<Character> tab = new ArrayList<Character>();
		int r;
		int _x = 0, _y = 0;
		try {
			while ((r = buffreader.read()) != -1) {
				if (r == 10) {
					_y++;
					_x = 0;
				} else {
					_x++;
					tab.add((char) r);
				}
			}
		} catch (IOException e) {
			// return null;
		}
		_y++;
		int index = 0;
		
		char[][] buf = new char[_x][_y];
		for (int i = 0; i < _y; i++) {
			for (int j = 0; j < _x; j++) {
				buf[j][i] = tab.get((index));
				index++;
			}

		}
		return new Pair<char[][], Pair<Integer, Integer>>(buf,
				new Pair<Integer, Integer>(_x, _y));
	}
}
