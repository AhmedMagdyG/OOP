package controller;

import java.io.File;
import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
	private static final String BACKGROUND_MUSIC = "background_Music.mp3";
	private Media backgroundMusic;
	private MediaPlayer player;

	public AudioController() {
		backgroundMusic = new Media(Paths.get("res" + File.separator + BACKGROUND_MUSIC).toUri().toString());
	}

	public void playBackgroundMusic() {
		player = new MediaPlayer(backgroundMusic);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		player.play();
	}

	public void pauseBackgroundMusic() {
		player.pause();
	}

	public void resumeBackgroundMusic() {
		player.play();
	}
}
