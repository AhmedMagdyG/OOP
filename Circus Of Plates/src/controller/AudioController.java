package controller;

import java.io.File;
import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
	private static final String BACKGROUND_MUSIC = "background_Music.mp3";

	public void playBackgroundMusic() {
		Media backgroundMusic = new Media(Paths.get("res" + File.separator + BACKGROUND_MUSIC).toUri().toString());
		MediaPlayer player = new MediaPlayer(backgroundMusic);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		player.play();
	}
}
