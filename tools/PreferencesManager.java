package com.matthewgarrison.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.matthewgarrison.GameHandler;
import com.matthewgarrison.objects.Score;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PreferencesManager {
	private static GameHandler game;
	private static Preferences prefs;

	public static void create(GameHandler g) {
		game = g;
		prefs = Gdx.app.getPreferences("My preferences");
	}

	public static void loadFile(String FileName) {
		FileHandle file = Gdx.files.local(FileName);
		if (!file.exists()) {
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local(FileName).write(false)));
				out.write("1");
				out.close();
			} catch (GdxRuntimeException ex) {

			} catch (IOException e) {
				System.out.println("LoadFile error");
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
		boolean fileFail = !file.exists();
		// Creates the file if it doesn't exist.
		if (fileFail) file = Gdx.files.internal("SavedVariables/highscoreBase.in");

		// Scans in the values into the Scores array.
		String[] parts = file.readString().split(":| ");
		game.createScores(parts.length / 2);
		for (int i = 0; i < game.getScores().length; i += 2) {
			game.getScores()[i] = new Score(parts[i], Integer.parseInt(parts[i+1]));
		}
		game.sortScores();

		if (fileFail) {
			// Saves the default values in the file, if it didn't exist.
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
