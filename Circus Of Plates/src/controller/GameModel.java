package controller;

public class GameModel {
	
	private static GameModel gameModel;
	
	private GameModel(){
		
	}

	public void updateData() {
		
	}

	public static GameModel getInstance() {
		if(gameModel == null){
			gameModel = new GameModel();
		}
		return gameModel;
	}
}
