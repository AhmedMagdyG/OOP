package model;

import java.util.ArrayList;

import model.Sprite;

public class GameModel {
	
	private ArrayList<Avatar> avatars;
	private int playerCount;
	
	public GameModel(int playerCount){
		this.playerCount = playerCount;
		initialiseAvatars();
	}

	private void initialiseAvatars() {
		avatars = new ArrayList<Avatar>();
		for(int i = 0; i < playerCount; i++){
			avatars.add(new Avatar());
		}
	}

	public void updateData() {
		
	}


	public void movePlayer(int i, int dist) {
		avatars.get(i).move(dist);
	}

	public ArrayList<Sprite> getSprites() {
		ArrayList<Sprite> sprites = new ArrayList<Sprite>();
		for(int idx = 0; idx < playerCount; idx++){
			sprites.add(avatars.get(idx).getSprite());
		}
		// get shapes and rails here
		return sprites;
	}
}
