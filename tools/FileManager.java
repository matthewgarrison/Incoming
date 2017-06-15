package com.matthewgarrison.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.matthewgarrison.GameHandler;
import com.matthewgarrison.objects.Score;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileManager {
	static GameHandler game;

	public static void create(GameHandler g) {
		game = g;
	}

	public static void loadSecureFile(String FileName) {
		FileHandle file = Gdx.files.local(FileName);
		if (!file.exists()) {
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local(FileName).write(false)));
				out.write("1");
				out.close();
			} catch (GdxRuntimeException ex) {

			} catch (IOException e) {
				System.out.println("loadSecureFile error");
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	// Loads the leaderboards, player name, difficulty, etc.
	public static void loadNonSecureFile(String FileName) {
		FileHandle file = Gdx.files.external(FileName);
		// Creates the file if it doesn't exist.
		if (!file.exists()) {
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(FileName).write(false)));
				out.write("1");
				out.close();
			} catch (GdxRuntimeException ex) {

			} catch (IOException e) {
				System.out.println("loadNonSecureFile error");
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	// Loads the high scores.
	public static void loadHighscoreFile(String FileName) {
		FileHandle file = Gdx.files.local(FileName);
		// Creates the file if it doesn't exist.
		if (!file.exists()) {
			file = Gdx.files.internal("SavedVariables/highscoreBase.in");
			String[] parts = file.readString().split(":| ");
			game.createScores(parts.length / 2);
			for (int i = 0; i < game.getScores().length; i += 2) {
				game.getScores()[i] = new Score(parts[i], Integer.parseInt(parts[i+1]));
			}
			game.sortScores();

			// Saves the top 3 in the file.
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local(FileName).write(false)));
				for (int j = 0; j < 3; j++) {
					out.write(game.getScores()[j].toString() + " ");
				}
				out.close();
			} catch (GdxRuntimeException ex) {

			} catch (IOException e) {
				System.out.println("LoadHighScoreFile error");
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	public static void writeToFile(String fileName, String value, boolean append) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local(fileName).write(append)));
			out.write(value);
			out.close();
		} catch (GdxRuntimeException ex) {
			Gdx.app.log("ERROR", ex.getMessage());
		} catch (IOException e) {
			Gdx.app.log("ERROR", e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
